package controllers.administrator;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Complaint;
import services.ComplaintService;

@Controller
@RequestMapping("/complaint/administrator")
public class ComplaintAdministratorController extends AbstractController {
	
	static Logger log = Logger.getLogger(ComplaintAdministratorController.class);

	// Services ---------------------------------------------------------------
	
	@Autowired
	private ComplaintService complaintService;
	
	// Constructors -----------------------------------------------------------
	
	public ComplaintAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam (required = false, defaultValue = "Serious") String type, @RequestParam int page) {
		ModelAndView result;
		Page<Complaint> items;
		Integer allSerious;
		Integer allMild;
		Integer allOmitted;
		Pageable pageable;
		String complaintType;

		pageable = new PageRequest(page - 1, 3);
		allSerious = (int) complaintService.findAllSerious(pageable).getTotalElements();
		allMild = (int) complaintService.findAllMild(pageable).getTotalElements();
		allOmitted = (int) complaintService.findAllOmitted(pageable).getTotalElements();

		if(type.equals("Serious") || type.equals("Grave")) {
			items = complaintService.findAllSerious(pageable);
			complaintType="Serious";
		} else if(type.equals("Mild") || type.equals("Leve")) {
			items = complaintService.findAllMild(pageable);
			complaintType="Mild";
		} else {   // if (type.equals("Omitted") || type.equals("Omitido")) {
			items = complaintService.findAllOmitted(pageable);
			complaintType="Omitted";

		}

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", items.getContent());
		result.addObject("allSerious", allSerious);
		result.addObject("allMild", allMild);
		result.addObject("allOmitted", allOmitted);		
		result.addObject("p", page);
		result.addObject("total_pages", items.getTotalPages());
		result.addObject("urlPage", "complaint/administrator/list.do?type="+complaintType+"&page=");

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------
	
	// Ancillary methods ------------------------------------------------------

}
