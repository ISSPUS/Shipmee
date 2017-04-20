package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Shipment;
import domain.ShipmentOffer;
import domain.User;
import repositories.ShipmentOfferRepository;

@Service
@Transactional
public class ShipmentOfferService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShipmentOfferRepository shipmentOfferRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
		
	// Constructors -----------------------------------------------------------

	public ShipmentOfferService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ShipmentOffer create(int shipmentId) {
		ShipmentOffer res;
		Shipment shipment;

		shipment = shipmentService.findOne(shipmentId);
		Assert.notNull(shipment, "message.error.shipmentOffer.shipment.mustExist");

		res = new ShipmentOffer();
		res.setShipment(shipment);
		res.setUser(userService.findByPrincipal());

		return res;
	}

	public ShipmentOffer createFromClone(int shipmentOfferId) {
		ShipmentOffer res;
		ShipmentOffer act;
		act = this.findOne(shipmentOfferId);
		Assert.notNull(act, "message.error.shipmentOffer.mustExist");

		res = this.create(act.getShipment().getId());
		res.setAmount(act.getAmount());
		res.setDescription(act.getDescription());

		return res;
	}

	public ShipmentOffer save(ShipmentOffer input) {
		User actUser;
		ShipmentOffer tmp;

		Assert.notNull(input, "message.error.shipmentOffer.mustExist");

		actUser = userService.findByPrincipal();

		if (actUser.equals(input.getUser())) { // User that create shipment
			if (input.getId() != 0) {
				tmp = this.findOne(input.getId());
				Assert.notNull(tmp, "message.error.shipmentOffer.save.dontFindID");
				Assert.isTrue(tmp.getUser().equals(actUser), "message.error.shipmentOffer.save.user.own");
				Assert.isTrue(!tmp.getAcceptedBySender() && !tmp.getRejectedBySender(),
						"message.error.shipmentOffer.notAcceptedOrRejected");
			} else {
				tmp = this.create(input.getShipment().getId());
			}

			tmp.setAmount(input.getAmount());
			tmp.setDescription(input.getDescription());
		} else if (actUser.equals(input.getShipment().getCreator())) { // User
																		// that
																		// put
																		// the
																		// offer
			Assert.isTrue(input.getId() != 0, "service.shipmentOffer.save.ProposerCreating"); // The
																								// shipmentCreator
																								// can't

			tmp = this.findOne(input.getId());
			Assert.notNull(tmp, "message.error.shipmentOffer.save.dontFindID");
			Assert.isTrue(tmp.getShipment().getCreator().equals(actUser), "message.error.shipmentOffer.save.user.own");
			tmp.setAcceptedBySender(input.getAcceptedBySender());
			tmp.setRejectedBySender(input.getRejectedBySender());
		} else {
			Assert.isTrue(false, "shipmentOffer.commit.error");
			return null;
		}
		Assert.isTrue(!tmp.getUser().equals(tmp.getShipment().getCreator()),
				"message.error.shipmentOffer.equalCreatorAndProposer");
		Assert.isTrue(tmp.getShipment().getMaximumArriveTime().after(new Date()),
				"message.error.shipmentOffer.shipment.maxArrivalTime.future");

		tmp = shipmentOfferRepository.save(tmp);

		return tmp;
	}

	public void delete(int shipmentOfferId) {
		ShipmentOffer a;

		a = this.findOne(shipmentOfferId);

		Assert.isTrue(this.checkPermission(a), "service.shipmentOffer.delete.notPermitted");

		shipmentOfferRepository.delete(a);
	}

	public ShipmentOffer findOne(int shipmentOfferId) {
		ShipmentOffer result;
		result = shipmentOfferRepository.findOne(shipmentOfferId);

		if (!this.checkPermission(result)) {
			result = null;
		}

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<ShipmentOffer> findAllByShipmentId(int shipmentId) {
		Collection<ShipmentOffer> result;
		User actUser;

		actUser = userService.findByPrincipal();
		result = shipmentOfferRepository.findAllByShipmentId(shipmentId);

		if (!result.isEmpty()) {
			Assert.isTrue(result.iterator().next().getShipment().getCreator().equals(actUser),
					"service.shipmentOffer.delete.notPermitted");
		}

		return result;
	}
	
	public Collection<ShipmentOffer> findAllByShipmentId2(int shipmentId){
		Collection<ShipmentOffer> result;
		
		result = shipmentOfferRepository.findAllByShipmentId(shipmentId);
		
		return result;
	}
	
	
	public Collection<ShipmentOffer> findAllPendingByShipmentId(int shipmentId){
		Collection<ShipmentOffer> result;
		
		result = shipmentOfferRepository.findAllPendingByShipmentId(shipmentId);
		
		return result;
	}
	
	/**
	 * 
	 * @param shipmentOfferId - The id of the ShipmentOffer
	 * 
	 * The client (the user that created the shipment) accept the counter offer proposed by the carrier. 
	 */
	public ShipmentOffer accept(int shipmentOfferId){
		
		Assert.isTrue(shipmentOfferId != 0, "message.error.shipmentOffer.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.shipmentOffer.onlyUser");
		
		ShipmentOffer shipmentOffer = findOne(shipmentOfferId);		
		Shipment shipment = shipmentOffer.getShipment();
		
		Assert.notNull(shipment, "message.error.shipmentOffer.shipment.mustExist");
		Assert.isTrue(shipmentService.checkDates(shipment), "message.error.shipmentOffer.shipment.checkDates");
		Assert.isTrue(shipment.getDepartureTime().after(new Date()),"The Departure Time must be future");
		Assert.isTrue(shipment.getMaximumArriveTime().after(new Date()),"message.error.shipmentOffer.shipment.maxArrivalTime.future");
		Assert.isTrue(shipment.getCreator().equals(actorService.findByPrincipal()), "message.error.shipmentOffer.accept.user.own");
		Assert.isTrue(!shipmentService.checkShipmentOfferAccepted(shipment.getId()), "message.error.shipmentOffer.accept.alreadyAccepted");

		Assert.isTrue(!shipmentOffer.getAcceptedBySender() && !shipmentOffer.getRejectedBySender(), "message.error.shipmentOffer.notAcceptedOrRejected");
		Assert.isTrue(shipmentOffer.getUser().getIsVerified(), "message.error.shipmentOffer.verifiedCarrier");
		
		/*
		 * More possible constraints:
		 * 1. We look if the vehicle has the package size required by the user.
		 * - As a carrier could have more than one vehicle, we must know the vehicle he wants to use to perform this assert.
		 */
		
		shipment.setCarried(shipmentOffer.getUser()); // Shipment is now linked to the carrier.
		shipment.setPrice(shipmentOffer.getAmount()); // Shipment's price is updated.
		shipmentService.save(shipment);
		
		shipmentOffer.setAcceptedBySender(true); // The offer is accepted
		shipmentOffer.setRejectedBySender(false); // The offer is not rejected.
		shipmentOffer = save(shipmentOffer);
		
		// Now, we reject every other offer.
		
		Collection<ShipmentOffer> remaining = findAllPendingByShipmentId(shipment.getId());
		
		for(ShipmentOffer so:remaining){
			if(!so.getAcceptedBySender()){
				deny(so.getId());
			}
		}
		
		/*
		 * Here comes the notification to the carrier (Still not developed) 
		 */
		
		return shipmentOffer;
		
	}
	
	/**
	 * 
	 * @param shipmentOfferId - The id of the ShipmentOffer
	 * 
	 * The client (the user that created the shipment) deny the counter offer proposed by the carrier. 
	 */
	public void deny(int shipmentOfferId){
		
		Assert.isTrue(shipmentOfferId != 0, "message.error.shipmentOffer.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.shipmentOffer.onlyUser");
		
		ShipmentOffer shipmentOffer = findOne(shipmentOfferId);		
		Shipment shipment = shipmentOffer.getShipment();
		
		Assert.notNull(shipment, "message.error.shipmentOffer.shipment.mustExist");
		Assert.isTrue(shipmentService.checkDates(shipment), "message.error.shipmentOffer.shipment.checkDates");
		Assert.isTrue(shipment.getCreator().equals(actorService.findByPrincipal()), "message.error.shipmentOffer.deny.user.own");

		Assert.isTrue(!shipmentOffer.getAcceptedBySender() && !shipmentOffer.getRejectedBySender(), "message.error.shipmentOffer.notAcceptedOrRejected");
		Assert.isTrue(shipmentOffer.getUser().getIsVerified(), "message.error.shipmentOffer.verifiedCarrier");

		/*
		 * More possible constraints:
		 * 1. We look if the vehicle has the package size required by the user.
		 * - As a carrier could have more than one vehicle, we must know the vehicle he wants to use to perform this assert.
		 */
		
		shipmentOffer.setAcceptedBySender(false);
		shipmentOffer.setRejectedBySender(true); // The offer is rejected.
		save(shipmentOffer);
		
		/*
		 * Here comes the notification to the carrier (Still not developed) 
		 */
		
		Actor sender;
		Actor recipient;
		String subject;
		String body;
		
		sender = shipment.getCreator();
		recipient = shipmentOffer.getUser();
		subject = "Your counteroffer has been denied.";
		body = "The counteroffer you did for a Shipment to carry " + 
				shipment.getItemName() + 
				" from " + 
				shipment.getOrigin() + 
				" to " + 
				shipment.getDestination() + 
				" with a proposed cost of " +
				shipmentOffer.getAmount() + 
				" euros, originally posted by " + 
				shipment.getCreator().getUserAccount().getUsername() + 
				" with a cost of " + 
				shipment.getPrice() + 
				" euros, has been denied.";
		
		messageService.sendMessage(sender, recipient, subject, body);	
		
	}
	
	// IDs could be <= 0 to ignore in the find
	public Page<ShipmentOffer> findAllByOrShipmentIdAndOrUserId(int shipmentId, int userId, Pageable page) {
		Page<ShipmentOffer> result;
		User actUser;
		Assert.isTrue(shipmentId + userId > 0,
				"service.shipmentOffer.findAllByOrShipmentIdAndOrUserId.notShipmentOrUser");

		actUser = userService.findByPrincipal();

		if (shipmentId > 0 && userId <= 0) {
			Shipment actShipment;

			actShipment = shipmentService.findOne(shipmentId);
			if (!actShipment.getCreator().equals(actUser))
				userId = actUser.getId();
		}

		result = shipmentOfferRepository.findAllByShipmentIdAndUserId(shipmentId, userId, page);

		if (result.hasContent()) {
			if (userId > 0 && shipmentId <= 0) {
				Assert.isTrue(result.iterator().next().getUser().equals(actUser),
						"service.shipmentOffer.findAllByOrShipmentIdAndOrUserId.notPermittedUser");
			} else if (!checkPermission(result.iterator().next())) {
				Assert.isTrue(false, "service.shipmentOffer.findAllByOrShipmentIdAndOrUserId.notPermitted");
			}
		}

		return result;
	}

	private boolean checkPermission(ShipmentOffer input) {
		User actUser;

		actUser = userService.findByPrincipal(); // Inside check if it's null

		if (input != null) {
			return actUser.equals(input.getUser()) || actUser.equals(input.getShipment().getCreator());
		} else {
			return false;
		}
	}

}
