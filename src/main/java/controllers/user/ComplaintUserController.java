/* AnnouncementAdministratorController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.user;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Complaint;
import services.ComplaintService;

@Controller
@RequestMapping("/complaint/user")
public class ComplaintUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ComplaintService complaintService;
	
	// Constructors -----------------------------------------------------------
	
	public ComplaintUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int userId) {
		ModelAndView result;
		Complaint complaint;

		complaint = complaintService.create(userId);
		result = createEditModelAndView(complaint);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@Valid Complaint complaint, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(complaint);
		} else {
			try {				
				complaint = complaintService.save(complaint);
				
				result = new ModelAndView("redirect:../../");
			} catch (Throwable oops) {

				result = createEditModelAndView(complaint, "complaint.commit.error");				
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Complaint complaint) {
		ModelAndView result;

		result = createEditModelAndView(complaint, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Complaint complaint, String message) {
		ModelAndView result;
						
		result = new ModelAndView("complaint/create");
		result.addObject("complaint", complaint);
		result.addObject("message", message);

		return result;
	}

}