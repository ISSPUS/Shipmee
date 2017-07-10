package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.FundTransferPreference;
import domain.RouteOffer;
import domain.Shipment;
import domain.ShipmentOffer;
import domain.User;
import repositories.ShipmentOfferRepository;
import utilities.PayPalConfig;

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
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private RouteOfferService routeOfferService;
		
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
		Assert.isTrue(userService.findByPrincipal().getIsVerified(), "message.error.shipmentOffer.verifiedCarrier");

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
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String url;

		Assert.notNull(input, "message.error.shipmentOffer.mustExist");

		actUser = userService.findByPrincipal();

		if (actUser.equals(input.getUser())) { // User that put the offer
			if (input.getId() != 0) {
				tmp = this.findOne(input.getId());
				Assert.notNull(tmp, "message.error.shipmentOffer.save.dontFindID");
				Assert.isTrue(tmp.getUser().equals(actUser), "message.error.shipmentOffer.save.user.own");
//				Assert.isTrue(!tmp.getAcceptedBySender() && !tmp.getRejectedBySender(),
//						"message.error.shipmentOffer.notAcceptedOrRejected"); // Not neccessary.
				// Conflictive with autoDenyRelatedOffersNotAcceptedYet when accept from routeOffer 
			} else {
				tmp = this.create(input.getShipment().getId());
				
				url = PayPalConfig.getUrlBase()+"/shipmentOffer/user/list.do?shipmentId="+input.getShipment().getId();

				String[] args_body = {input.getShipment().getOrigin(), input.getShipment().getDestination(), dateFormat.format(input.getShipment().getDate()),url};
				
				messageService.sendMessage(actorService.findByUsername("shipmee"), input.getShipment().getCreator(),
						messageSource.getMessage("shipment.offer.alert.subject", null, new Locale(input.getShipment().getCreator().getLocalePreferences())), 
						messageSource.getMessage("shipment.offer.alert.body", args_body, new Locale(input.getShipment().getCreator().getLocalePreferences())));
			}

			tmp.setAmount(input.getAmount());
			tmp.setDescription(input.getDescription());
			tmp.setRejectedBySender(input.getRejectedBySender()); // Neccesary for autoDenyRelatedOffersNotAcceptedYet doing by oneself
		} else if (actUser.equals(input.getShipment().getCreator())) { // User
																		// that
																		// create
																		// the
																		// shipment
			Assert.isTrue(input.getId() != 0, "service.shipmentOffer.save.ProposerCreating"); // The
																								// shipmentCreator
																								// can't

			tmp = this.findOne(input.getId());
			Assert.notNull(tmp, "message.error.shipmentOffer.save.dontFindID");
			Assert.isTrue(tmp.getShipment().getCreator().equals(actUser), "message.error.shipmentOffer.save.user.own");
			tmp.setAcceptedBySender(input.getAcceptedBySender());
			tmp.setRejectedBySender(input.getRejectedBySender());
		} else { // Other user denied offers
			tmp = shipmentOfferRepository.findOne(input.getId());
			Assert.notNull(tmp, "message.error.shipmentOffer.save.dontFindID");
			tmp.setRejectedBySender(input.getRejectedBySender());
		}
		Assert.isTrue(tmp.getUser().getIsVerified(), "message.error.shipmentOffer.verifiedCarrier");
		Assert.isTrue(!tmp.getUser().equals(tmp.getShipment().getCreator()),
				"message.error.shipmentOffer.equalCreatorAndProposer");
		Assert.isTrue(tmp.getShipment().getMaximumArriveTime().after(new Date()),
				"message.error.shipmentOffer.shipment.maxArrivalTime.future");
		Assert.isTrue(isValidMethodPayment(tmp.getUser().getFundTransferPreference()),
				"message.error.shipmentOffer.fundTransferPreference");
		
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

		result = shipmentOfferRepository.findAllByShipmentId(shipmentId);

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
		Assert.isTrue(shipmentService.checkFutureDepartureDate(shipment), "message.error.shipment.checkFutureDepartureDate");
		Assert.isTrue(shipmentService.checkMaximumArriveTimeAfterDepartureDate(shipment), "message.error.shipment.checkMaximumArriveTimeAfterDepartureDate");
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
		
		this.autoDenyRelatedOffersNotAcceptedYet(shipment.getId());
		
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
		
		ShipmentOffer shipmentOffer = findOne(shipmentOfferId);
		Assert.notNull(shipmentOffer);
		
		Assert.isTrue(shipmentOffer.getShipment().getCreator().equals(actorService.findByPrincipal()), "message.error.shipmentOffer.deny.user.own");

		this.internalDeny(shipmentOffer);
	}
	
	private void internalDeny(ShipmentOffer shipmentOffer){
		
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.shipmentOffer.onlyUser");
		Assert.notNull(shipmentOffer);
		
		Shipment shipment = shipmentOffer.getShipment();
		
		Assert.notNull(shipment, "message.error.shipmentOffer.shipment.mustExist");
		Assert.isTrue(shipmentService.checkFutureDepartureDate(shipment), "message.error.shipment.checkFutureDepartureDate");
		Assert.isTrue(shipmentService.checkMaximumArriveTimeAfterDepartureDate(shipment), "message.error.shipment.checkMaximumArriveTimeAfterDepartureDate");
		// Assert.isTrue(shipment.getCreator().equals(actorService.findByPrincipal()), "message.error.shipmentOffer.deny.user.own");

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
		
		messageService.autoMessageDenyShipmentOffer(shipmentOffer);
	}
	
	/**
	 * Deny all Offer from a Shipment not Accepted yet. It consider shipmentOffers and routeOffers related with the given shipment
	 * @param shipmentId
	 */
	public void autoDenyRelatedOffersNotAcceptedYet(int shipmentId){

		// First, we reject every shipmentOffer pendind.
		Collection<ShipmentOffer> remainingSO = findAllPendingByShipmentId(shipmentId);
		
		for(ShipmentOffer so:remainingSO){
			if(!so.getAcceptedBySender()){
//				deny(so.getId());
				this.internalDeny(so);
			}
		}
		
		// Finally we reject every routeOffer pending related with the shipment.
		
		Collection<RouteOffer> remainingRO = routeOfferService.findAllPendingByShipmentId(shipmentId);

		for(RouteOffer ro: remainingRO){
			routeOfferService.internalDeny(ro);
		}
		
		
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
	public boolean isValidMethodPayment(FundTransferPreference form){
		return (form.getAccountHolder() != null && !form.getAccountHolder().equals("")) &&
		(form.getBankName() != null && !form.getBankName().equals("")) &&
		(form.getIBAN() != null && !form.getIBAN().equals("")) &&
		(form.getBIC() != null && !form.getBIC().equals("")) || 
		(form.getPaypalEmail() != null && !form.getPaypalEmail().equals(""));
	}
}
