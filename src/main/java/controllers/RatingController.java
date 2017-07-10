package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Rating;
import services.ActorService;
import services.RatingService;

@Controller
@RequestMapping("/rating")
public class RatingController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	
	public RatingController() {
		super();
	}
		
	// Search ------------------------------------------------------------------		

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false, defaultValue="-1") int authorId
			, @RequestParam(required=false, defaultValue="-1") int userReceivedId
			, @RequestParam(required=false, defaultValue="1") int page) {
		ModelAndView result;
		Page<Rating> ratings;
		Pageable pageable;
		int currentActorId;

		
		pageable = new PageRequest(page - 1, 4);
		
		ratings = ratingService.findAllByAuthorOrUser(authorId, userReceivedId, pageable);
		try{
			currentActorId = actorService.findByPrincipal().getId();
		}catch (Exception e) {
			currentActorId = -1;
		}
				
		result = new ModelAndView("rating/list");
		result.addObject("ratings", ratings);
		result.addObject("currentActorId", currentActorId);
		result.addObject("authorId", authorId);
		result.addObject("userReceivedId", userReceivedId);
		result.addObject("p", page);
		result.addObject("total_pages", ratings.getTotalPages());
		result.addObject("urlPage", "rating/list.do?userReceivedId=" + String.valueOf(userReceivedId) + "&authorId="
				+ String.valueOf(authorId)+"&page=");

		return result;
	}

}
