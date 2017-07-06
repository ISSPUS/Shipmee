package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import domain.form.ActorForm;
import services.UserService;
import services.form.ActorFormService;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorFormService actorFormService;
	
	
	// Constructors -----------------------------------------------------------
	
	public UserController() {
		super();
	}
		
	// Creation ------------------------------------------------------------------		

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ActorForm user;

		user = actorFormService.createForm(true);
		result = createEditModelAndView(user);

		return result;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ActorForm actorForm, BindingResult binding) {
		ModelAndView result;
		User reconstructed;
		
		reconstructed = (User) actorFormService.reconstruct(actorForm, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(actorForm);
		} else {
			try {
				userService.save(reconstructed);

				result = new ModelAndView("redirect:/security/login.do?register=true");
			} catch (Throwable oops) {
				result = createEditModelAndView(actorForm, "user.register.error");
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(ActorForm input) {
		ModelAndView result;

		result = createEditModelAndView(input, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ActorForm input, String message) {
		ModelAndView result;
		
		input.setAcceptLegalCondition(false);
		input.setPassword("");
		input.setRepeatedPassword("");

		result = new ModelAndView("user/edit");
		result.addObject("actorForm", input);
		result.addObject("url", "user/register.do");
		result.addObject("message", message);

		return result;
	}

}

