package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Route;
import services.RouteService;

@Controller
@RequestMapping("/route")
public class RouteController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private RouteService routeService;	
	
	// Constructors -----------------------------------------------------------
	
	public RouteController() {
		super();
	}
		
	// Search ------------------------------------------------------------------		

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(String origin, String destination, @RequestParam(required=false) Date date,
			@RequestParam(required=false) String hour, @RequestParam(required=false) String envelope,
			@RequestParam(required=false) String itemSize) {
		ModelAndView result;
		SimpleDateFormat formatter;
		Date time;
		Collection<Route> routes;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		routes = new HashSet<Route>();
		time = null;

		if(date!=null){
			try {
				time = formatter.parse(date.getDate()+"/"+date.getMonth()+"/"+date.getYear()+" "+hour);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		routes = routeService.searchRoute(origin, destination, date, time, envelope, itemSize);
				
		result = new ModelAndView("route/search");
		result.addObject("routes", routes);

		return result;
	}
}