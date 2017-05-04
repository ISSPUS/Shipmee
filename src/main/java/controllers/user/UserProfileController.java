package controllers.user;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Rating;
import domain.User;
import security.UserAccount;
import services.ActorService;
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
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public UserProfileController() {
		super();
	}

	// ------------------------------------------------------------------------

	@RequestMapping("/profile")
	public ModelAndView profile(HttpServletRequest request, @RequestParam(required=false) Integer userId,@RequestParam(required=false, defaultValue="1") int pagecomment) {
		ModelAndView result;
		User user;
		UserAccount userAccount;
		Boolean isPrincipal = false;
		int shipmentsCreated,routesCreated,ratingsCreated, ratingsReceived;
		/*VARIABLES PARA LISTA DE COMENTARIOS */
		Page<Rating> ratings;
		Pageable pageable;
		
		pageable = new PageRequest(pagecomment - 1, 3);
		
		if(userId != null){
			user = userService.findOne(userId);
			Assert.notNull(user);
			if(actorService.checkLogin()){
				isPrincipal = actorService.findByPrincipal().getId() == user.getId();
			}
			ratings = ratingService.findAllByAuthorOrUser(0, userId, pageable);

		}else{
			user = userService.findByPrincipal();
			Assert.notNull(user);
			isPrincipal = true;
			ratings = ratingService.findAllByAuthorOrUser(0, user.getId(), pageable);
		}
		
		userAccount = user.getUserAccount();
		
		shipmentsCreated   = shipmentService.countShipmentCreatedByUserId(user);
		routesCreated   = routeService.countRouteCreatedByUserId(user);
		ratingsCreated = ratingService.countRatingCreatedByUserId(user);
		ratingsReceived = ratingService.countRatingReceivedByUserId(user);
		
		result = new ModelAndView("user/profile");
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("routesCreated", routesCreated);
		result.addObject("shipmentsCreated", shipmentsCreated);
		result.addObject("ratingsCreated", ratingsCreated);
		result.addObject("ratingsReceived", ratingsReceived);
		result.addObject("userAccount", userAccount);
		result.addObject("user", user);
		if(!isPrincipal && actorService.checkAuthority("USER")){
			Rating rating = ratingService.create(userId);
			result.addObject("rating", rating);

		}
		result.addObject("ratings", ratings);
		result.addObject("page", pagecomment);
		result.addObject("total_pages", ratings.getTotalPages());
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int userReceivedId) {
		ModelAndView result;
		Rating rating;

		rating = ratingService.create(userReceivedId);
		result = createEditModelAndView(rating);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Rating rating, BindingResult binding) {
		ModelAndView result;
		Rating reconstructed;
		
		reconstructed = ratingService.reconstruct(rating, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(rating);
		} else {
			try {
				reconstructed = ratingService.save(reconstructed);

				result = new ModelAndView("redirect:/user/profile.do?userId=" + reconstructed.getUser().getId());
				result.addObject("message", "rating.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(reconstructed, "rating.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/mylist", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		int currentActorId;
		
		currentActorId = actorService.findByPrincipal().getId();
				
		result = new ModelAndView("redirect:user/profile.do?userId=" + currentActorId);

		return result;
	}
	
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Rating input) {
		ModelAndView result;

		result = createEditModelAndView(input, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Rating input, String message) {
		
		ModelAndView result = profile(null, input.getUser().getId(), 1);
		result.addObject("rating", input);
		result.addObject("message", message);

		return result;
	}
}
