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


import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Route;
import domain.Vehicle;
import domain.form.RouteForm;
import services.RouteService;
import services.VehicleService;
import services.form.RouteFormService;

@Controller
@RequestMapping("/route/user")
public class RouteUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private RouteService routeService;
	
	@Autowired
	private RouteFormService routeFormService;
	
	@Autowired
	private VehicleService vehicleService;
	
	// Constructors -----------------------------------------------------------
	
	public RouteUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		RouteForm routeForm;

		routeForm = routeFormService.create();
		result = createEditModelAndView(routeForm);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int routeId) {
		ModelAndView result;
		RouteForm routeForm;

		routeForm = routeFormService.findOne(routeId);		
		Assert.notNull(routeForm);
		routeForm.setRouteId(routeId);
		result = createEditModelAndView(routeForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RouteForm routeForm, BindingResult binding) {
		ModelAndView result;
		int id;

		if (binding.hasErrors()) {
			result = createEditModelAndView(routeForm);
		} else {
			try {
				Route route;
				id = routeForm.getRouteId();
				
				route = routeFormService.reconstruct(routeForm);
				route = routeService.save(route);
				
				if(id == 0) {
					result = new ModelAndView("redirect:../../sizePrice/user/create.do?routeId="+route.getId());
				} else {
					result = new ModelAndView("redirect:../../sizePrice/user/edit.do?routeId="+route.getId());
				}
			} catch (Throwable oops) {

				result = createEditModelAndView(routeForm, "route.commit.error");				
			}
		}

		return result;
	}
			
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(RouteForm routeForm, BindingResult binding) {
		ModelAndView result;

		try {
			routeFormService.delete(routeForm);
			result = new ModelAndView("redirect:../../");						
		} catch (Throwable oops) {
			result = createEditModelAndView(routeForm, "route.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/contract", method = RequestMethod.GET)
	public ModelAndView contractRoute(@RequestParam int routeId, @RequestParam int sizePriceId) {
		ModelAndView result;
		String messageError;
		
		Route route = routeService.findOne(routeId);
		
		try {
			routeService.contractRoute(routeId, sizePriceId);
			result = new ModelAndView("redirect:../../routeOffer/user/list.do?routeId=" + routeId);
		} catch(Throwable oops){
			messageError = "route.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError = oops.getMessage();
			}
			result = createEditModelAndView(route, messageError);
		}
		
		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(RouteForm routeForm) {
		ModelAndView result;

		result = createEditModelAndView(routeForm, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Route route) {
		ModelAndView result;

		result = createEditModelAndView(route, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(RouteForm routeForm, String message) {
		ModelAndView result;
		Collection<Vehicle> vehicles;
		
		vehicles = vehicleService.findAllByUser();
				
		result = new ModelAndView("route/edit");
		result.addObject("routeForm", routeForm);
		result.addObject("message", message);
		result.addObject("vehicles", vehicles);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Route route, String message) {
		ModelAndView result;
		Collection<Vehicle> vehicles;
		
		vehicles = vehicleService.findAllByUser();
				
		result = new ModelAndView("route/search");
		result.addObject("route", route);
		result.addObject("message", message);
		result.addObject("vehicles", vehicles);

		return result;
	}

}