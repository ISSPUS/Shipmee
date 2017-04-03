package controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import services.form.ActorFormService;
import controllers.AbstractController;
import domain.User;
import domain.form.ActorForm;

@Controller
@RequestMapping("/user/user")
public class UserUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ActorFormService actorFormService;
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------
	
	public UserUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------


	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		ActorForm form;

		form = actorFormService.createForm(false);		

		result = createEditModelAndView(form);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		User reconstructed;
		
		reconstructed = (User) actorFormService.reconstruct(actorForm, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				userService.save(reconstructed);
				
				result = new ModelAndView("redirect:/user/profile.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "user.commit.error");				
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ActorForm actorForm) {
		ModelAndView result;

		result = createEditModelAndView(actorForm, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(ActorForm actorForm, String message) {
		ModelAndView result;
		
		actorForm.setPassword("");
		actorForm.setRepeatedPassword("");
				
		result = new ModelAndView("user/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;
	}

}
