package services.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import domain.Route;
import domain.User;
import domain.form.RouteForm;
import services.RouteService;
import services.UserService;

@Service
@Transactional
public class RouteFormService {
	
	static Logger log = Logger.getLogger(RouteFormService.class);

	// Supporting services ----------------------------------------------------

	@Autowired
	private RouteService routeService;
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public RouteFormService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public RouteForm create() {
		RouteForm result;
		
		result = new RouteForm();
		
		result.setRouteId(0);
		
		return result;
	}
	
	public Route reconstruct(RouteForm routeForm) {
		Route result;
		Date departureTime;
		Date arriveTime;
		
		departureTime = null;
		arriveTime = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		try {
			departureTime = formatter.parse(routeForm.getDepartureTime());
			arriveTime = formatter.parse(routeForm.getArriveTime());
		} catch (ParseException e) {
			log.error("Excepcion al parsear:",e);
		}
				
		if (routeForm.getRouteId() == 0) {
			result = routeService.create();
			
			result.setOrigin(routeForm.getOrigin());
			result.setDestination(routeForm.getDestination());
			result.setItemEnvelope(routeForm.getItemEnvelope());
			result.setVehicle(routeForm.getVehicle());
			result.setArriveTime(arriveTime);
			result.setDepartureTime(departureTime);
			
			
		} else if(routeForm.getRouteId() != 0) {			
			result = routeService.findOne(routeForm.getRouteId());
			result.setOrigin(routeForm.getOrigin());
			result.setDestination(routeForm.getDestination());
			result.setItemEnvelope(routeForm.getItemEnvelope());
			result.setVehicle(routeForm.getVehicle());
			result.setArriveTime(arriveTime);
			result.setDepartureTime(departureTime);
		} else {
			result = null;
		}
		
		return result;
	}

	public RouteForm findOne(int routeId) {
		RouteForm result;
		Route route;
		String arriveTime; 
		String departureTime;
		User user;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		result = this.create();
		route = routeService.findOne(routeId);
		user = userService.findByPrincipal();
		
		Assert.isTrue(user.getId() == route.getCreator().getId());
		
		arriveTime = formatter.format(route.getArriveTime());
		departureTime = formatter.format(route.getDepartureTime());
		
		result.setOrigin(route.getOrigin());
		result.setDestination(route.getDestination());
		result.setItemEnvelope(route.getItemEnvelope());
		result.setVehicle(route.getVehicle());
		result.setArriveTime(arriveTime);
		result.setDepartureTime(departureTime);
		
		return result;
	}

	public void delete(RouteForm routeForm) {
		Route route;
		route = routeService.findOne(routeForm.getRouteId());
		routeService.delete(route);
	}

	public BindingResult checkConditionsRoute(RouteForm routeForm,BindingResult binding) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date departureTime;
		Date arriveTime;

		departureTime = null;
		arriveTime = null;
		
		try {
			departureTime = formatter.parse(routeForm.getDepartureTime());
			arriveTime = formatter.parse(routeForm.getArriveTime());
		} catch (ParseException e) {
			log.error("Excepcion al parsear:",e);
		}
		
		if(departureTime!=null)
			this.addBinding(binding, departureTime.after(new Date()), "departureTime", "message.error.route.futureDepartureDate", null);
		if(arriveTime!=null){
			this.addBinding(binding, arriveTime.after(new Date()), "arriveTime", "message.error.route.futureArrivalDate", null);
			this.addBinding(binding, arriveTime.after(departureTime), "arriveTime", "message.error.route.checkArriveTimeAfterDepartureDate", null);
		}
			
		this.addBinding(binding, routeForm.getItemEnvelope().equals("Open") || routeForm.getItemEnvelope().equals("Closed") ||
				routeForm.getItemEnvelope().equals("Both") || routeForm.getItemEnvelope().equals("Abierto") ||
				routeForm.getItemEnvelope().equals("Cerrado") || routeForm.getItemEnvelope().equals("Ambos"), "itemEnvelope", "message.error.route.checkItemEnvelope", null);

		
		return binding;
	}
	private void addBinding(Errors errors, boolean mustBeTrue, String field, String validationError, Object[] other){
		if (!mustBeTrue){
			errors.rejectValue(field, validationError, other, "");
		}
	}
}
