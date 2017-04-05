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
import domain.Vehicle;
import services.VehicleService;

@Controller
@RequestMapping("/vehicle/user")
public class VehicleUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private VehicleService vehicleService;
	
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
		Vehicle vehicle;

		vehicle = vehicleService.create();
		result = createEditModelAndView(vehicle);

		return result;
	}

	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int vehicleId) {
		ModelAndView result;
		Vehicle vehicle;

		vehicle = vehicleService.findOne(vehicleId);		
		Assert.notNull(vehicle);
		result = createEditModelAndView(vehicle);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Vehicle vehicle, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(vehicle);
		} else {
			try {				
				vehicle = vehicleService.save(vehicle);
				
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {

				result = createEditModelAndView(vehicle, "vehicle.commit.error");				
			}
		}

		return result;
	}
			
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Vehicle vehicle, BindingResult binding) {
		ModelAndView result;

		try {
			vehicleService.delete(vehicle);
			result = new ModelAndView("redirect:list.do");						
		} catch (Throwable oops) {
			result = createEditModelAndView(vehicle, "vehicle.commit.error");
		}

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Vehicle vehicle) {
		ModelAndView result;

		result = createEditModelAndView(vehicle, null);
		
		return result;
	}	
	
	protected ModelAndView createEditModelAndView(Vehicle vehicle, String message) {
		ModelAndView result;
						
		result = new ModelAndView("vehicle/edit");
		result.addObject("vehicle", vehicle);
		result.addObject("message", message);

		return result;
	}

}
