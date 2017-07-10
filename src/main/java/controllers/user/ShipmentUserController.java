package controllers.user;


import java.util.Collection;

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
import controllers.ShipmentController;
import domain.Shipment;
import domain.ShipmentOffer;
import domain.User;
import domain.form.ShipmentForm;
import services.ShipmentOfferService;
import services.ShipmentService;
import services.UserService;
import services.form.ShipmentFormService;

@Controller
@RequestMapping("/shipment/user")
public class ShipmentUserController extends AbstractController {
	
	static Logger log = Logger.getLogger(ShipmentUserController.class);
	
	// Services ---------------------------------------------------------------

	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private ShipmentFormService shipmentFormService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	@Autowired
	private ShipmentController shipmentController;
	
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
		Integer shipmentId;
		
		pageable = new PageRequest(page - 1, 5);
		
		ownShipments = shipmentService.findAllByCurrentUser(pageable);
		currentUser = userService.findByPrincipal();
		shipmentId = 0;
		
		if(!ownShipments.getContent().isEmpty()){
			shipmentId = ownShipments.getContent().iterator().next().getCreator().getId();
		}
		
		result = new ModelAndView("shipment/user");
		result.addObject("shipments", ownShipments.getContent());
		result.addObject("user", currentUser);
		result.addObject("currentUser", currentUser);
		result.addObject("shipmentId", shipmentId);
		result.addObject("p", page);
		result.addObject("total_pages", ownShipments.getTotalPages());
		result.addObject("urlPage", "shipment/user/list.do?page=");

		
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
		String messageError;
		Collection<ShipmentOffer> shipmentOffers;

		if (binding.hasErrors()) {
			result = createEditModelAndView(shipmentForm);
		} else {
			try {
				Shipment shipment;

				shipment = shipmentFormService.reconstruct(shipmentForm);
				
				//Añadimos la comisión al precio
				shipment.setPrice(Math.round((shipment.getPrice()*1.15) * 100.0)/100.0);
				
				shipmentOffers = shipmentOfferService.findAllByShipmentId(shipment.getId());
				Assert.isTrue(shipmentOffers.isEmpty(), "message.error.shipment.edit");
				
				shipmentService.save(shipment);

				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				log.error(oops.getMessage());
				messageError = "shipment.commit.error";
				if(oops.getMessage().contains("message.error")){
					messageError=oops.getMessage();
				}
				result = createEditModelAndView(shipmentForm, messageError);
			}
		}

		return result;
	}
			
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ShipmentForm shipmentForm, BindingResult binding) {
		ModelAndView result;

		try {
			shipmentFormService.delete(shipmentForm);
			result = new ModelAndView("redirect:list.do");
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
			result = shipmentController.createListModelAndView(shipment.getId());
		    result.addObject("message", messageError);
		}
		
		return result;		
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ShipmentForm shipmentForm) {
		ModelAndView result;

		result = createEditModelAndView(shipmentForm, null);
		
		return result;
	}	
	
	
	protected ModelAndView createEditModelAndView(ShipmentForm shipmentForm, String message) {
		ModelAndView result;
						
		result = new ModelAndView("shipment/edit");
		result.addObject("shipmentForm", shipmentForm);
		result.addObject("message", message);

		return result;
	}
	


}
