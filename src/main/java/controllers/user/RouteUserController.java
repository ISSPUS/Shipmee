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
import domain.Route;
import domain.User;
import domain.Vehicle;
import domain.form.RouteForm;
import services.RouteService;
import services.UserService;
import services.VehicleService;
import services.form.RouteFormService;

@Controller
@RequestMapping("/route/user")
public class RouteUserController extends AbstractController {

	static Logger log = Logger.getLogger(RouteUserController.class);

	// Services ---------------------------------------------------------------

	@Autowired
	private RouteService routeService;

	@Autowired
	private RouteFormService routeFormService;

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public RouteUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "1") int page) {
		ModelAndView result;
		Page<Route> ownRoutes;
		Pageable pageable;
		User currentUser;
		Integer routeId;

		pageable = new PageRequest(page - 1, 5);

		ownRoutes = routeService.findAllByCurrentUser(pageable);
		currentUser = userService.findByPrincipal();
		routeId = 0;
		
		if(!ownRoutes.getContent().isEmpty()){
			routeId = ownRoutes.getContent().iterator().next().getCreator().getId();
		}

		result = new ModelAndView("route/user");
		result.addObject("routes", ownRoutes.getContent());
		result.addObject("user", currentUser);
		result.addObject("currentUser", currentUser);
		result.addObject("routeId", routeId);
		result.addObject("p", page);
		result.addObject("total_pages", ownRoutes.getTotalPages());
		result.addObject("urlPage", "route/user/list.do?page=");

		return result;
	}

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
		String messageError;
		
		if (!binding.hasErrors()) {
			binding = routeFormService.checkConditionsRoute(routeForm,binding);
		}
		if (binding.hasErrors()) {
			result = createEditModelAndView(routeForm);
		} else {
			try {
				Route route;
				String departure, arrive, origin, destination, itemEnvelope;
				int vehicleId;

				id = routeForm.getRouteId();

				if (id == 0) {
					departure = routeForm.getDepartureTime();
					arrive = routeForm.getArriveTime();
					origin = routeForm.getOrigin();
					destination = routeForm.getDestination();
					itemEnvelope = routeForm.getItemEnvelope();
					
					if(routeForm.getVehicle() != null) {
						vehicleId = routeForm.getVehicle().getId();
					} else {
						vehicleId = 0;
					}
					
					result = new ModelAndView("redirect:../../sizePrice/user/create.do?departure=" + departure
							+ "&arrive=" + arrive + "&origin=" + origin + "&destination=" + destination
							+ "&itemEnvelope=" + itemEnvelope + "&vehicleId=" + vehicleId);
				} else {
					route = routeFormService.reconstruct(routeForm);
					route = routeService.save(route);

					result = new ModelAndView("redirect:../../sizePrice/user/edit.do?routeId=" + route.getId());
				}
			} catch (Throwable oops) {
				log.error(oops.getMessage());
				messageError = "route.commit.error";
				if(oops.getMessage().contains("message.error")){
					messageError=oops.getMessage();
				}
				result = createEditModelAndView(routeForm, messageError);
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(RouteForm routeForm, BindingResult binding) {
		ModelAndView result;
		String messageError;

		try {
			routeFormService.delete(routeForm);
			result = new ModelAndView("redirect:../../route/user/list.do");
		} catch (Throwable oops) {
			log.error(oops.getMessage());
			messageError = "route.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError=oops.getMessage();
			}
			result = createEditModelAndView(routeForm, messageError);
		}

		return result;
	}

	@RequestMapping(value = "/contract", method = RequestMethod.GET)
	public ModelAndView contractRoute(@RequestParam int routeId, @RequestParam int sizePriceId) {
		ModelAndView result;
		String messageError;

		Route route = routeService.findOne(routeId);

		try {
			result = new ModelAndView("redirect:../../feepayment/user/create.do?type=1&id=" + routeId + "&sizePriceId=" + sizePriceId);
		} catch (Throwable oops) {
			messageError = "route.commit.error";
			if (oops.getMessage().contains("message.error")) {
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
		Page<Vehicle> vehicles;
		User user;
		
		Pageable page = new PageRequest(0, Integer.MAX_VALUE);
		vehicles = vehicleService.findAllNotDeletedByUser(page);
		user = userService.findByPrincipal();

		result = new ModelAndView("route/edit");
		result.addObject("routeForm", routeForm);
		result.addObject("message", message);
		result.addObject("vehicles", vehicles.getContent());
		result.addObject("user", user);

		return result;
	}

	protected ModelAndView createEditModelAndView(Route route, String message) {
		ModelAndView result;
		Page<Vehicle> vehicles;
		
		Pageable page = new PageRequest(0, Integer.MAX_VALUE);
		vehicles = vehicleService.findAllNotDeletedByUser(page);

		result = new ModelAndView("route/search");
		result.addObject("route", route);
		result.addObject("message", message);
		result.addObject("vehicles", vehicles.getContent());

		return result;
	}

}