package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Route;
import domain.SizePrice;
import services.RouteService;
import services.SizePriceService;

@Controller
@RequestMapping("/route")
public class RouteController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private RouteService routeService;
	
	@Autowired
	private SizePriceService sizePriceService;
	
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
	
	private ModelAndView createListModelAndView(int routeId){
		ModelAndView result;
		Route route;
		Collection<SizePrice> sizePrices;
		
		route = routeService.findOne(routeId);
		sizePrices = sizePriceService.findAllByRouteId(routeId);
		
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
		
		return result;
	}
}