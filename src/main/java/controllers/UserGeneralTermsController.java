package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/userGeneralTerms")
public class UserGeneralTermsController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public UserGeneralTermsController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/info")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("userGeneralTerms/info");

		return result;
	}

}
