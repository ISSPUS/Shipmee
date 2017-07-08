package controllers.administrator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import services.ActorService;
import services.UserService;

@Controller
@RequestMapping("/user/administrator")
public class UserAdministratorController extends AbstractController {
	
	static Logger log = Logger.getLogger(UserAdministratorController.class);

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService actorService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public UserAdministratorController() {
		super();
	}

	// Search ------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "-1") int isVerified,
			@RequestParam(required = false, defaultValue = "-1") int isActive,
			@RequestParam(required = false, defaultValue = "-1") int isModerator,
			@RequestParam(required = false, defaultValue = "-1") int verificationPending,
			@RequestParam(required = false, defaultValue = "1") int page) {
		ModelAndView result;
		Page<User> users;
		Pageable pageable;
		int currentActorId;

		pageable = new PageRequest(page - 1, 5);

		users = userService.findAllByVerifiedActiveVerificationPending(isVerified, isActive, verificationPending,
				isModerator, pageable);
		try {
			currentActorId = actorService.findByPrincipal().getId();
		} catch (Exception e) {
			currentActorId = -1;
		}

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("currentActorId", currentActorId);
		result.addObject("isVerified", isVerified);
		result.addObject("isActive", isActive);
		result.addObject("verificationPending", verificationPending);
		result.addObject("p", page);
		result.addObject("total_pages", users.getTotalPages());

		return result;
	}

	@RequestMapping(value = "/turnIntoModerator", method = RequestMethod.GET)
	public ModelAndView turnIntoModerator(@RequestParam int userId) {
		ModelAndView result;
		String message;

		try {
			userService.turnIntoModerator(userId);
			message = "user.turnIntoModerator.ok";
		} catch (Exception e) {
			message = "user.turnIntoModerator.error";
		}

		result = new ModelAndView("redirect:list.do?isModerator=1");
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/unturnIntoModerator", method = RequestMethod.GET)
	public ModelAndView unturnIntoModerator(@RequestParam int userId) {
		ModelAndView result;
		String message;

		try {
			userService.unturnIntoModerator(userId);
			message = "user.unturnIntoModerator.ok";
		} catch (Exception e) {
			log.error(e);
			message = "user.unturnIntoModerator.error";
		}

		result = new ModelAndView("redirect:list.do?isModerator=1");
		result.addObject("message", message);

		return result;
	}
	
	@RequestMapping(value = "/verifyUser", method = RequestMethod.GET)
	public ModelAndView verifyUser(@RequestParam int userId) {
		ModelAndView result;
		String message;

		try {
			userService.verifyUser(userId);
			message = "user.verifyUser.ok";
		} catch (Exception e) {
			message = "user.verifyUser.error";
		}

		result = new ModelAndView("redirect:list.do?isVerified=1");
		result.addObject("message", message);

		return result;
	}
	
	@RequestMapping(value = "/unverifyUser", method = RequestMethod.GET)
	public ModelAndView unverifyUser(@RequestParam int userId) {
		ModelAndView result;
		String message;

		try {
			userService.unVerifyUser(userId);
			message = "user.unverifyUser.ok";
		} catch (Exception e) {
			message = "user.unverifyUser.error";
		}

		result = new ModelAndView("redirect:list.do?isVerified=1");
		result.addObject("message", message);

		return result;
	}

}
