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

import domain.Route;
import domain.RouteOffer;
import domain.SizePrice;
import domain.User;
import repositories.RouteRepository;

@Service
@Transactional
public class RouteService {
	
	static Logger log = Logger.getLogger(RouteService.class);

	// Managed repository -----------------------------------------------------

	@Autowired
	private RouteRepository routeRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SizePriceService sizePriceService;
	
	@Autowired
	private RouteOfferService routeOfferService;
	
	@Autowired
	private AlertService alertService;
	
	// Constructors -----------------------------------------------------------

	public RouteService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Route create() {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can create a route");
		
		Route result;
		User user;
		Date date;
		
		result = new Route();
		user = userService.findByPrincipal();
		date = new Date();
		
		result.setCreator(user);
		result.setDate(date);
		
		return result;
	}
	
	public Route save(Route route) {
		Assert.notNull(route, "message.error.route.notNull");
		Assert.isTrue(route.getArriveTime().after(new Date()), "message.error.route.past");
		Assert.isTrue(checkDates(route), "message.error.route.checkDates");
		Assert.isTrue(checkItemEnvelope(route.getItemEnvelope()), "message.error.route.checkItemEnvelope");

		if(route.getVehicle() != null) {
			Assert.isTrue(route.getCreator().getId() == route.getVehicle().getUser().getId(), "message.error.route.vehicle");
		}

		User user;
		Date date;
		
		user = userService.findByPrincipal();
		date = new Date();
		
		Assert.isTrue(user.getId() == route.getCreator().getId(), "message.error.route.save.user.own");
		Assert.isTrue(user.getIsVerified() == true, "message.error.route.save.user.verified");
		Assert.isTrue(user.getFundTransferPreference() != null);
		
		if(route.getId() == 0) {
			route.setCreator(user);
			route.setDate(date);
			
			route = routeRepository.save(route);
			alertService.checkAlerts(route.getOrigin(), route.getDestination(), 
					route.getDepartureTime(), "Route");
		} else {
			route = routeRepository.save(route);
		}
		
		
			
		return route;
	}
	
	public void delete(Route route) {
		Assert.notNull(route, "message.error.route.notNull");
		Assert.isTrue(route.getId() != 0, "message.error.route.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.route.delete.user");

		User user;
		Collection<User> users;
		Collection<SizePrice> sizePrices;
		Collection<RouteOffer> routeOffers;
		
		user = userService.findByPrincipal();
		users = userService.findAllByRoutePurchased(route.getId());

		Assert.isTrue(user.getId() == route.getCreator().getId(), "message.error.route.delete.user.own");
		Assert.isTrue(users.isEmpty(), "message.error.route.delete.noPurchasers");

		sizePrices = sizePriceService.findAllByRouteId(route.getId());
		for(SizePrice s : sizePrices) {
			sizePriceService.delete(s);
		}
		
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		for(RouteOffer ro : routeOffers) {
			routeOfferService.delete(ro.getId());
		}
						
		routeRepository.delete(route);
	}
	
	public Route findOne(int routeId) {
		Route result;
		
		result = routeRepository.findOne(routeId);
		
		return result;
	}
	
	public Collection<Route> findAll() {
		Collection<Route> result;

		result = routeRepository.findAll();

		return result;
	}
	
	protected Page<Route> findAllByUser(Pageable page){
		Page<Route> result;
		User user = userService.findByPrincipal();
		
		result = routeRepository.findAllByUserId(user.getId(), page);
		
		return result;
	}
	
	public Page<Route> findAllByUserId(int userId, Pageable page){
		Assert.isTrue(userId != 0, "The user must exist");
		
		Page<Route> result;
		
		result = routeRepository.findAllByUserId(userId, page);
		
		return result;
	}
	
	public Page<Route> findAllByCurrentUser(Pageable page){
		Assert.isTrue(actorService.checkAuthority("USER"), "Only a user can see his own routes.");
		
		Page<Route> result;
		
		result = findAllByUser(page);
		
		return result;
	}
	
	public int countRouteCreatedByUserId(User user){
		Assert.notNull(user);
		
		int result;
		
		result = routeRepository.countRouteCreatedByUserId(user.getId());
		
		return result;
	}

	// Other business methods -------------------------------------------------
	
	public Page<Route> searchRoute(String origin, String destination, String date, String hour, String envelope, String itemSize,Pageable page){
		Assert.isTrue(!origin.equals("") && !destination.equals(""));
		Page<Route> result;
		SimpleDateFormat formatter;
		Date time;
		Date finalDate;
		Date moment;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		time = null;
		finalDate = null;
		moment = new Date();
		
		if(date!=null && !date.equals("")){
			try {
				finalDate = formatter.parse(date+" 00:00");
				if(hour!=null && !hour.equals("")){
					time = formatter.parse(date+" "+hour);
				}
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}
		
		log.trace(origin+" - "+destination+" at "+finalDate);
		result = routeRepository.searchRoute(origin, destination, finalDate, time, envelope, itemSize, moment, page);
		log.trace(result);
		return result;
	}
	
	public RouteOffer contractRoute(int routeId, int sizePriceId){
		
		Assert.isTrue(routeId != 0, "The Route's ID must not be zero.");
		Assert.isTrue(actorService.checkAuthority("USER"), "Only a user can carry a shipment.");
		
		Route route = findOne(routeId);
		User client = userService.findByPrincipal();
		SizePrice sizePrice = sizePriceService.findOne(sizePriceId);
		
		Assert.notNull(route, "The ID must match a Route.");
		Assert.isTrue(userService.findAllByRoutePurchased(routeId).isEmpty());
		Assert.isTrue(checkDates(route));
		Assert.isTrue(route.getDepartureTime().after(new Date()), "The Departure Time must be future");
		Assert.isTrue(route.getArriveTime().after(new Date()), "The Arrival Time must be future");
		Assert.notNull(client, "The client must not be empty.");
		Assert.isTrue(!client.equals(route.getCreator()), "You cannot contract your own route.");
		Assert.notNull(sizePrice, "The pair of a Size and its Price must not be null");
		Assert.notNull(sizePrice.getSize(), "The Size must not be null");
		Assert.isTrue(route.equals(sizePrice.getRoute()), "The Size and Price must refer to the same route");
		
		RouteOffer routeOffer;
		
		routeOffer = routeOfferService.create(routeId);
		routeOffer.setAmount(sizePrice.getPrice());
		routeOffer.setDescription("This client accepts the conditions of your Route");
		
		/*
		 * This may include more sets to the RouteOffer.
		 */
		
		routeOffer = routeOfferService.save(routeOffer);
		
		/*
		 * Here comes the notification system (Sprint 2)
		 */
		
		return routeOffer;
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
	
	public boolean checkDates(Route route) {
		boolean res;
		
		res = true;
		
		if(route.getDate().compareTo(route.getDepartureTime()) >= 0) {
			res = false;
		}
		
		if(route.getDepartureTime().compareTo(route.getArriveTime()) >= 0) {
			res = false;
		}
		
		return res;
	}
	
}
