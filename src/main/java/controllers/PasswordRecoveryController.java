package controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import services.ActorService;

@Controller
@RequestMapping("/passwordRecovery")
public class PasswordRecoveryController extends AbstractController {
	
	static Logger log = Logger.getLogger(PasswordRecoveryController.class);
	
	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------
	
	public PasswordRecoveryController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView forgot() {
		ModelAndView result;
				
		result = new ModelAndView("passwordRecovery/forgot");

		return result;
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST, params = "send")
	public ModelAndView forgot(String username) {
		ModelAndView result;
		Actor actor;
		
		actor = actorService.findByUsername(username);
		log.trace(actor);
		
		if(!username.equals("") && actor!=null){
			try{
				actorService.forgotPassword(actor);
				result = new ModelAndView("passwordRecovery/resultMessage");
				result.addObject("resultMessage", "password.recovery.forgot.success");
			}catch(Throwable oops){
				log.error(oops);
				result = new ModelAndView("passwordRecovery/forgot");
				result.addObject("message", "password.recovery.commit.error");
			}
		}else{
			if(username.equals("")){
				result = new ModelAndView("passwordRecovery/forgot");
				result.addObject("message", "password.recovery.blank.username");
			}else{
				result = new ModelAndView("passwordRecovery/forgot");
				result.addObject("message", "password.recovery.incorrect.username");
			}
		}
		
		return result;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView reset(String passwordResetToken) {
		ModelAndView result;
		
		if(actorService.findByPasswordResetToken(passwordResetToken)!=null){
			result = new ModelAndView("passwordRecovery/reset");
			result.addObject("passwordResetToken", passwordResetToken);
		}else{
			result = new ModelAndView("passwordRecovery/resultMessage");
			result.addObject("resultMessage", "password.recovery.incorrect.token");
		}

		return result;
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST, params = "send")
	public ModelAndView reset(String passwordResetToken, String password, String confirmPassword) {
		ModelAndView result;
		Actor actor;
		
		actor = actorService.findByPasswordResetToken(passwordResetToken);
		
		if(!password.equals("") && !confirmPassword.equals("") && password.equals(confirmPassword) && password.length()>=5 && password.length()<=32){
			try{
				actorService.resetPassword(actor, password);
				result = new ModelAndView("passwordRecovery/resultMessage");
				result.addObject("resultMessage", "password.recovery.reset.success");
			}catch(Throwable oops){
				log.error(oops);
				result = new ModelAndView("passwordRecovery/reset");
				result.addObject("passwordResetToken", passwordResetToken);
				result.addObject("message", "password.recovery.commit.error");
			}
		}else{
			if(password.equals("") || confirmPassword.equals("")){
				result = new ModelAndView("passwordRecovery/reset");
				result.addObject("passwordResetToken", passwordResetToken);
				result.addObject("message", "password.recovery.blank.password");
			}else if(password.length()<5 || password.length()>32){
				result = new ModelAndView("passwordRecovery/reset");
				result.addObject("passwordResetToken", passwordResetToken);
				result.addObject("message", "password.recovery.incorrect.size.password");
			}else{
				result = new ModelAndView("passwordRecovery/reset");
				result.addObject("passwordResetToken", passwordResetToken);
				result.addObject("message", "password.recovery.same.password");
			}
		}
		
		return result;
	}
}