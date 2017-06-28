package controllers.user;


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
import domain.Alert;
import services.AlertService;

@Controller
@RequestMapping("/alert/user")
public class AlertUserController extends AbstractController {
	
	static Logger log = Logger.getLogger(AlertUserController.class);
	
	// Services ---------------------------------------------------------------

	@Autowired
	private AlertService alertService;

	// Constructors -----------------------------------------------------------

	public AlertUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false, defaultValue="1") int page) {
		ModelAndView result;
		Page<Alert> alerts;
		Pageable pageable;

		pageable = new PageRequest(page - 1, 4);
		alerts = alertService.getAlertsByPrincipal(pageable);
		
		result = new ModelAndView("alert/list");
		result.addObject("alerts", alerts.getContent());
		result.addObject("p", page);
		result.addObject("total_pages", alerts.getTotalPages());
		result.addObject("urlPage", "alert/user/list.do?page=");

		return result;
	}
	
	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Alert alert;
		
		alert = alertService.create();
		
		result = createEditModelAndView(alert);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int alertId) {
		ModelAndView result;
		Alert alert;

		alert = alertService.findOne(alertId);
		Assert.notNull(alert, "controller.user.alert.edit.isNull");
		result = createEditModelAndView(alert);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Alert alert, BindingResult binding) {
		ModelAndView result;
		String messageError;

		if (binding.hasErrors()) {
			result = createEditModelAndView(alert);
		} else {
			try {
				alert = alertService.save(alert);

				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				log.error(oops.getMessage());
				messageError = "alert.commit.error";
				if(oops.getMessage().contains("message.error")){
					messageError=oops.getMessage();
				}
				result = createEditModelAndView(alert, messageError);
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Alert alert, BindingResult binding) {
		ModelAndView result;

		try {
			alertService.delete(alert);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(alert, "alert.commit.error");
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Alert alert) {
		ModelAndView result;

		result = createEditModelAndView(alert, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Alert alert, String message) {
		ModelAndView result;

		result = new ModelAndView("alert/edit");
		result.addObject("alert", alert);
		result.addObject("message", message);

		return result;
	}

}
