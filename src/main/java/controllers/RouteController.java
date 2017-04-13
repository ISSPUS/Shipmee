package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;

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
import domain.SizePrice;
import domain.User;
import services.ActorService;
import services.RouteService;
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
	// Constructors -----------------------------------------------------------
	
	public RouteController() {
		super();
	}
		
	// Search ------------------------------------------------------------------		

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(String origin, String destination, @RequestParam(required=false) String date,
			@RequestParam(required=false) String hour, @RequestParam(required=false) String envelope,
			@RequestParam(required=false) String itemSize) {
		ModelAndView result;
		Collection<Route> routes;
		
		routes = routeService.searchRoute(origin, destination, date, hour, envelope, itemSize);
				
		result = new ModelAndView("route/search");
		result.addObject("routes", routes);
		result.addObject("origin", origin);
		result.addObject("destination", destination);

		return result;
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView seeThread(@RequestParam int routeId) {
		ModelAndView result;
		
		result = createListModelAndView(routeId);
		
		return result;

	}
	

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int userId,
			@RequestParam(required=false, defaultValue="1") int page) {
		ModelAndView result;
		Page<Route> routes;
		Pageable pageable;
		User user;
		
		pageable = new PageRequest(page - 1, 5);

		routes = routeService.findAllByUserId(userId, pageable);
		user = userService.findOne(userId);
				
		result = new ModelAndView("route/user");
		result.addObject("routes", routes.getContent());
		result.addObject("user", user);
		result.addObject("p", page);
		result.addObject("total_pages", routes.getTotalPages());
		
		return result;
	}		
	
	private ModelAndView createListModelAndView(int routeId){
		ModelAndView result;
		Route route;
		Collection<SizePrice> sizePrices;
		User currentUser;
		
		route = routeService.findOne(routeId);
		sizePrices = sizePriceService.findAllByRouteId(routeId);
		currentUser = null;
		
		if(actorService.checkLogin()){
			currentUser = userService.findByPrincipal();
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
		
		return result;
	}
}