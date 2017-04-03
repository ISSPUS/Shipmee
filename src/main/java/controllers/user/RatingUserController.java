package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Rating;
import services.ActorService;
import services.RatingService;

@Controller
@RequestMapping("/rating/user")
public class RatingUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ActorService actorService;
	
	
	// Constructors -----------------------------------------------------------
	
	public RatingUserController() {
		super();
	}
		
	// Creation ------------------------------------------------------------------		

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
			result = createEditModelAndView(reconstructed);
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
		ModelAndView result;

		result = new ModelAndView("user/profile");
		result.addObject("rating", input);
		result.addObject("message", message);

		return result;
	}

}