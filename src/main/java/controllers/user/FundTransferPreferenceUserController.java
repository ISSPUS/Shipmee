package controllers.user;


import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.User;
import domain.form.FundTransferPreferenceForm;
import services.UserService;
import services.form.FundTransferPreferenceFormService;

@Controller
@RequestMapping("/fundTransferPreference/user")
public class FundTransferPreferenceUserController extends AbstractController {
	
	static Logger log = Logger.getLogger(FundTransferPreferenceUserController.class);

	// Services ---------------------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FundTransferPreferenceFormService fundTransferPreferenceFormService;
	// Constructors -----------------------------------------------------------
	
	public FundTransferPreferenceUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		FundTransferPreferenceForm form;

		form = fundTransferPreferenceFormService.create();
		result = createEditModelAndView(form);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePaypal")
	public ModelAndView savePaypal(@Valid FundTransferPreferenceForm form, BindingResult binding) {
		ModelAndView result;
		String messageError;
		User user;

		if (binding.hasErrors()) {
			result = createEditModelAndView(form);
		} else {
			if (userService.IBANBICValidator(form.getIBAN(), form.getBIC())){
				try {
					if(form.getPaypalEmail() != null && form.getPaypalEmail().equals("")){
						Assert.isTrue(false,"message.error.fundTransferPreference.fillPayPal");
					}
					
					user = fundTransferPreferenceFormService.reconstruct(form);
					userService.save(user);
					
					result = new ModelAndView("redirect:../../user/profile.do");
				} catch (Throwable oops) {
					log.error(oops.getMessage());
					messageError = "fundTransferPreference.commit.error";
					if(oops.getMessage().contains("message.error")){
						messageError=oops.getMessage();
					}
					result = createEditModelAndView(form, messageError);				
				}
			}else{
				if(form.getIBAN().contains(" ")){
					messageError = "message.error.fundTransferPreference.ibanbic.blankspace.error";
				}else{
					messageError = "message.error.fundTransferPreference.ibanbic.error";
				}
				
				result = createEditModelAndView(form, messageError);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveBankAccount")
	public ModelAndView saveBankAccount(@Valid FundTransferPreferenceForm form, BindingResult binding) {
		ModelAndView result;
		String messageError;
		User user;

		if (binding.hasErrors()) {
			result = createEditModelAndView(form);
		} else {
			if (userService.IBANBICValidator(form.getIBAN(), form.getBIC())){
				try {
					if((form.getCountry() != null && form.getCountry().equals("")) ||
							(form.getAccountHolder() != null && form.getAccountHolder().equals("")) ||
							(form.getBankName() != null && form.getBankName().equals("")) ||
							(form.getIBAN() != null && form.getIBAN().equals("")) ||
							(form.getBIC() != null && form.getBIC().equals(""))) {
						Assert.isTrue(false,"message.error.fundTransferPreference.fillBankAccount");
					}
					
					user = fundTransferPreferenceFormService.reconstruct(form);
					userService.save(user);
					
					result = new ModelAndView("redirect:../../user/profile.do");
				} catch (Throwable oops) {
					log.error(oops.getMessage());
					messageError = "fundTransferPreference.commit.error";
					if(oops.getMessage().contains("message.error")){
						messageError=oops.getMessage();
					}
					result = createEditModelAndView(form, messageError);				
				}
			}else{
				if(form.getIBAN().contains(" ")){
					messageError = "message.error.fundTransferPreference.ibanbic.blankspace.error";
				}else{
					messageError = "message.error.fundTransferPreference.ibanbic.error";
				}
				
				result = createEditModelAndView(form, messageError);
			}
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(FundTransferPreferenceForm form) {
		ModelAndView result;

		result = createEditModelAndView(form, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(FundTransferPreferenceForm form, String message) {
		ModelAndView result;
						
		result = new ModelAndView("fundTransferPreference/edit");
		result.addObject("fundTransferPreferenceForm", form);
		result.addObject("message", message);

		return result;
	}

}
