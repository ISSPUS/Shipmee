package services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.paypal.base.rest.PayPalRESTException;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;

import domain.PayPalObject;
import domain.Route;
import domain.RouteOffer;
import domain.Shipment;
import domain.SizePrice;
import domain.User;
import repositories.RouteOfferRepository;
import utilities.PayPalConfig;

@Service
@Transactional
public class RouteOfferService {
	
	static Logger log = Logger.getLogger(RouteOfferService.class);


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
	
	@Autowired
	private PayPalService payPalService;
	
	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private SizePriceService sizePriceService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;

	// Constructors -----------------------------------------------------------

	public RouteOfferService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public RouteOffer create(int routeId, int shipmentId) {
		RouteOffer res;
		Route route;
		Shipment shipment;
		Collection<SizePrice> sizePrices;

		route = routeService.findOne(routeId);
		Assert.notNull(route, "message.error.routeOffer.route.mustExist");

		res = new RouteOffer();
		res.setRoute(route);
		if(shipmentId != 0) {
			shipment = shipmentService.findOne(shipmentId);
			Assert.notNull(shipment, "message.error.shipmentOffer.shipment.mustExist");
			
			res.setShipment(shipment);
			
			sizePrices = sizePriceService.findAllByRouteId(routeId);
			for(SizePrice sizePrice : sizePrices) {
				if(sizePrice.getSize().equals(shipment.getItemSize())) {
					if(sizePrice.getPrice() < shipment.getPrice()) {
						res.setAmount(sizePrice.getPrice());
					} else {
						res.setAmount(shipment.getPrice());
					}
					break;
				}
			}
		}
		
		res.setUser(userService.findByPrincipal());

		return res;
	}

	public RouteOffer createFromClone(int routeOfferId) {
		RouteOffer res;
		RouteOffer act;
		act = this.findOne(routeOfferId);
		Assert.notNull(act, "message.error.routeOffer.mustExist");

		res = this.create(act.getRoute().getId(), act.getShipment().getId());
		res.setAmount(act.getAmount());
		res.setDescription(act.getDescription());

		return res;
	}

	public RouteOffer save(RouteOffer input) {
		User actUser;
		RouteOffer tmp;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String url;

		Assert.notNull(input, "message.error.routeOffer.mustExist");

		actUser = userService.findByPrincipal();

		if (actUser.equals(input.getUser())) { // User that put the offer
			if (input.getId() != 0) {
				tmp = this.findOne(input.getId());
				Assert.notNull(tmp, "message.error.routeOffer.save.dontFindID");
				Assert.isTrue(tmp.getUser().equals(actUser), "message.error.routeOffer.save.user.own");
				// Assert.isTrue(!tmp.getAcceptedByCarrier() && !tmp.getRejectedByCarrier(),
				// 		"message.error.routeOffer.notAcceptedOrRejected"); // Not neccessary.
				// Conflictive with autoDenyRelatedOffersNotAcceptedYet when accept from shipmentOffer 

			} else {
				if(input.getShipment() != null) {
					tmp = this.create(input.getRoute().getId(), input.getShipment().getId());
				} else {
					tmp = this.create(input.getRoute().getId(), 0);
				}
				
				url = PayPalConfig.getUrlBase()+"/routeOffer/user/list.do?routeId="+input.getRoute().getId();

				String[] args_body = {input.getRoute().getOrigin(), input.getRoute().getDestination(), dateFormat.format(input.getRoute().getDate()),url};
				
				messageService.sendMessage(actorService.findByUsername("shipmee"), input.getRoute().getCreator(),
						messageSource.getMessage("route.offer.alert.subject", null, new Locale(input.getRoute().getCreator().getLocalePreferences())), 
						messageSource.getMessage("route.offer.alert.body", args_body, new Locale(input.getRoute().getCreator().getLocalePreferences())));
			}

			tmp.setAmount(input.getAmount());
			tmp.setDescription(input.getDescription());
			tmp.setShipment(input.getShipment());
			tmp.setRejectedByCarrier(input.getRejectedByCarrier());
			
			if(tmp.getShipment() != null) {
				Assert.isTrue(actUser.getId() == tmp.getShipment().getCreator().getId());
			}
			
		} else if (actUser.equals(input.getRoute().getCreator())) { // User that
																	// create
																	// the route
			Assert.isTrue(input.getId() != 0, "service.routeOffer.save.ProposerCreating"); // The
																							// routeCreator
																							// can't

			tmp = this.findOne(input.getId());
			Assert.notNull(tmp, "message.error.routeOffer.save.dontFindID");
			Assert.isTrue(tmp.getRoute().getCreator().equals(actUser), "message.error.routeOffer.save.user.own");
			tmp.setAcceptedByCarrier(input.getAcceptedByCarrier());
			tmp.setRejectedByCarrier(input.getRejectedByCarrier());
		} else {
			tmp = routeOfferRepository.findOne(input.getId());
			Assert.notNull(tmp, "message.error.routeOffer.save.dontFindID");
			tmp.setRejectedByCarrier(input.getRejectedByCarrier());
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
	
	public Collection<RouteOffer> findAll() {
		Collection<RouteOffer> result;

		result = routeOfferRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<RouteOffer> findAllByRouteId(int routeId) {
		Collection<RouteOffer> result;

		result = routeOfferRepository.findAllByRouteId(routeId);

		return result;
	}
	
	public Collection<RouteOffer> findAllPendingByRouteId(int routeId){
		Collection<RouteOffer> result;
		
		result = routeOfferRepository.findAllPendingByRouteId(routeId);
		
		return result;
	}
	
	public Collection<RouteOffer> findAllPendingByShipmentId(int shipmentId){
		Collection<RouteOffer> result;
		
		result = routeOfferRepository.findAllPendingByShipmentId(shipmentId);
		
		return result;
	}
	
	/**
	 * 
	 * @param routeOfferId - The if of the RouteOffer
	 * 
	 * The carrier (the user that created the route) accept the counter offer proposed by the client.
	 */
	public void accept(int routeOfferId){
		
		Assert.isTrue(routeOfferId != 0, "message.error.routeOffer.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.routeOffer.onlyUser");
		
		RouteOffer routeOffer = findOne(routeOfferId);
		Route route = routeOffer.getRoute();
		Shipment shipment;
		User user;
		PayPalObject po;
		
		user = userService.findByPrincipal();
		
		Assert.notNull(route, "message.error.routeOffer.route.mustExist");
		Assert.isTrue(routeService.checkFutureDepartureDate(route), "message.error.route.checkFutureDepartureDate");
		Assert.isTrue(routeService.checkArriveTimeAfterDepartureDate(route), "message.error.route.checkArriveTimeAfterDepartureDate");
		Assert.isTrue(route.getDepartureTime().after(new Date()), "message.error.routeOffer.route.departureTime.future");
		Assert.isTrue(route.getArriveTime().after(new Date()), "message.error.routeOffer.route.arrivalTime.future");
		Assert.isTrue(route.getCreator().equals(actorService.findByPrincipal()), "message.error.routeOffer.accept.user.own");
		Assert.isTrue(route.getCreator().getIsVerified(), "message.error.must.verified");
		
		po = payPalService.findByRouteOfferId(routeOfferId);
		Assert.isTrue(po == null || po.getPayStatus().equals("COMPLETED"), "message.error.routeOffer.tryToAcceptNotPayOffer");

		Assert.isTrue(!routeOffer.getAcceptedByCarrier() && !routeOffer.getRejectedByCarrier(), "message.error.routeOffer.notAcceptedOrRejected");		
		
		routeOffer.setAcceptedByCarrier(true); // The offer is accepted.
		routeOffer.setRejectedByCarrier(false); // The offer is not rejected.
		
		if(routeOffer.getShipment() != null) {
			shipment = routeOffer.getShipment();
			
			shipment.setCarried(user);
			shipment = shipmentService.save(shipment);
			
			shipmentOfferService.autoDenyRelatedOffersNotAcceptedYet(shipment.getId());
		}
		
		save(routeOffer);
		
		/*
		 * Here comes the notification to the carrier (Still not developed) 
		 */
		
		messageService.autoMessageAcceptRouteOffer(routeOffer);
		
	}
	
	
	/**
	 * 
	 * @param routeOfferId - The id of the RouteOffer
	 * 
	 * The carrier (the user that created the route) deny the counter offer proposed by the client.
	 */
	public void deny(int routeOfferId){
		
		Assert.isTrue(routeOfferId != 0, "message.error.routeOffer.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.routeOffer.onlyUser");
		
		RouteOffer routeOffer = routeOfferRepository.findOne(routeOfferId);
		Route route = routeOffer.getRoute();
		
		Assert.isTrue(route.getCreator().equals(actorService.findByPrincipal()), "message.error.routeOffer.deny.user.own");
		Assert.isTrue(route.getCreator().getIsVerified(), "message.error.must.verified");

		Assert.isTrue(!routeOffer.getAcceptedByCarrier() && !routeOffer.getRejectedByCarrier(), "message.error.routeOffer.notAcceptedOrRejected");

		this.internalDeny(routeOffer);
	}
	
	public void internalDeny(RouteOffer routeOffer){
		Assert.notNull(routeOffer);
		
		Assert.notNull(routeOffer.getRoute(), "message.error.routeOffer.route.mustExist");
		Assert.isTrue(routeService.checkFutureDepartureDate(routeOffer.getRoute()), "message.error.route.checkFutureDepartureDate");
		Assert.isTrue(routeService.checkArriveTimeAfterDepartureDate(routeOffer.getRoute()), "message.error.route.checkArriveTimeAfterDepartureDate");

		PayPalObject po = payPalService.findByRouteOfferId(routeOffer.getId());

		if(po != null){
			Assert.isTrue(po.getPayStatus().equals("COMPLETED"), "message.error.routeOffer.tryToAcceptNotPayOffer");
			
			try {
				payPalService.refundToSender(po.getFeePayment().getId());
			} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
					| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
					| OAuthException | PayPalRESTException | IOException | InterruptedException e) {
				log.error(e, e.getCause());
				Assert.isTrue(false, "RouteOfferService.deny.error.RefundToSender");
			}
		}
		
		routeOffer.setAcceptedByCarrier(false); // The offer is not accepted.
		routeOffer.setRejectedByCarrier(true); // The offer is rejected.
		save(routeOffer);
		
		/*
		 * Here comes the notification to the carrier (Still not developed) 
		 */
		
		messageService.autoMessageDenyRouteOffer(routeOffer);
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
