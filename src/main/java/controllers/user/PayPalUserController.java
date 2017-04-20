package controllers.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
import utilities.PayPal;
import utilities.PayPalConfig;

@Controller
@RequestMapping("/user/payPal")
public class PayPalUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------

//	@Autowired
//	private UserService userService;
	
	// Constructors -----------------------------------------------------------
	
	public PayPalUserController() {
		super();
	}
		
	// Creation ------------------------------------------------------------------		

	
	@RequestMapping(value = "/adaptive/pay", method = RequestMethod.GET)
	public ModelAndView adaptiveCreate() {
		ModelAndView result;

		PayResponse p = null;
		
		try {
			p = PayPal.startAdaptiveTransaction("manoloorientacion-buyer-1@gmail.com", 10.0, 3.0, PayPalConfig.generateTrackingId());
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = new ModelAndView("redirect:" + PayPalConfig.getPayRedirectUrl()+ "?cmd=_ap-payment&paykey=" + p.getPayKey());

		return result;
	}
	
	@RequestMapping(value = "/adaptive/accept", method = RequestMethod.GET)
	public ModelAndView adaptiveSendToSenconds(@RequestParam String trackingId) {
		ModelAndView result;

		ExecutePaymentResponse p = null;
		PaymentDetailsResponse details = null;
		
		try {
			details = PayPal.fetchDetailsAdaptiveTransaction(trackingId);
			
			switch (details.getStatus()) {
			case "INCOMPLETE":
				// Se ha pagado pero el dinero todavia no le ha llegado al otro
				p = PayPal.adaptiveSendToSenconds(details.getPayKey());
				break;

			default:
				break;
			}
			
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
	
	@RequestMapping(value = "/adaptive/refund", method = RequestMethod.GET)
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

