package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Shipment;
import domain.ShipmentOffer;
import domain.User;
import repositories.ShipmentRepository;

@Service
@Transactional
public class ShipmentService {
	
	static Logger log = Logger.getLogger(ShipmentService.class);

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShipmentRepository shipmentRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	@Autowired
	private AlertService alertService;
	
	// Constructors -----------------------------------------------------------

	public ShipmentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Shipment create() {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can create a shipment");
		
		Shipment result;
		User user;
		Date date;
		
		result = new Shipment();
		user = userService.findByPrincipal();
		date = new Date();
		
		result.setCreator(user);
		result.setDate(date);
		
		return result;
	}
	
	public Shipment save(Shipment shipment) {
		Assert.notNull(shipment, "message.error.shipment.notNull");
		Assert.isTrue(checkDates(shipment), "message.error.shipment.checkDates");
		Assert.isTrue(checkItemEnvelope(shipment.getItemEnvelope()), "message.error.shipment.checkItemEnvelope");
		
		User user;
		Date date;

		user = userService.findByPrincipal();
		date = new Date();

		if(shipment.getId() == 0) {
			shipment.setCreator(user);
			shipment.setDate(date);
			shipment.setCarried(null);
			shipment = shipmentRepository.save(shipment);
			alertService.checkAlerts(shipment.getOrigin(), shipment.getDestination(), 
					shipment.getDepartureTime(), "Shipment");
		} else {
			shipment = shipmentRepository.save(shipment);
		}
	
		return shipment;
	}
	
	public void delete(Shipment shipment) {
		Assert.notNull(shipment, "message.error.shipment.notNull");
		Assert.isTrue(shipment.getId() != 0, "message.error.shipment.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.shipment.user.delete");
		Assert.isTrue(shipment.getCarried() == null, "message.error.shipment.user.delete.hasCarrier");
		
		User user;
		Collection<ShipmentOffer> shipmentOffers;
		
		user = userService.findByPrincipal();

		Assert.isTrue(user.getId() == shipment.getCreator().getId(), "message.error.shipment.user.delete.own");
		
		shipmentOffers = shipmentOfferService.findAllByShipmentId(shipment.getId());
		for(ShipmentOffer so : shipmentOffers) {
			shipmentOfferService.delete(so.getId());
		}
						
		shipmentRepository.delete(shipment);
	}
	
	public Shipment findOne(int shipmentId) {
		Shipment result;
		
		result = shipmentRepository.findOne(shipmentId);
		
		return result;
	}
	
	public Collection<Shipment> findAll() {
		Collection<Shipment> result;

		result = shipmentRepository.findAll();

		return result;
	}
	
	protected Page<Shipment> findAllByUser(Pageable page){
		Page<Shipment> result;
		User user = userService.findByPrincipal();
				
		result = shipmentRepository.findAllByUserId(user.getId(), page);
		
		return result;
	}
	
	public Page<Shipment> findAllByUserId(int userId, Pageable page){
		Assert.isTrue(userId != 0, "The user must exist");
		
		Page<Shipment> result;
				
		result = shipmentRepository.findAllByUserId(userId, page);
		
		return result;		
	}
	
	public Page<Shipment> findAllByCurrentUser(Pageable page){
		Assert.isTrue(actorService.checkAuthority("USER"), "Only a user can see his own shipments.");
		
		Page<Shipment> result;
		
		result = findAllByUser(page);
		
		return result;
	}
	
	public int countShipmentCreatedByUserId(User user){
		Assert.notNull(user);
		
		int result;
		
		result = shipmentRepository.countShipmentCreatedByUserId(user.getId());
		
		return result;
	}

	
	// Other business methods -------------------------------------------------

	public Page<Shipment> searchShipment(String origin, String destination, String date, String hour, String envelope, String itemSize,Pageable page){
		Assert.isTrue(origin != "" && destination != "");
		Page<Shipment> result;
		SimpleDateFormat formatter;
		Date time;
		Date finalDate;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		time = null;
		finalDate = null;
		
		
		if(date!="" && date!=null){
			try {
				finalDate = formatter.parse(date+" 00:00");
				if(hour!="" && hour!=null){
					time = formatter.parse(date+" "+hour);
				}
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}
		
		log.trace(origin+" - "+destination+" at "+finalDate);
		result = shipmentRepository.searchShipment(origin, destination, finalDate, time, envelope, itemSize,page);
		log.trace(result);
		//System.out.println(result);
		
		return result;
	}
	
	/**
	 * 
	 * @param shipmentId - Shipment's ID
	 * 
	 * The carrier selects a shipment he/she wants to carry.
	 * This takes place in the details view.
	 * The carrier clicks in a button to select the shipment and the creator receives a notification
	 */
	public ShipmentOffer carryShipment(int shipmentId){
		
		Assert.isTrue(shipmentId != 0, "The Shipment's ID must not be zero.");
		Assert.isTrue(actorService.checkAuthority("USER"), "Only a user can carry a shipment.");
				
		Shipment shipment = findOne(shipmentId);
		User carrier = userService.findByPrincipal();
				
		Assert.notNull(shipment, "The ID must match a Shipment.");
		Assert.isNull(shipment.getCarried(), "The shipment must not have a carrier asigned.");
		Assert.isTrue(checkDates(shipment), "All dates must be valid.");
		Assert.isTrue(shipment.getDepartureTime().after(new Date()),"The Departure Time must be future");
		Assert.isTrue(shipment.getMaximumArriveTime().after(new Date()),"The Maximum Arrival Time must be future");
		Assert.notNull(carrier, "The carrier must not be empty.");
		Assert.isTrue(carrier.getIsVerified(), "Only a verified user can carry packages.");
		Assert.isTrue(!carrier.equals(shipment.getCreator()), "You cannot carry your own Shipment.");
		Assert.isTrue(!checkShipmentOfferAccepted(shipmentId), "The creator of the Shipment must not accept any other offer.");
		
		ShipmentOffer shipmentOffer;
		
		shipmentOffer = shipmentOfferService.create(shipmentId);
		shipmentOffer.setAmount(shipment.getPrice());
		shipmentOffer.setDescription("This carrier accepts the conditions of your Shipment");
		
		/*
		 * This may include more sets to the ShipmentOffer.
		 */
		
		shipmentOffer = shipmentOfferService.save(shipmentOffer);
		
		/*
		 * Here comes the notification system (Sprint 2)
		 */
		
		return shipmentOffer;
	}
		
	private boolean checkItemEnvelope(String itemEnvelope) {
		boolean res;
		
		res = false;

		if(itemEnvelope.equals("Open") || itemEnvelope.equals("Closed") ||
				itemEnvelope.equals("Both") || itemEnvelope.equals("Abierto") ||
				itemEnvelope.equals("Cerrado") || itemEnvelope.equals("Ambos")) {
			res = true;
		}

		return res;
	}
	
	public boolean checkDates(Shipment shipment) {
		boolean res;
		
		res = true;
		
		if(shipment.getDate().compareTo(shipment.getDepartureTime()) >= 0) {
			res = false;
		}
		
		if(shipment.getDepartureTime().compareTo(shipment.getMaximumArriveTime()) >= 0) {
			res = false;
		}
		
		return res;
	}
	
	public boolean checkShipmentOfferAccepted(int shipmentId){
		
		boolean res;
		Collection<ShipmentOffer> allShipmentOffersFromShipment;
		
		res = false;
		
		allShipmentOffersFromShipment = shipmentOfferService.findAllByShipmentId2(shipmentId);
				
		for(ShipmentOffer shipmentOffer:allShipmentOffersFromShipment){
			if(shipmentOffer.getAcceptedBySender()){
				res = true;
				break;
			}
		}
		
		return res;
	}
	
}
