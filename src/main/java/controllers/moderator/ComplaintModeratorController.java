package controllers.moderator;


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
import domain.Complaint;
import services.ComplaintService;

@Controller
@RequestMapping("/complaint/moderator")
public class ComplaintModeratorController extends AbstractController {
	
	static Logger log = Logger.getLogger(ComplaintModeratorController.class);

	
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
		pageable = new PageRequest(page - 1, 3);

		items = complaintService.findAllNotResolvedAndNotInvolved(pageable);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", items.getContent());
		result.addObject("p", page);
		result.addObject("total_pages", items.getTotalPages());
		result.addObject("urlPage", "complaint/moderator/list.do?page=");

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int complaintId, @RequestParam String type) {
		ModelAndView result;
		Complaint complaint;
		String messageError;

		try {
			complaint = complaintService.findOne(complaintId);
			complaint.setType(type);
			complaintService.save(complaint);

			result = new ModelAndView("redirect:list.do?page=1");
		} catch (Throwable oops) {
			log.error(oops.getMessage());
			messageError = "complaint.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError=oops.getMessage();
			}
			result = list(1);
			result.addObject("message", messageError);
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

}
