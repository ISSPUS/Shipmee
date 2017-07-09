package controllers.user;

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

import services.RouteService;
import services.UserService;
import services.VehicleService;
import services.form.RouteFormService;
import services.form.SizePriceFormService;
import controllers.AbstractController;
import domain.Route;
import domain.User;
import domain.Vehicle;
import domain.form.RouteForm;
import domain.form.SizePriceForm;

@Controller
@RequestMapping("/sizePrice/user")
public class SizePriceUserController extends AbstractController {

	static Logger log = Logger.getLogger(SizePriceUserController.class);

	// Services ---------------------------------------------------------------

	@Autowired
	private SizePriceFormService sizePriceFormService;

	@Autowired
	private RouteFormService routeFormService;

	@Autowired
	private RouteService routeService;

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public SizePriceUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam String departure, @RequestParam String arrive, @RequestParam String origin,
			@RequestParam String destination, @RequestParam String itemEnvelope, @RequestParam String vehicleId) {
		ModelAndView result;
		SizePriceForm sizePriceForm;
		Vehicle vehicle;

		sizePriceForm = sizePriceFormService.create(0);
		sizePriceForm.setSizePriceFormId(0);

		if (!vehicleId.equals("0")) {
			vehicle = vehicleService.findOne(Integer.parseInt(vehicleId));
			sizePriceForm.setVehicle(vehicle);
		}
		
		sizePriceForm.setDepartureTime(departure);
		sizePriceForm.setArriveTime(arrive);
		sizePriceForm.setOrigin(origin);
		sizePriceForm.setDestination(destination);
		sizePriceForm.setItemEnvelope(itemEnvelope);

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
		String messageError;

		if (binding.hasErrors()) {
			result = createEditModelAndView(sizePriceForm);
		} else {
			try {
				
				if (sizePriceForm.getRouteId() == 0 && sizePriceFormService.checkSizePrices(sizePriceForm)) {
					RouteForm routeForm;
					Route route;

					routeForm = routeFormService.create();
					routeForm.setDepartureTime(sizePriceForm.getDepartureTime());
					routeForm.setArriveTime(sizePriceForm.getArriveTime());
					routeForm.setOrigin(sizePriceForm.getOrigin());
					routeForm.setDestination(sizePriceForm.getDestination());
					routeForm.setItemEnvelope(sizePriceForm.getItemEnvelope());
					routeForm.setVehicle(sizePriceForm.getVehicle());

					route = routeFormService.reconstruct(routeForm);
					route = routeService.save(route);

					sizePriceForm.setRouteId(route.getId());
				}

				sizePriceFormService.reconstruct(sizePriceForm);
				result = new ModelAndView("redirect:../../route/user/list.do");
			} catch (Throwable oops) {
				log.error(oops.getMessage());
				messageError = "sizePrice.commit.error";
				if(oops.getMessage().contains("message.error")){
					messageError=oops.getMessage();
				}
				result = createEditModelAndView(sizePriceForm, messageError);
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SizePriceForm sizePriceForm, BindingResult binding) {
		ModelAndView result;

		try {
			sizePriceFormService.delete(sizePriceForm);
			result = new ModelAndView("redirect:../../route/user/list.do");
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
		User user;

		user = userService.findByPrincipal();
		result = new ModelAndView("sizePrice/edit");

		result.addObject("sizePriceForm", sizePriceForm);
		result.addObject("message", message);
		result.addObject("user",user);
		return result;
	}

}