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
import domain.Route;
import domain.RouteOffer;
import domain.User;
import repositories.RouteOfferRepository;

@Service
@Transactional
public class RouteOfferService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RouteOfferRepository routeOfferRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired	
	private UserService userService;

	@Autowired
	private RouteService routeService;
	
	@Autowired
	private MessageService messageService;

	// Constructors -----------------------------------------------------------

	public RouteOfferService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public RouteOffer create(int routeId) {
		RouteOffer res;
		Route route;

		route = routeService.findOne(routeId);
		Assert.notNull(route, "message.error.routeOffer.route.mustExists");

		res = new RouteOffer();
		res.setRoute(route);
		res.setUser(userService.findByPrincipal());

		return res;
	}

	public RouteOffer createFromClone(int routeOfferId) {
		RouteOffer res;
		RouteOffer act;
		act = this.findOne(routeOfferId);
		Assert.notNull(act, "message.error.routeOffer.mustExists");

		res = this.create(act.getRoute().getId());
		res.setAmount(act.getAmount());
		res.setDescription(act.getDescription());

		return res;
	}

	public RouteOffer save(RouteOffer input) {
		User actUser;
		RouteOffer tmp;

		Assert.notNull(input, "message.error.routeOffer.mustExists");

		actUser = userService.findByPrincipal();

		if (actUser.equals(input.getUser())) { // User that create route
			if (input.getId() != 0) {
				tmp = this.findOne(input.getId());
				Assert.notNull(tmp, "message.error.routeOffer.save.dontFindID");
				Assert.isTrue(tmp.getUser().equals(actUser), "message.error.routeOffer.save.user.own");
				Assert.isTrue(!tmp.getAcceptedByCarrier() && !tmp.getRejectedByCarrier(),
						"message.error.routeOffer.notAcceptedOrRejected");
			} else {
				tmp = this.create(input.getRoute().getId());
			}

			tmp.setAmount(input.getAmount());
			tmp.setDescription(input.getDescription());
		} else if (actUser.equals(input.getRoute().getCreator())) { // User that
																	// put the
																	// offer
			Assert.isTrue(input.getId() != 0, "service.routeOffer.save.ProposerCreating"); // The
																							// routeCreator
																							// can't

			tmp = this.findOne(input.getId());
			Assert.notNull(tmp, "message.error.routeOffer.save.dontFindID");
			Assert.isTrue(tmp.getRoute().getCreator().equals(actUser), "message.error.routeOffer.save.user.own");
			tmp.setAcceptedByCarrier(input.getAcceptedByCarrier());
			tmp.setRejectedByCarrier(input.getRejectedByCarrier());
		} else {
			Assert.isTrue(false, "routeOffer.commit.error");
			return null;
		}
		Assert.isTrue(!tmp.getUser().equals(tmp.getRoute().getCreator()),
				"message.error.routeOffer.equalCreatorAndProposer");
		Assert.isTrue(tmp.getRoute().getArriveTime().after(new Date()),
				"message.error.routeOffer.route.arrivalTime.future");
		
		tmp = routeOfferRepository.save(tmp);

		return tmp;
	}

	public void delete(int routeOfferId) {
		RouteOffer a;

		a = this.findOne(routeOfferId);

		Assert.isTrue(this.checkPermission(a), "service.routeOffer.delete.notPermitted");

		routeOfferRepository.delete(a);
	}

	public RouteOffer findOne(int routeOfferId) {
		RouteOffer result;
		result = routeOfferRepository.findOne(routeOfferId);

		if (!this.checkPermission(result)) {
			result = null;
		}

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<RouteOffer> findAllByRouteId(int routeId) {
		Collection<RouteOffer> result;
		User actUser;

		actUser = userService.findByPrincipal();

		result = routeOfferRepository.findAllByRouteId(routeId);

		if (!result.isEmpty()) {
			Assert.isTrue(result.iterator().next().getRoute().getCreator().equals(actUser),
					"service.routeOffer.delete.notPermitted");
		}

		return result;
	}
	
	public Collection<RouteOffer> findAllPendingByRouteId(int routeId){
		Collection<RouteOffer> result;
		
		result = routeOfferRepository.findAllPendingByRouteId(routeId);
		
		return result;
	}
	
	/**
	 * 
	 * @param routeOfferId - The if of the RouteOffer
	 * 
	 * The carrier (the user that created the route) accept the counter offer proposed by the client.
	 */
	public void accept(int routeOfferId){
		
		Assert.isTrue(routeOfferId != 0, "message.error.routeOffer.mustExists");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.routeOffer.onlyUser");
		
		RouteOffer routeOffer = findOne(routeOfferId);
		Route route = routeOffer.getRoute();
		
		Assert.notNull(route, "message.error.routeOffer.route.mustExists");
		Assert.isTrue(routeService.checkDates(route), "message.error.routeOffer.route.checkDates");
		Assert.isTrue(route.getDepartureTime().after(new Date()), "message.error.routeOffer.route.departureTime.future");
		Assert.isTrue(route.getArriveTime().after(new Date()), "message.error.routeOffer.route.arrivalTime.future");
		Assert.isTrue(route.getCreator().equals(actorService.findByPrincipal()), "message.error.routeOffer.accept.user.own");
		Assert.isTrue(route.getCreator().getIsVerified(), "message.error.must.verified");

		Assert.isTrue(!routeOffer.getAcceptedByCarrier() && !routeOffer.getRejectedByCarrier(), "message.error.routeOffer.notAcceptedOrRejected");		
		
		routeOffer.setAcceptedByCarrier(true); // The offer is accepted.
		routeOffer.setRejectedByCarrier(false); // The offer is not rejected.
		save(routeOffer);
		
		/*
		 * Here comes the notification to the carrier (Still not developed) 
		 */
		
	}
	
	/**
	 * 
	 * @param routeOfferId - The id of the RouteOffer
	 * 
	 * The carrier (the user that created the route) deny the counter offer proposed by the client.
	 */
	public void deny(int routeOfferId){
		
		Assert.isTrue(routeOfferId != 0, "message.error.routeOffer.mustExists");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.routeOffer.onlyUser");
		
		RouteOffer routeOffer = findOne(routeOfferId);
		Route route = routeOffer.getRoute();
		
		Assert.notNull(route, "message.error.routeOffer.route.mustExists");
		Assert.isTrue(routeService.checkDates(route), "message.error.routeOffer.route.checkDates");
		Assert.isTrue(route.getCreator().equals(actorService.findByPrincipal()), "message.error.routeOffer.deny.user.own");
		Assert.isTrue(route.getCreator().getIsVerified(), "message.error.must.verified");

		Assert.isTrue(!routeOffer.getAcceptedByCarrier() && !routeOffer.getRejectedByCarrier(), "message.error.routeOffer.notAcceptedOrRejected");
		
		routeOffer.setAcceptedByCarrier(false); // The offer is not accepted.
		routeOffer.setRejectedByCarrier(true); // The offer is rejected.
		save(routeOffer);
		
		/*
		 * Here comes the notification to the carrier (Still not developed) 
		 */
		
		Actor sender;
		Actor recipient;
		String subject;
		String body;
		
		sender = route.getCreator();
		recipient = routeOffer.getUser();
		subject = "Your counteroffer has been denied.";
		body = "The counteroffer you did for a Route" + 				
				" from " + 
				route.getOrigin() + 
				" to " + 
				route.getDestination() + 
				" with a proposed cost of " +
				routeOffer.getAmount() + 
				" euros, originally posted by " + 
				route.getCreator().getUserAccount().getUsername() + 
				", has been denied.";
		
		messageService.sendMessage(sender, recipient, subject, body);
	
	}
	
	// IDs could be <= 0 to ignore in the find
	public Page<RouteOffer> findAllByOrRouteIdAndOrUserId(int routeId, int userId, Pageable page) {
		Page<RouteOffer> result;
		User actUser;
		Assert.isTrue(routeId + userId > 0, "service.routeOffer.findAllByOrRouteIdAndOrUserId.notRouteOrUser");

		actUser = userService.findByPrincipal();

		if (routeId > 0	&& userId <= 0) {
			Route actRoute;

			actRoute = routeService.findOne(routeId);
			if (!actRoute.getCreator().equals(actUser))
				userId = actUser.getId();
		}

		result = routeOfferRepository.findAllByRouteIdAndUserId(routeId, userId, page);

		if (result.hasContent()) {
			if (userId > 0 && routeId <= 0) {
				Assert.isTrue(result.iterator().next().getUser().equals(actUser),
						"service.routeOffer.findAllByOrShipmentIdAndOrUserId.notPermittedUser");
			} else if (!checkPermission(result.iterator().next())) {
				Assert.isTrue(false, "service.routeOffer.findAllByOrShipmentIdAndOrUserId.notPermitted");
			}
		}

		return result;
	}

	private boolean checkPermission(RouteOffer input) {
		User actUser;

		actUser = userService.findByPrincipal(); // Inside check if it's null

		if (input != null) {
			return actUser.equals(input.getUser()) || actUser.equals(input.getRoute().getCreator());
		} else {
			return false;
		}
	}

}
