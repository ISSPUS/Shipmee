package controllers.user;


import java.util.Collection;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Vehicle;
import domain.form.VehicleForm;
import services.VehicleService;
import services.form.VehicleFormService;

@Controller
@RequestMapping("/vehicle/user")
public class VehicleUserController extends AbstractController {
	
	static Logger log = Logger.getLogger(VehicleUserController.class);

	// Services ---------------------------------------------------------------
	
	@Autowired
	private VehicleService vehicleService;
	@Autowired
	private VehicleFormService vehicleFormService;
	// Constructors -----------------------------------------------------------
	
	public VehicleUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Vehicle> vehicles;
		
		vehicles = vehicleService.findAllNotDeletedByUser();
		
		result = new ModelAndView("vehicle/list");
		result.addObject("vehicles", vehicles);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		VehicleForm vehicleForm;

		vehicleForm = vehicleFormService.create();
		result = createEditModelAndView(vehicleForm);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int vehicleId) {
		ModelAndView result;
		Vehicle vehicle;

		vehicle = vehicleService.findOne(vehicleId);
		Assert.notNull(vehicle);
		
		VehicleForm vehicleForm = vehicleFormService.contruct(vehicle.getId());
		result = createEditModelAndView(vehicleForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid VehicleForm vehicleForm, BindingResult binding) {
		ModelAndView result;
		String messageError;

		if (binding.hasErrors()) {
			result = createEditModelAndView(vehicleForm);
		} else {
			try {
				Vehicle vehicle = vehicleFormService.reconstruct(vehicleForm);
				vehicle = vehicleService.save(vehicle);
				
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				log.error(oops.getMessage());
				messageError = "vehicle.commit.error";
				if(oops.getMessage().contains("message.error")){
					messageError=oops.getMessage();
				}
				result = createEditModelAndView(vehicleForm, messageError);				
			}
		}

		return result;
	}
			
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(VehicleForm vehicleForm, BindingResult binding) {
		ModelAndView result;
		String messageError;

		try {
			Vehicle vehicle = vehicleService.findOne(vehicleForm.getVehicleId());
			vehicleService.delete(vehicle);
			result = new ModelAndView("redirect:list.do");						
		} catch (Throwable oops) {
			log.error(oops.getMessage());
			messageError = "vehicle.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError=oops.getMessage();
			}
			result = createEditModelAndView(vehicleForm, messageError);
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(VehicleForm vehicleform) {
		ModelAndView result;

		result = createEditModelAndView(vehicleform, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(VehicleForm vehicleForm, String message) {
		ModelAndView result;
						
		result = new ModelAndView("vehicle/edit");
		result.addObject("vehicleForm", vehicleForm);
		result.addObject("message", message);

		return result;
	}

}
