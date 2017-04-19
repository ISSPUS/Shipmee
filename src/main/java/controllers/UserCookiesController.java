package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/userCookies")
public class UserCookiesController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public UserCookiesController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/info")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("userCookies/info");

		return result;
	}

}
