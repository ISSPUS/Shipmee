package controllers.administrator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.FeePayment;
import services.FeePaymentService;

@Controller
@RequestMapping("/feepayment/administrator")
public class FeePaymentAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private FeePaymentService feePaymentService;
	
	// Constructors -----------------------------------------------------------
	
	public FeePaymentAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam (required = false, defaultValue = "Pending") String type, @RequestParam int page) {
		ModelAndView result;
		Page<FeePayment> items;
		Pageable pageable;
		pageable = new PageRequest(page - 1, 5);

		if(type.equals("Rejected")) {
			items = feePaymentService.findAllRejected(pageable);
		} else if(type.equals("Pending")) {
			items = feePaymentService.findAllPending(pageable);
		} else {   // if (type.equals("Accepted")) {
			items = feePaymentService.findAllAccepted(pageable);
		}

		result = new ModelAndView("feepayment/list");
		result.addObject("feePayments", items.getContent());
		result.addObject("p", page);
		result.addObject("total_pages", items.getTotalPages());

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------
	
	// Ancillary methods ------------------------------------------------------

}
