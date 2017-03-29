package controllers.user;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import services.RatingService;
import services.RouteService;
import services.ShipmentService;
import services.UserService;

@Controller
@RequestMapping("/user")
public class UserProfileController extends AbstractController {

	// Supporting services -----------------------

	@Autowired
	private UserService userService;

	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private RatingService ratingService;
	
	// Constructors -----------------------------------------------------------

	public UserProfileController() {
		super();
	}

	// ------------------------------------------------------------------------

	@RequestMapping("/profile")
	public ModelAndView profile(HttpServletRequest request, @RequestParam(required=false) Integer userId) {
		ModelAndView result;
		User user;
		Boolean isPrincipal = false;
		int routesCreated, shipmentsCreated, ratingsCreated;
	
		
		if(userId != null){
			user = userService.findOne(userId);
			Assert.notNull(user);
		}else{
			user = userService.findByPrincipal();
			Assert.notNull(user);
			isPrincipal = true;
		}
		
		
		shipmentsCreated   = shipmentService.countShipmentCreatedByUserId(user);
		routesCreated   = routeService.countRouteCreatedByUserId(user);
		ratingsCreated = ratingService.countRatingCreatedByUserId(user);
		
		result = new ModelAndView("user/profile");
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("routesCreated", routesCreated);
		result.addObject("shipmentsCreated", shipmentsCreated);
		result.addObject("ratingsCreated", ratingsCreated);

		result.addObject("user", user);
		
		return result;
	}
}