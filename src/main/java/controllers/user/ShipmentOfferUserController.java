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
import domain.ShipmentOffer;
import domain.User;
import services.ShipmentOfferService;
import services.ShipmentService;
import services.UserService;

@Controller
@RequestMapping("/shipmentOffer/user")
public class ShipmentOfferUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShipmentService shipmentService;

	// Constructors -----------------------------------------------------------

	public ShipmentOfferUserController() {
		super();
	}

	// Listing ----------------------------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false, defaultValue="-1") int shipmentId,
			@RequestParam(required=false, defaultValue="-1") int userId,
			@RequestParam(required=false, defaultValue="1") int page) {
		ModelAndView result;
		Page<ShipmentOffer> shipmentOffers;
		Pageable pageable;
		User currentUser;
		Shipment shipment;
		
		pageable = new PageRequest(page - 1, 5);
		
		shipmentOffers = shipmentOfferService.findAllByOrShipmentIdAndOrUserId(shipmentId, userId, pageable);
		currentUser = userService.findByPrincipal();
		shipment = shipmentService.findOne(shipmentId);
		
		result = new ModelAndView("shipmentOffer/list");
		result.addObject("shipmentOffers", shipmentOffers.getContent());
		result.addObject("p", page);
		result.addObject("total_pages", shipmentOffers.getTotalPages());
		result.addObject("shipmentId", shipmentId);
		result.addObject("userId", userId);
		result.addObject("currentUser", currentUser);
		result.addObject("shipment", shipment);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int shipmentId) {
		ModelAndView result;
		ShipmentOffer offer;

		offer = shipmentOfferService.create(shipmentId);
		result = createEditModelAndView(offer);

		return result;
	}

	@RequestMapping(value = "/createClone", method = RequestMethod.GET)
	public ModelAndView createFromClone(@RequestParam int shipmentOfferId) {
		ModelAndView result;
		ShipmentOffer shipmentOffer;

		shipmentOffer = shipmentOfferService.createFromClone(shipmentOfferId);
		result = createEditModelAndView(shipmentOffer);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int shipmentOfferId) {
		ModelAndView result;
		ShipmentOffer shipmentOffer;

		shipmentOffer = shipmentOfferService.findOne(shipmentOfferId);
		Assert.notNull(shipmentOffer, "controller.user.shipmentOffer.edit.isNull");
		result = createEditModelAndView(shipmentOffer);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ShipmentOffer shipmentOffer, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(shipmentOffer);
		} else {
			try {
				shipmentOffer = shipmentOfferService.save(shipmentOffer);

				result = new ModelAndView("redirect:list.do?shipmentId=" + shipmentOffer.getShipment().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(shipmentOffer, "shipmentOffer.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam int shipmentOfferId){
		ModelAndView result;
		String messageError;
		
		ShipmentOffer shipmentOffer = shipmentOfferService.findOne(shipmentOfferId);
		
		try{

			result = new ModelAndView("redirect:../../feepayment/user/create.do?type=3&id=" + shipmentOfferId);
			
		}catch(Throwable oops){
			messageError = "shipmentOffer.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError = oops.getMessage();
			}
			result = createEditModelAndView2(shipmentOffer, messageError);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/deny", method = RequestMethod.GET)
	public ModelAndView deny(@RequestParam int shipmentOfferId){
		ModelAndView result;
		String messageError;
		
		ShipmentOffer shipmentOffer = shipmentOfferService.findOne(shipmentOfferId);
		Shipment shipment = shipmentOffer.getShipment();
		
		try{
			shipmentOfferService.deny(shipmentOfferId);
			// This reditect may be change to other url.
			result = new ModelAndView("redirect:../user/list.do?shipmentId="+shipment.getId());
		}catch(Throwable oops){
			messageError = "shipmentOffer.commit.error";
			if(oops.getMessage().contains("message.error")){
				messageError = oops.getMessage();
			}
			result = createEditModelAndView2(shipmentOffer, messageError);
		}
		
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(ShipmentOffer input) {
		ModelAndView result;

		result = createEditModelAndView(input, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ShipmentOffer input, String message) {
		ModelAndView result;

		result = new ModelAndView("shipmentOffer/edit");
		result.addObject("shipmentOffer", input);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndView2(ShipmentOffer input) {
		ModelAndView result;

		result = createEditModelAndView(input, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(ShipmentOffer input, String message) {
		ModelAndView result;
		
		result = new ModelAndView("shipmentOffer/list");
		result.addObject("shipmentOffer", input);
		result.addObject("message", message);

		return result;
	}

}