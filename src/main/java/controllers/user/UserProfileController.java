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
import services.UserService;

@Controller
@RequestMapping("/user")
public class UserProfileController extends AbstractController {

	// Supporting services -----------------------

	@Autowired
	private UserService userService;

	
	
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

		if(userId != null){
			user = userService.findOne(userId);
			Assert.notNull(user);
		}else{
			user = userService.findByPrincipal();
			Assert.notNull(user);
			isPrincipal = true;
		}
		
		result = new ModelAndView("user/profile");
		result.addObject("isPrincipal", isPrincipal);
		result.addObject("routesCreated", 0);
		result.addObject("shipmentsCreated", 0);
		result.addObject("commentsCreated", 0);

		result.addObject("user", user);
		
		return result;
	}
}