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
import domain.Shipment;
import domain.User;
import domain.form.ShipmentForm;
import services.ShipmentService;
import services.UserService;
import services.form.ShipmentFormService;

@Controller
@RequestMapping("/shipment/user")
public class ShipmentUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private ShipmentFormService shipmentFormService;
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------
	
	public ShipmentUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false, defaultValue="1") int page){
		ModelAndView result;
		Page<Shipment> ownShipments;
		Pageable pageable;
		User currentUser;
		
		pageable = new PageRequest(page - 1, 5);
		
		ownShipments = shipmentService.findAllByCurrentUser(pageable);
		currentUser = userService.findByPrincipal();
		
		result = new ModelAndView("shipment/user");
		result.addObject("shipments", ownShipments.getContent());
		result.addObject("user", currentUser);
		result.addObject("p", page);
		result.addObject("total_pages", ownShipments.getTotalPages());
		
		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ShipmentForm shipmentForm;

		shipmentForm = shipmentFormService.create();
		result = createEditModelAndView(shipmentForm);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int shipmentId) {
		ModelAndView result;
		ShipmentForm shipmentForm;

		shipmentForm = shipmentFormService.findOne(shipmentId);		
		Assert.notNull(shipmentForm);
		shipmentForm.setShipmentId(shipmentId);
		result = createEditModelAndView(shipmentForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ShipmentForm shipmentForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(shipmentForm);
		} else {
			try {
				Shipment shipment;

				shipment = shipmentFormService.reconstruct(shipmentForm);
				shipmentService.save(shipment);

				result = new ModelAndView("redirect:../../");
			} catch (Throwable oops) {
				result = createEditModelAndView(shipmentForm, "shipment.commit.error");
			}
		}

		return result;
	}
			
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ShipmentForm shipmentForm, BindingResult binding) {
		ModelAndView result;

		try {
			shipmentFormService.delete(shipmentForm);
			result = new ModelAndView("redirect:../../");
		} catch (Throwable oops) {
			result = createEditModelAndView(shipmentForm, "shipment.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/carry", method = RequestMethod.GET)
	public ModelAndView carryShipment(@RequestParam int shipmentId){
		ModelAndView result;
		String messageError;
		
		Shipment shipment = shipmentService.findOne(shipmentId);
		
		try {
			shipmentService.carryShipment(shipmentId);
			result = new ModelAndView("redirect:../../shipmentOffer/user/list.do?shipmentId=" + shipmentId);
		}catch(Throwable oops){
			messageError = "shipment.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError = oops.getMessage();
			}
			result = createEditModelAndView(shipment, messageError);
		}
		
		return result;		
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ShipmentForm shipmentForm) {
		ModelAndView result;

		result = createEditModelAndView(shipmentForm, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Shipment shipment) {
		ModelAndView result;

		result = createEditModelAndView(shipment, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(ShipmentForm shipmentForm, String message) {
		ModelAndView result;
						
		result = new ModelAndView("shipment/edit");
		result.addObject("shipmentForm", shipmentForm);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView(Shipment shipment, String message) {
		ModelAndView result;
						
		result = new ModelAndView("shipment/search");
		result.addObject("shipment", shipment);
		result.addObject("message", message);

		return result;
	}

}
