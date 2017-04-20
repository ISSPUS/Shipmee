package controllers.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import com.paypal.svcs.types.ap.ExecutePaymentResponse;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.PaymentDetailsResponse;
import com.paypal.svcs.types.ap.RefundResponse;

import controllers.AbstractController;
import domain.PayPalObject;
import domain.ShipmentOffer;
import services.PayPalService;
import services.ShipmentOfferService;
import utilities.PayPal;
import utilities.PayPalConfig;

@Controller
@RequestMapping("/user/payPal")
public class PayPalUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------

//	@Autowired
//	private UserService userService;
	
	@Autowired
	private PayPalService payPalService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	static Logger log = Logger.getLogger(PayPalUserController.class);

	
	// Constructors -----------------------------------------------------------
	
	public PayPalUserController() {
		super();
	}
		
	// Creation ------------------------------------------------------------------		

	
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView adaptiveCreate(@RequestParam(required=false, defaultValue="-1") int routeOfferId,
			@RequestParam(required=false, defaultValue="-1") int shipmentOfferId) {
		ModelAndView result;

		PayResponse p = null;

		try {
			p = payPalService.authorizePay(routeOfferId, shipmentOfferId);
			result = new ModelAndView("redirect:" + PayPalConfig.getPayRedirectUrl()+ "?cmd=_ap-payment&paykey=" + p.getPayKey());

		} catch (Throwable e) {
			e.printStackTrace();
			log.error(e,e);
			result = new ModelAndView("redirect:/?message=error");
		}
		return result;
	}
	
	@RequestMapping(value = "/returnPayment", method = RequestMethod.GET)
	public ModelAndView adaptiveSendToSenconds(@RequestParam String trackingId) {
		ModelAndView result;
		String status = "ok";
		ShipmentOffer so;
		PayPalObject po;

		try {
			payPalService.returnPaymentFromPaypal(trackingId);
			
			po = payPalService.findByTrackingId(trackingId);
			
			if (po.getFeePayment().getShipmentOffer() != null){
				so = shipmentOfferService.accept(payPalService.findByTrackingId(trackingId).getFeePayment().getShipmentOffer().getId());
				result = new ModelAndView("redirect:/shipmentOffer/user/list.do?shipmentId=" + so.getShipment().getId());
			}else{
				System.out.println("Estudiar pra routeOffer");
				result = new ModelAndView("redirect:/?status=" + status);
			}

		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | PayPalRESTException | IOException | InterruptedException e) {
			log.error(e);
			status = "error";
			result = new ModelAndView("redirect:/?status=" + status);
		}
		return result;
	}
	
	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	public ModelAndView adaptiveRefundToSenconds(@RequestParam String trackingId) {
		ModelAndView result;

		RefundResponse p = null;
		PaymentDetailsResponse details = null;
		
		try {
			details = PayPal.fetchDetailsAdaptiveTransaction(trackingId);
			
			System.out.println("Actualmente solo se puede devolver dinero a alguien que no le ha pasado el dinero al usuario final");

			p = PayPal.refundAdaptiveTransaction(trackingId,
					details.getPaymentInfoList().getPaymentInfo().get(0).getReceiver()
					,details.getPaymentInfoList().getPaymentInfo().get(1).getReceiver());
			
		} catch (SSLConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidResponseDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientActionRequiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingCredentialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		result = new ModelAndView("redirect:");

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------


}

