package controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class AbstractController {
	
	static Logger log = Logger.getLogger(AbstractController.class);
	
	// Panic handler ----------------------------------------------------------
	
	@ExceptionHandler(Throwable.class)
	public void panic(final Throwable oops) {
		log.error("Exception: ", oops);
	}	

}
