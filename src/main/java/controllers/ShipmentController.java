package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import domain.Shipment;
import domain.ShipmentOffer;
import domain.User;
import services.ActorService;
import services.ShipmentOfferService;
import services.ShipmentService;
import services.UserService;

@Controller
@RequestMapping("/shipment")
public class ShipmentController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private ShipmentService shipmentService;	

	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	// Constructors -----------------------------------------------------------
	
	public ShipmentController() {
		super();
	}
		
	// Search ------------------------------------------------------------------		

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int userId,
			@RequestParam(required=false, defaultValue="1") int page) {
		ModelAndView result;
		Page<Shipment> shipments;
		Pageable pageable;
		User user;
		User currentUser;

		pageable = new PageRequest(page - 1, 5);

		shipments = shipmentService.findAllByUserId(userId, pageable);
		user = userService.findOne(userId);
		currentUser = null;
		
		if(actorService.checkAuthority("USER")){
			currentUser = userService.findByPrincipal();
		}
				
		result = new ModelAndView("shipment/user");
		result.addObject("shipments", shipments.getContent());
		result.addObject("user", user);
		result.addObject("currentUser", currentUser);
		result.addObject("p", page);
		result.addObject("total_pages", shipments.getTotalPages());
		result.addObject("urlPage", "route/list.do?userId="+userId+"&page=");

		return result;
	}		
		@RequestMapping(value = "/search")
		public ModelAndView search(String origin, String destination, @RequestParam(required=false) String date,
				@RequestParam(required=false) String hour, @RequestParam(required=false) String envelope,
				@RequestParam(required=false) String itemSize,@RequestParam(required = false, defaultValue = "1") int page) {
			ModelAndView result;
			Page<Shipment> shipments;
			Pageable pageable;
			
			if (itemSize != null && itemSize.equals("")){
				itemSize=null;
			}
			
			if (hour != null && hour.equals("")){
				hour=null;
			}

			pageable = new PageRequest(page - 1, 5);
			shipments = shipmentService.searchShipment(origin, destination, date, hour, envelope, itemSize,pageable);
						
			result = new ModelAndView("shipment/search");
			result.addObject("shipments", shipments.getContent());
			result.addObject("origin", origin);
			result.addObject("destination", destination);
			result.addObject("form_date", date);
			result.addObject("form_hour", hour);
			result.addObject("form_envelope", envelope);
			result.addObject("form_itemSize", itemSize);
			result.addObject("p", page);
			result.addObject("total_pages", shipments.getTotalPages());
			
			String url = getUrlParametros(origin,destination,date,hour,envelope,itemSize);
			result.addObject("urlPage", "shipment/search.do?"+url+"&page=");
			return result;
			}
		
		private String getUrlParametros(String origin, String destination, String date, String hour, String envelope,
				String itemSize) {
			String url = "";
			Map<String,String> parametrosBusqueda = new HashMap<String,String>();
			parametrosBusqueda.put("origin", origin);
			parametrosBusqueda.put("destination", destination);
			parametrosBusqueda.put("date", date);
			parametrosBusqueda.put("hour", hour);
			parametrosBusqueda.put("envelope", envelope);
			
			for (String clave : parametrosBusqueda.keySet()) {
				String valor = parametrosBusqueda.get(clave);
				if(valor!=null && !valor.equals("")){
					url=url+"&"+clave+"="+valor;
				}
			}
			return url;
		}
		
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView seeThread(@RequestParam int shipmentId) {
			ModelAndView result;
			
			result = createListModelAndView(shipmentId);
			
			return result;

		}
		
		public ModelAndView createListModelAndView(int shipmentId){
			ModelAndView result;
			Shipment shipment;
			User currentUser;
			Boolean shipmentOffersIsEmpty;
			Collection<ShipmentOffer> shipmentOffers;
			
			shipment = shipmentService.findOne(shipmentId);
			currentUser = null;
			shipmentOffersIsEmpty = false;
			
			if(actorService.checkAuthority("ADMIN")){
				currentUser = userService.findOne(shipment.getCreator().getId());
			}else if (actorService.checkAuthority("USER")){
				currentUser = userService.findByPrincipal();
			}
			
			shipmentOffers = shipmentOfferService.findAllByShipmentId(shipment.getId());
			
			if(shipmentOffers.isEmpty()) {
				shipmentOffersIsEmpty = true;
			}
			
			String departureTime = new SimpleDateFormat("dd'/'MM'/'yyyy").format(shipment.getDepartureTime());
			String departureTimeHour = new SimpleDateFormat("HH':'mm").format(shipment.getDepartureTime());
			
			String maximumArriveTime = new SimpleDateFormat("dd'/'MM'/'yyyy").format(shipment.getMaximumArriveTime());
			String maximumArriveTimeHour = new SimpleDateFormat("HH':'mm").format(shipment.getMaximumArriveTime());

			
			result = new ModelAndView("shipment/display");
			result.addObject("shipment", shipment);
			result.addObject("departureTime", departureTime);
			result.addObject("departureTime_hour", departureTimeHour);
			result.addObject("maximumArriveTime", maximumArriveTime);
			result.addObject("maximumArriveTime_hour", maximumArriveTimeHour);
			result.addObject("user", currentUser);
			result.addObject("shipmentOffersIsEmpty", shipmentOffersIsEmpty);

			return result;
		}
}