package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import services.UserService;

@Controller
@RequestMapping("/admin/user")
public class UserAdministratorController extends AbstractController {

	// Supporting services -----------------------

	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public UserAdministratorController() {
		super();
	}

	// ------------------------------------------------------------------------

	@RequestMapping(value = "/listVerified", method = RequestMethod.GET)
	public ModelAndView listAllVerified(int page) {
		ModelAndView result;
		Page<User> allVerified;
		int countAllVerified = 0;
		Pageable pageable;
		
		pageable = new PageRequest(page - 1, 5);
		
		allVerified = userService.findAllVerified(pageable);
		countAllVerified = userService.countAllVerified();
		
		result = new ModelAndView("admin/user");
		result.addObject("allVerified", allVerified.getContent());
		result.addObject("countAllVerified", countAllVerified);
		result.addObject("p", page);
		result.addObject("total_pages", allVerified.getTotalPages());
		
		return result;

	}
	
	@RequestMapping(value = "/listPending", method = RequestMethod.GET)
	public ModelAndView listAllPending(int page) {
		ModelAndView result;
		Page<User> allPending;
		int countAllPending = 0;
		Pageable pageable;
		
		pageable = new PageRequest(page - 1, 5);
		
		allPending = userService.findAllPending(pageable);
		countAllPending = userService.countAllPending();
		
		result = new ModelAndView("admin/user");
		result.addObject("allVerified", allPending.getContent());
		result.addObject("countAllVerified", countAllPending);
		result.addObject("p", page);
		result.addObject("total_pages", allPending.getTotalPages());
		
		return result;

	}

}