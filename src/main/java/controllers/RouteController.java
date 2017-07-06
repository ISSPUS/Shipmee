package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Route;
import domain.RouteOffer;
import domain.Shipment;
import domain.SizePrice;
import domain.User;
import services.ActorService;
import services.RouteOfferService;
import services.RouteService;
import services.ShipmentService;
import services.SizePriceService;
import services.UserService;

@Controller
@RequestMapping("/route")
public class RouteController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private RouteService routeService;
	
	@Autowired
	private SizePriceService sizePriceService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private RouteOfferService routeOfferService;
	
	@Autowired
	private ShipmentService shipmentService;
	// Constructors -----------------------------------------------------------
	
	public RouteController() {
		super();
	}
		
	// Search ------------------------------------------------------------------		

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(String origin, String destination, @RequestParam(required=false) String date,
			@RequestParam(required=false) String hour, @RequestParam(required=false) String envelope,
			@RequestParam(required=false) String itemSize,@RequestParam(required = false, defaultValue = "1") int page) {
		ModelAndView result;
		Page<Route> routes;
		Pageable pageable;
		
		if (itemSize != null && itemSize.equals("")){
			itemSize=null;
		}
		
		if (hour != null && hour.equals("")){
			hour=null;
		}

		pageable = new PageRequest(page - 1, 5);
		
		routes = routeService.searchRoute(origin, destination, date, hour, envelope, itemSize,pageable);
				
		result = new ModelAndView("route/search");
		result.addObject("routes", routes.getContent());
		result.addObject("origin", origin);
		result.addObject("destination", destination);
		result.addObject("form_date", date);
		result.addObject("form_hour", hour);
		result.addObject("form_envelope", envelope);
		result.addObject("form_itemSize", itemSize);
		result.addObject("p", page);
		result.addObject("total_pages", routes.getTotalPages());
		
		String url = getUrlParametros(origin,destination,date,hour,envelope,itemSize);
		result.addObject("urlPage", "route/search.do?"+url+"&page=");
		return result;
	}
	
	private String getUrlParametros(String origin, String destination, String date, String hour, String envelope,
			String itemSize) {
		String url = "";
		Map<String,String> parametrosBusqueda = new HashMap<String,String>();
		parametrosBusqueda.put("origin", origin);
		parametrosBusqueda.put("destination", destination);
		parametrosBusqueda.put("date", date);
		parametrosBusqueda.put("hour", hour);
		parametrosBusqueda.put("envelope", envelope);
		
		for (String clave : parametrosBusqueda.keySet()) {
			String valor = parametrosBusqueda.get(clave);
			if(valor!=null && !valor.equals("")){
				url=url+"&"+clave+"="+valor;
			}
		}
		return url;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView seeThread(@RequestParam int routeId, @RequestParam(required=false, defaultValue="1") int page) {
		ModelAndView result;
		
		result = createListModelAndView(routeId, page);
		
		return result;

	}
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int userId,
			@RequestParam(required=false, defaultValue="1") int page) {
		ModelAndView result;
		Page<Route> routes;
		Pageable pageable;
		User user;
		User currentUser;
		
		pageable = new PageRequest(page - 1, 5);

		routes = routeService.findAllByUserId(userId, pageable);
		user = userService.findOne(userId);
		currentUser = null;
		
		if(actorService.checkAuthority("USER")){
			currentUser = userService.findByPrincipal();
		}
				
		result = new ModelAndView("route/user");
		result.addObject("routes", routes.getContent());
		result.addObject("user", user);
		result.addObject("currentUser", currentUser);
		result.addObject("p", page);
		result.addObject("total_pages", routes.getTotalPages());
		
		return result;
	}		
	
	private ModelAndView createListModelAndView(int routeId, @RequestParam(required=false, defaultValue="1") int page){
		ModelAndView result;
		Route route;
		Collection<SizePrice> sizePrices;
		Collection<RouteOffer> routeOffers;
		User currentUser;
		Boolean routeOffersIsEmpty;
		Page<Shipment> shipmentsPage;
		Pageable pageable;
		Collection<Shipment> shipments;
		
		route = routeService.findOne(routeId);
		sizePrices = sizePriceService.findAllByRouteId(routeId);
		currentUser = null;
		routeOffersIsEmpty = false;
		shipments = new ArrayList<Shipment>();
		
		routeOffers = routeOfferService.findAllByRouteId(routeId);
		
		if(routeOffers.isEmpty()) {
			routeOffersIsEmpty = true;
		}
		
		if(actorService.checkAuthority("ADMIN")){
			currentUser = userService.findOne(route.getCreator().getId());
		}else if (actorService.checkAuthority("USER")){
			currentUser = userService.findByPrincipal();
			
			pageable = new PageRequest(page - 1, 5);
			shipmentsPage = shipmentService.findAllAvailableByCurrentUser(pageable);
			shipments = shipmentsPage.getContent();
		}
		
		String departureTime = new SimpleDateFormat("dd'/'MM'/'yyyy").format(route.getDepartureTime());
		String departureTimeHour = new SimpleDateFormat("HH':'mm").format(route.getDepartureTime());
		
		String arriveTime = new SimpleDateFormat("dd'/'MM'/'yyyy").format(route.getArriveTime());
		String arriveTimeHour = new SimpleDateFormat("HH':'mm").format(route.getArriveTime());


		result = new ModelAndView("route/display");
		result.addObject("route", route);
		result.addObject("departureTime", departureTime);
		result.addObject("departureTime_hour", departureTimeHour);
		result.addObject("arriveTime", arriveTime);
		result.addObject("arriveTime_hour", arriveTimeHour);
		result.addObject("sizePrices", sizePrices);
		result.addObject("user", currentUser);
		result.addObject("routeOffersIsEmpty", routeOffersIsEmpty);
		result.addObject("shipments", shipments);
		
		return result;
	}
}