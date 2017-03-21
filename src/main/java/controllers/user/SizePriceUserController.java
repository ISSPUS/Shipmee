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
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.form.SizePriceFormService;
import controllers.AbstractController;
import domain.form.SizePriceForm;

@Controller
@RequestMapping("/sizePrice/user")
public class SizePriceUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private SizePriceFormService sizePriceFormService;
	
	// Constructors -----------------------------------------------------------
	
	public SizePriceUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int routeId) {
		ModelAndView result;
		SizePriceForm sizePriceForm;

		sizePriceForm = sizePriceFormService.create(routeId);
		sizePriceForm.setSizePriceFormId(0);
		
		result = createEditModelAndView(sizePriceForm);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int routeId) {
		ModelAndView result;
		SizePriceForm sizePriceForm;

		sizePriceForm = sizePriceFormService.findOne(routeId);		
		Assert.notNull(sizePriceForm);
		sizePriceForm.setSizePriceFormId(routeId);
		result = createEditModelAndView(sizePriceForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SizePriceForm sizePriceForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {

			result = createEditModelAndView(sizePriceForm);
		} else {
			try {
				sizePriceFormService.reconstruct(sizePriceForm);				
				result = new ModelAndView("redirect:../../");
			} catch (Throwable oops) {

				result = createEditModelAndView(sizePriceForm, "sizePrice.commit.error");				
			}
		}

		return result;
	}
			
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SizePriceForm sizePriceForm, BindingResult binding) {
		ModelAndView result;

		try {			
			sizePriceFormService.delete(sizePriceForm);
			result = new ModelAndView("redirect:../../");						
		} catch (Throwable oops) {
			result = createEditModelAndView(sizePriceForm, "sizePrice.commit.error");
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(SizePriceForm sizePriceForm) {
		ModelAndView result;

		result = createEditModelAndView(sizePriceForm, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(SizePriceForm sizePriceForm, String message) {
		ModelAndView result;		
				
		result = new ModelAndView("sizePrice/edit");
		result.addObject("sizePriceForm", sizePriceForm);
		result.addObject("message", message);

		return result;
	}

}