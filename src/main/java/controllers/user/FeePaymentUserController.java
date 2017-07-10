package controllers.user;


import java.net.URLDecoder;
import java.time.LocalDateTime;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.FeePayment;
import domain.PayPalObject;
import domain.form.FeePaymentForm;
import services.FeePaymentService;
import services.PayPalService;
import services.form.FeePaymentFormService;

@Controller
@RequestMapping("/feepayment/user")
public class FeePaymentUserController extends AbstractController {
	
	static Logger log = Logger.getLogger(FeePaymentUserController.class);
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private FeePaymentService feePaymentService;
	
	@Autowired
	private FeePaymentFormService feePaymentFormService;
	
	@Autowired
	private PayPalService payPalService;

	// Constructors -----------------------------------------------------------
	
	public FeePaymentUserController() {
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
		allAccepted = (int) feePaymentService.findAllAcceptedByUser(pageable).getTotalElements();
		allPending = (int) feePaymentService.findAllPendingByUser(pageable).getTotalElements();
		allDenied = (int) feePaymentService.findAllRejectedByUser(pageable).getTotalElements();

		if(type.equals("Rejected") || type.equals("Rechazados")) {
			items = feePaymentService.findAllRejectedByUser(pageable);
			feePaymentsType="Rejected";
		} else if(type.equals("Pending") || type.equals("Pendientes")) {
			items = feePaymentService.findAllPendingByUser(pageable);
			feePaymentsType="Pending";
		} else {   // if (type.equals("Accepted")) {
			items = feePaymentService.findAllAcceptedByUser(pageable);
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
		result.addObject("urlPage", "feepayment/user/list.do?type="+feePaymentsType+"&page=");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int type, @RequestParam int id,
			@RequestParam (required=false, defaultValue="0") Integer sizePriceId, @RequestParam (required=false, defaultValue = "0") Double amount,
			@RequestParam (required=false) String description, @RequestParam (required=false, defaultValue="0") Integer shipmentId) {
		
		ModelAndView result;
		FeePaymentForm feePaymentForm;

		feePaymentForm = feePaymentFormService.create(type, id, sizePriceId, amount, description, shipmentId);
		result = createEditModelAndView(feePaymentForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid FeePaymentForm feePaymentForm, BindingResult binding) {
		ModelAndView result;
		FeePayment feePayment;
		String redirect = null;

		if (binding.hasErrors()) {
			result = createEditModelAndView(feePaymentForm);
		} else {
			try {
				
				/**
				 * Type == 1 -> Contract a route
				 * Type == 2 -> Create a routeOffer
				 * Type == 3 -> Accept a shipmentOffer
				 */
				switch (feePaymentForm.getType()) {
				case 1:
				case 2:
					redirect = "redirect:../../routeOffer/user/list.do?routeId=";
					break;
					
				case 3:
					redirect = "redirect:../../shipmentOffer/user/list.do?shipmentId=";
					break;

				default:
					break;
				}
				
				feePayment = feePaymentFormService.reconstruct(feePaymentForm);
				feePaymentService.save(feePayment);
				
				result = new ModelAndView(redirect+feePaymentForm.getId());
			} catch (Throwable oops) {
				log.error(oops);
				LocalDateTime now = LocalDateTime.now();
				if(feePaymentForm.getCreditCard().getExpirationMonth() < now.getMonthValue() &&
						feePaymentForm.getCreditCard().getExpirationYear() == now.getYear()){
					System.out.println(now.getMonthValue());
					result = createEditModelAndView(feePaymentForm, "feePayment.commit.error.month");
				}else{
					result = createEditModelAndView(feePaymentForm, "feePayment.commit.error");
				}
				
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int feepaymentId, @RequestParam String type) {
		ModelAndView result;
		FeePayment feePayment;
		PayPalObject po;

		try {
			feePayment = feePaymentService.findOne(feepaymentId);
			
			po = payPalService.findByFeePaymentId(feepaymentId);
			
			if (type.equals("Accepted") && po != null){
				payPalService.payToShipper(feepaymentId);
			} else if (type.equals("Rejected") && po != null){
				payPalService.refundToSender(feepaymentId);
			}
			
			feePayment.setType(type);
			
			feePaymentService.save(feePayment);

			result = new ModelAndView("redirect:list.do?page=1");
		} catch (Throwable oops) {
			log.error(oops, oops);
			result = new ModelAndView("redirect:list.do?page=1&message=error");
		}

		return result;
	}
	
	@RequestMapping(value = "/cancelPaymentInProgress", method = RequestMethod.GET)
	public ModelAndView cancelPaymentInProgress(@RequestParam int feePaymentId){
		ModelAndView result;
		String messageError;
		
		FeePayment feePayment = feePaymentService.findOne(feePaymentId);
		
		try{
			Assert.notNull(feePayment);
			
			if(feePayment.getShipmentOffer() != null){
				result = new ModelAndView("redirect:../../shipmentOffer/user/list.do?shipmentId=" + feePayment.getShipmentOffer().getShipment().getId());
			}else{
				result = new ModelAndView("redirect:../../routeOffer/user/list.do?routeId=" + feePayment.getRouteOffer().getRoute().getId());
			}
			feePaymentService.cancelPaymentInProgress(feePaymentId);
			
		}catch(Throwable oops){
			log.error(oops.getMessage());
			messageError = "shipmentOffer.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError = oops.getMessage();
			}
			result = new ModelAndView("redirect:/?message=" + messageError);
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(FeePaymentForm feePaymentForm) {
		ModelAndView result;

		result = createEditModelAndView(feePaymentForm, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(FeePaymentForm feePaymentForm, String message) {
		ModelAndView result;
						
		result = new ModelAndView("feepayment/create");
		result.addObject("feePaymentForm", feePaymentForm);
		result.addObject("message", message);

		return result;
	}

}
