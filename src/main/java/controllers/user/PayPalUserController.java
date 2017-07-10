package controllers.user;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paypal.base.rest.PayPalRESTException;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.types.ap.PayResponse;

import controllers.AbstractController;
import domain.FeePayment;
import domain.PayPalObject;
import domain.ShipmentOffer;
import services.FeePaymentService;
import services.PayPalService;
import services.RouteOfferService;
import services.ShipmentOfferService;
import utilities.PayPalConfig;

@Controller
@RequestMapping("/user/payPal")
public class PayPalUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	
	@Autowired
	private PayPalService payPalService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	@Autowired
	private FeePaymentService feePaymentService;
	
	@Autowired
	private RouteOfferService routeOfferService;
	
	static Logger log = Logger.getLogger(PayPalUserController.class);

	
	// Constructors -----------------------------------------------------------
	
	public PayPalUserController() {
		super();
	}
		
	// Creation ------------------------------------------------------------------		

	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView adaptiveCreate(@RequestParam int type, @RequestParam int id,
			@RequestParam (required=false, defaultValue="0") Integer sizePriceId, @RequestParam (required=false, defaultValue = "0") Double amount,
			@RequestParam (required=false) String description, @RequestParam (required=false, defaultValue="0") int shipmentId) {
		ModelAndView result;

		PayResponse p = null;
		FeePayment feePayment;

		try {
			feePayment = feePaymentService.createAndSave(type, id, sizePriceId, amount, description, shipmentId);
			
			p = payPalService.authorizePay(feePayment.getId());
			result = new ModelAndView("redirect:" + PayPalConfig.getPayRedirectUrl()+ "?cmd=_ap-payment&paykey=" + p.getPayKey());

		} catch (Throwable e) {
			log.error(e.getStackTrace(),e);
			
			// Debería devolverte a la vista donde te pregunta como pagar y mostrar ahí un error diciendo que actualmente hay un problema
			// 		con el metodo seleccionado. Que pruebe posteriormente o con tarjeta
			result = new ModelAndView("redirect:/?message=error");
		}
		return result;
	}
	
	@RequestMapping(value = "/returnPayment", method = RequestMethod.GET)
	public ModelAndView adaptiveReturnCreate(@RequestParam String trackingId) {
		ModelAndView result;
		ShipmentOffer so;
		PayPalObject po = null;

		try {
			po = payPalService.refreshPaymentStatusFromPaypal(trackingId);
			
			// po = payPalService.findByTrackingId(trackingId);
			
			if (po.getFeePayment().getShipmentOffer() != null){
				so = payPalService.findByTrackingId(trackingId).getFeePayment().getShipmentOffer();
				if (!po.getPayStatus().equals("CREATED"))
					so = shipmentOfferService.accept(so.getId());
				result = new ModelAndView("redirect:/shipmentOffer/user/list.do?shipmentId=" + so.getShipment().getId());
			}else{
				routeOfferService.save(routeOfferService.findOne(po.getFeePayment().getRouteOffer().getId()));
				result = new ModelAndView("redirect:/routeOffer/user/list.do?routeId=" + po.getFeePayment().getRouteOffer().getRoute().getId());
			}
			// Pagado y registrado correctamente

		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | PayPalRESTException | IOException | InterruptedException e) {
			log.error(e.getStackTrace(),e);
			
			// Si algo ha ido mal decirle al cliente que pruebe a acceder más tarde a user/payPal/returnPayment?trackingId= this.trackingid
			
			result = new ModelAndView("redirect:/?status=somethingWrongWithPayPal");
		}
		return result;
	}
	
	
	// Ancillary methods ------------------------------------------------------


}

