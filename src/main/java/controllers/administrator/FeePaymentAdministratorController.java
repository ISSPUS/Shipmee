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
		Integer allAccepted;
		Integer allPending;
		Integer allDenied;
		Pageable pageable;
		String feePaymentsType;
		
		pageable = new PageRequest(page - 1, 3);
		allAccepted = (int) feePaymentService.findAllAccepted(pageable).getTotalElements();
		allPending = (int) feePaymentService.findAllPending(pageable).getTotalElements();
		allDenied = (int) feePaymentService.findAllRejected(pageable).getTotalElements();

		if(type.equals("Rejected") || type.equals("Rechazados")) {
			items = feePaymentService.findAllRejected(pageable);
			feePaymentsType="Rejected";
		} else if(type.equals("Pending") || type.equals("Pendientes")) {
			items = feePaymentService.findAllPending(pageable);
			feePaymentsType="Pending";
		} else {   // if (type.equals("Accepted")) {
			items = feePaymentService.findAllAccepted(pageable);
			feePaymentsType="Accepted";

		}

		result = new ModelAndView("feepayment/list");
		result.addObject("feePayments", items.getContent());
		result.addObject("allAccepted", allAccepted);
		result.addObject("allPending", allPending);
		result.addObject("allDenied", allDenied);
		result.addObject("p", page);
		result.addObject("total_pages", items.getTotalPages());
		result.addObject("feePaymentsType", feePaymentsType);
		result.addObject("urlPage", "feepayment/administrator/list.do?type="+feePaymentsType+"&page=");

		return result;
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------
	
	// Ancillary methods ------------------------------------------------------

}
