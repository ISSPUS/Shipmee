package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import domain.Shipment;
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
	// Constructors -----------------------------------------------------------
	
	public ShipmentController() {
		super();
	}
		
	// Search ------------------------------------------------------------------		

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int userId) {
		ModelAndView result;
		Collection<Shipment> shipments;
		

		shipments = shipmentService.findAll();
				
		result = new ModelAndView("shipment/user");
		result.addObject("shipments", shipments);
		result.addObject("user", userService.findByPrincipal());

		return result;
	}		
		@RequestMapping(value = "/search")
		public ModelAndView search(String origin, String destination, @RequestParam(required=false) String date,
				@RequestParam(required=false) String hour, @RequestParam(required=false) String envelope,
				@RequestParam(required=false) String itemSize) {
			ModelAndView result;
			Collection<Shipment> shipments;

			shipments = shipmentService.searchShipment(origin, destination, date, hour, envelope, itemSize);
						
			result = new ModelAndView("shipment/search");
			result.addObject("shipments", shipments);
			result.addObject("origin", origin);
			result.addObject("destination", destination);

			return result;
			}
		
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView seeThread(@RequestParam int shipmentId) {
			ModelAndView result;
			
			result = createListModelAndView(shipmentId);
			
			return result;

		}
		
		private ModelAndView createListModelAndView(int shipmentId){
			ModelAndView result;
			Shipment shipment;
			
			shipment = shipmentService.findOne(shipmentId);
			
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

			

			return result;
		}
}