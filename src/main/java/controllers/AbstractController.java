package controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AbstractController {
	
	static Logger log = Logger.getLogger(AbstractController.class);
	
	// Panic handler ----------------------------------------------------------
	
	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("redirect:/");
		log.error("Exception: ", oops);

		return result;
	}

}
