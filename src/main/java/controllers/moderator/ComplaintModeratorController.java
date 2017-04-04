/* AnnouncementAdministratorController.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.moderator;


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
import domain.Complaint;
import services.ComplaintService;

@Controller
@RequestMapping("/complaint/moderator")
public class ComplaintModeratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private ComplaintService complaintService;
	
	// Constructors -----------------------------------------------------------
	
	public ComplaintModeratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam int page) {
		ModelAndView result;
		Page<Complaint> items;
		Pageable pageable;
		pageable = new PageRequest(page - 1, 5);

		items = complaintService.findAllNotResolvedAndNotInvolved(pageable);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", items.getContent());
		result.addObject("p", page);
		result.addObject("total_pages", items.getTotalPages());

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int complaintId, @RequestParam String type) {
		ModelAndView result;
		Complaint complaint;

		try {
			complaint = complaintService.findOne(complaintId);
			complaint.setType(type);
			complaintService.save(complaint);

			result = new ModelAndView("redirect:list.do?page=1");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:list.do?page=1");
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}