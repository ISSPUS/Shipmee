package controllers.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paypal.api.payments.Payment;
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
import domain.User;
import domain.form.ActorForm;
import services.UserService;
import services.form.ActorFormService;
import utilities.PayPal;
import utilities.PayPalConfig;

@Controller
@RequestMapping("/user/payPal")
public class PayPalUserController extends AbstractController {
	
	// Services ---------------------------------------------------------------

	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorFormService actorFormService;
	
	
	// Constructors -----------------------------------------------------------
	
	public PayPalUserController() {
		super();
	}
		
	// Creation ------------------------------------------------------------------		

	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		Payment p = null;
		
		try {
			p = PayPal.startTransaction(PayPal.generateTransaction(), PayPal.generatePayer());
			System.out.println("Identificador del pago a guardar: " + p.getId());
			System.out.println("Identificador de la venta: " + p.getTransactions().get(1).getRelatedResources().get(1).getSale().getId());
			System.out.println("Estado del pago a guardar: " + p.getState());
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result = new ModelAndView("redirect:" + p.getLinks().get(1).getHref());

		return result;
	}
	
	@RequestMapping(value = "/doit", method = RequestMethod.GET)
	public ModelAndView doit(@RequestParam String paymentId, @RequestParam String token, @RequestParam String PayerID) {
		ModelAndView result;

		Payment p = null;
		
		try {
			p = PayPal.endTransaction(paymentId, PayerID);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result = new ModelAndView("redirect:" + p.getLinks().get(1).getHref());

		return result;
	}
	
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

			p = PayPal.refundAdaptativeTransaction(trackingId,
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
	
	@RequestMapping(value = "/requestPemissions", method = RequestMethod.GET)
	public ModelAndView requestPemissionsGet() {
		ModelAndView result;

		String res;
		
		res = PayPal.requestPemissions();
		
		result = new ModelAndView("redirect:" + res);

		return result;
	}
	
	@RequestMapping(value = "/requestPemissions", method = RequestMethod.POST)
	public ModelAndView requestPemissionsPost(String code) {
		ModelAndView result;

		String res = null;
		
		try {
			res = PayPal.obtainRefreshToken(code);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(res);
		
		result = new ModelAndView("redirect:");

		return result;
	}
	
	// Ancillary methods ------------------------------------------------------


}

