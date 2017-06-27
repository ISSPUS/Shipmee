package utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

import com.paypal.base.rest.PayPalRESTException;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.ExecutePaymentRequest;
import com.paypal.svcs.types.ap.ExecutePaymentResponse;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.PaymentDetailsRequest;
import com.paypal.svcs.types.ap.PaymentDetailsResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.ap.RefundRequest;
import com.paypal.svcs.types.ap.RefundResponse;
import com.paypal.svcs.types.common.RequestEnvelope;

public class PayPal {
	
	/**
	 * 
	 * @param receiverEmail: Email de la persona que debe recibir el dinero
	 * @param total: Coste total de la operación
	 * @param commission: Importe que se quedará Shipmee (incluyendo las comisiones de PayPal)
	 * @param uniqueTrackingId: Identificador ÚNICO a usar durante toda la transacción
	 * @return PayResponse
	 * @throws SSLConfigurationException
	 * @throws InvalidCredentialException
	 * @throws UnsupportedEncodingException
	 * @throws HttpErrorException
	 * @throws InvalidResponseDataException
	 * @throws ClientActionRequiredException
	 * @throws MissingCredentialException
	 * @throws OAuthException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static PayResponse startAdaptiveTransaction(String receiverEmail, Double total, Double commission,
			String uniqueTrackingId, String returnPath)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, IOException, InterruptedException {
//		Assert.isTrue(receiverEmail.length() > 0);
		Assert.isTrue(total > commission);

		RequestEnvelope env = new RequestEnvelope("en_US");
		PayRequest payRequest = new PayRequest();

		payRequest.setRequestEnvelope(env);

		List<Receiver> receiver = new ArrayList<Receiver>();
		Receiver rec = new Receiver();
		rec.setAmount(total);
		rec.setEmail(PayPalConfig.getBusinessEmail());

		payRequest.setActionType("CREATE");  // En caso de querer actualizar el pago únicamente


		if (receiverEmail != null && !receiverEmail.equals("")) {
//			rec.setPrimary(true);
//			
//			Receiver rec2 = new Receiver();
//			rec2.setAmount(total - commission);
//			rec2.setEmail(receiverEmail);
//			rec2.setPrimary(false);
//			receiver.add(rec2);
//			payRequest.setActionType("PAY_PRIMARY");	// En caso de querer pagar a la empresa y dejar el pago al usuario final autorizado
//			payRequest.setFeesPayer("PRIMARYRECEIVER");

		}
		
		receiver.add(rec);

		ReceiverList receiverlst = new ReceiverList(receiver);

		payRequest.setReceiverList(receiverlst);
		payRequest.setCurrencyCode("EUR");
		
		// responseEnvelope.timestamp=2017-04-19T04%3A00%3A27.676-07%3A00&responseEnvelope.ack=Success&responseEnvelope.correlationId=de635edcbc526&responseEnvelope.build=32250686&payKey=AP-8WD959703H6814522&paymentExecStatus=CREATED

		// Comprobar que el trackingId sea único
		payRequest.setTrackingId(uniqueTrackingId);

		payRequest.setCancelUrl(PayPalConfig.getUrlBase() + "/" + returnPath + "?trackingId=" + payRequest.getTrackingId());
		payRequest.setReturnUrl(PayPalConfig.getUrlBase() + "/" + returnPath + "?trackingId=" + payRequest.getTrackingId());

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(PayPalConfig.getConfigurationMap());

		PayResponse payResponse = adaptivePaymentsService.pay(payRequest, PayPalConfig.getConfigurationMap().get("acct1.UserName"));

		// Debemos guardar en la DB el PayKey
		return payResponse;
	}
	
	/**
	 * 
	 * @param trackingID: Identificador ÚNICO de toda la transacción
	 * @return
	 * @throws PayPalRESTException
	 * @throws SSLConfigurationException
	 * @throws InvalidCredentialException
	 * @throws UnsupportedEncodingException
	 * @throws HttpErrorException
	 * @throws InvalidResponseDataException
	 * @throws ClientActionRequiredException
	 * @throws MissingCredentialException
	 * @throws OAuthException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static PaymentDetailsResponse fetchDetailsAdaptiveTransaction(String trackingID)
			throws PayPalRESTException, SSLConfigurationException, InvalidCredentialException,
			UnsupportedEncodingException, HttpErrorException, InvalidResponseDataException,
			ClientActionRequiredException, MissingCredentialException, OAuthException, IOException,
			InterruptedException {
		
		RequestEnvelope env = new RequestEnvelope("en_US");
		PaymentDetailsRequest req = new PaymentDetailsRequest(env);

		req.setTrackingId(trackingID);

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(PayPalConfig.getConfigurationMap());

		PaymentDetailsResponse payDetailResponse = adaptivePaymentsService.paymentDetails(req);

		return payDetailResponse;
	}
	
	/**
	 * 
	 * @param payKey: Identificador único del pago creado por PAYPAL (se puede obtener con fetchDetailsAdaptiveTransaction)
	 * @return
	 * @throws PayPalRESTException
	 * @throws SSLConfigurationException
	 * @throws InvalidCredentialException
	 * @throws UnsupportedEncodingException
	 * @throws HttpErrorException
	 * @throws InvalidResponseDataException
	 * @throws ClientActionRequiredException
	 * @throws MissingCredentialException
	 * @throws OAuthException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static ExecutePaymentResponse adaptiveSendToSenconds(String payKey) throws PayPalRESTException,
			SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException, HttpErrorException,
			InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException, OAuthException,
			IOException, InterruptedException {

		RequestEnvelope env = new RequestEnvelope("en_US");
		ExecutePaymentRequest req = new ExecutePaymentRequest(env, payKey);

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(PayPalConfig.getConfigurationMap());

		ExecutePaymentResponse payResponse = adaptivePaymentsService.executePayment(req);

		return payResponse;
	}
	
	/**
	 * 
	 * @param trackingID: Identificador ÚNICO de toda la transacción
	 * @param rec1: Persona que recibio inicialmente el importe 1
	 * @param rec2: Persona que recibio inicialmente el importe 2
	 * @return
	 * @throws PayPalRESTException
	 * @throws SSLConfigurationException
	 * @throws InvalidCredentialException
	 * @throws UnsupportedEncodingException
	 * @throws HttpErrorException
	 * @throws InvalidResponseDataException
	 * @throws ClientActionRequiredException
	 * @throws MissingCredentialException
	 * @throws OAuthException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static RefundResponse refundAdaptiveTransaction(String trackingID, Receiver rec1, Receiver rec2)
			throws PayPalRESTException, SSLConfigurationException, InvalidCredentialException,
			UnsupportedEncodingException, HttpErrorException, InvalidResponseDataException,
			ClientActionRequiredException, MissingCredentialException, OAuthException, IOException,
			InterruptedException {
		// No es posible devolver el dinero cuando se ha pagado a un tercero puesto que no poseemos "third-party access"
		RequestEnvelope env = new RequestEnvelope("en_US");
		RefundRequest req = new RefundRequest(env);
		
		req.setTrackingId(trackingID);
		req.setCurrencyCode("EUR");
		
//		// REEMBOLSO PARCIAL
//		Receiver receiver = new Receiver(amount);
//
//		receiver.setEmail(senderEmail);
//		req.setTrackingId(trackingID);

		List<Receiver> receiverList = new ArrayList<Receiver>();
		receiverList.add(rec1);
		if(rec2 != null){
			receiverList.add(rec2);
		}
		ReceiverList receiverlst = new ReceiverList(receiverList);
		req.setReceiverList(receiverlst);

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(PayPalConfig.getConfigurationMap());

		RefundResponse refundResponse = adaptivePaymentsService.refund(req);

		return refundResponse;
	}
	

}
