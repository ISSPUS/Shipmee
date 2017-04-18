package utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.util.Assert;

import com.paypal.api.openidconnect.Session;
import com.paypal.api.openidconnect.Tokeninfo;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.ClientCredentials;
import com.paypal.base.rest.APIContext;
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
	
	public static Payment lolailo(String clientId, String clientSecret) throws PayPalRESTException{
		APIContext content = new APIContext(clientId, clientSecret, "sandbox");
		
		// ###Details
		// Let's you specify details of a payment amount.
		Details details = new Details();
		details.setShipping("0.03");
		details.setSubtotal("107.41");
		details.setTax("0.03");
		
		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("107.47");
		amount.setDetails(details);
		
		// ###Transaction
		// A transaction defines the contract of a
		// payment - what is the payment for and who
		// is fulfilling it. Transaction is created with
		// a `Payee` and `Amount` types
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription("This is the payment transaction description.");
		
		// The Payment creation API requires a list of
		// Transaction; add the created `Transaction`
		// to a List
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
/*		// ###Address
		// Base Address object used as shipping or billing
		// address in a payment. [Optional]
		Address billingAddress = new Address();
		billingAddress.setCity("Johnstown");
		billingAddress.setCountryCode("US");
		billingAddress.setLine1("52 N Main ST");
		billingAddress.setPostalCode("43210");
		billingAddress.setState("OH");*/
		

		// ###CreditCard
		// A resource representing a credit card that can be
		// used to fund a payment.
		CreditCard creditCard = new CreditCard();
//		creditCard.setBillingAddress(billingAddress);
		creditCard.setCvv2("874");
		creditCard.setExpireMonth(11);
		creditCard.setExpireYear(2018);
		creditCard.setFirstName("Joe");
		creditCard.setLastName("Shopper");
		creditCard.setNumber("4417119669820331");
		creditCard.setType("visa");
		
		
		// ###FundingInstrument
		// A resource representing a Payeer's funding instrument.
		// Use a Payer ID (A unique identifier of the payer generated
		// and provided by the facilitator. This is required when
		// creating or using a tokenized funding instrument)
		// and the `CreditCardDetails`
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);
		
		// The Payment creation API requires a list of
		// FundingInstrument; add the created `FundingInstrument`
		// to a List
		List<FundingInstrument> fundingInstruments = new ArrayList<FundingInstrument>();
		fundingInstruments.add(fundingInstrument);
		
		// ###Payer
		// A resource representing a Payer that funds a payment
		// Use the List of `FundingInstrument` and the Payment Method
		// as 'credit_card'
		Payer payer = new Payer();
		// payer.setFundingInstruments(fundingInstruments);
		// payer.setPaymentMethod("credit_card");
		payer.setPaymentMethod("PayPal");
		
		// ###Payment
		// A Payment Resource; create one using
		// the above types and intent as 'authorize'
		Payment payment = new Payment();
//		payment.setIntent("authorize");
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		
		RedirectUrls redirectUrls = new RedirectUrls();
		
		redirectUrls.setCancelUrl(PayPalConfig.getUrlBase() + "/actor/PayPal/capture.do");
		redirectUrls.setReturnUrl(PayPalConfig.getUrlBase() + "/actor/PayPal/capture.do");
		
		payment.setRedirectUrls(redirectUrls);
		
		
		Payment responsePayment = payment.create(content);
		
		
//		Authorization authorization = responsePayment.getTransactions().get(0)
//				.getRelatedResources().get(0).getAuthorization();
//		
//		String paypalValidationUrl = responsePayment.getLinks().get(1).getHref();
//		
//		result = new ModelAndView("redirect:" + paypalValidationUrl);

		return responsePayment;
	}
	
	public static Payment startTransaction(Transaction transaction, Payer payer) throws PayPalRESTException{
		APIContext content = new APIContext(PayPalConfig.getBusinessClientId(), PayPalConfig.getBusinessClientSecret(), "sandbox");
		
		// The Payment creation API requires a list of
		// Transaction; add the created `Transaction`
		// to a List
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
		// ###Payment
		// A Payment Resource; create one using
		// the above types and intent as 'authorize'
		Payment payment = new Payment();
//		payment.setIntent("authorize");
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		
		RedirectUrls redirectUrls = new RedirectUrls();
		
		redirectUrls.setCancelUrl(PayPalConfig.getUrlBase() + "/actor/PayPal/capture.do");
		redirectUrls.setReturnUrl(PayPalConfig.getUrlBase() + "/actor/PayPal/capture.do");
		
		payment.setRedirectUrls(redirectUrls);
		
		
		Payment responsePayment = payment.create(content);
		
		
//		Authorization authorization = responsePayment.getTransactions().get(0)
//				.getRelatedResources().get(0).getAuthorization();
//		
//		String paypalValidationUrl = responsePayment.getLinks().get(1).getHref();
//		
//		result = new ModelAndView("redirect:" + paypalValidationUrl);

		return responsePayment;
	}
	
	public static Payment endTransaction(String paymentId, String payerID) throws PayPalRESTException{
		APIContext content = new APIContext(PayPalConfig.getBusinessClientId(), PayPalConfig.getBusinessClientSecret(), "sandbox");
		
		
		Payment responsePayment = Payment.get(content, paymentId);
		
		
		System.out.println(responsePayment.getState());
		
		
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerID);
		
		Payment createdPayment = responsePayment.execute(content, paymentExecution);
		
		
//		Authorization authorization = responsePayment.getTransactions().get(0)
//				.getRelatedResources().get(0).getAuthorization();
//		
//		String paypalValidationUrl = responsePayment.getLinks().get(1).getHref();
//		
//		result = new ModelAndView("redirect:" + paypalValidationUrl);
		System.out.println(createdPayment.getState());

		return createdPayment;
	}
	
	// RESPUESTA RECIBIDA: ?paymentId=PAY-77N547492T712625HLDWQLQY&token=EC-6K945680DN941221Y&PayerID=C4TQ4ERTP3SSN
	// RESPUESTA SI CANCELACION: ?token=EC-1WC13362EL818763F
	
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
			String uniqueTrackingId)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, IOException, InterruptedException {
		Assert.isTrue(receiverEmail.length() > 0);
		Assert.isTrue(total > commission);

		RequestEnvelope env = new RequestEnvelope("en_US");
		PayRequest payRequest = new PayRequest();

		payRequest.setRequestEnvelope(env);

		List<Receiver> receiver = new ArrayList<Receiver>();
		Receiver rec = new Receiver();
		rec.setAmount(total);
		rec.setEmail(PayPalConfig.getBusinessEmail());
		rec.setPrimary(true);
		receiver.add(rec);

		Receiver rec2 = new Receiver();
		rec2.setAmount(total - commission);
		rec2.setEmail(receiverEmail);
		rec2.setPrimary(false);
		receiver.add(rec2);

		ReceiverList receiverlst = new ReceiverList(receiver);

		payRequest.setReceiverList(receiverlst);
		// payRequest.setActionType("CREATE");
		payRequest.setActionType("PAY_PRIMARY");
		payRequest.setCurrencyCode("EUR");

		payRequest.setFeesPayer("PRIMARYRECEIVER");

		// responseEnvelope.timestamp=2017-04-19T04%3A00%3A27.676-07%3A00&responseEnvelope.ack=Success&responseEnvelope.correlationId=de635edcbc526&responseEnvelope.build=32250686&payKey=AP-8WD959703H6814522&paymentExecStatus=CREATED

		// Comprobar que el trackingId sea único
		payRequest.setTrackingId(uniqueTrackingId);

		payRequest.setCancelUrl(PayPalConfig.getUrlBase() + "/actor/PayPal/capture.do" + "?trackingId=" + payRequest.getTrackingId());
		payRequest.setReturnUrl(PayPalConfig.getUrlBase() + "/actor/PayPal/capture.do" + "?trackingId=" + payRequest.getTrackingId());

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
	public static RefundResponse refundAdaptativeTransaction(String trackingID, Receiver rec1, Receiver rec2)
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
		receiverList.add(rec2);
		ReceiverList receiverlst = new ReceiverList(receiverList);
		req.setReceiverList(receiverlst);

		AdaptivePaymentsService adaptivePaymentsService = new AdaptivePaymentsService(PayPalConfig.getConfigurationMap());

		RefundResponse refundResponse = adaptivePaymentsService.refund(req);

		return refundResponse;
	}

	
	
	
	public static Payer generatePayer(){
		// ###Payer
		// A resource representing a Payer that funds a payment
		// Use the List of `FundingInstrument` and the Payment Method
		// as 'credit_card'
		Payer payer = new Payer();
		// payer.setFundingInstruments(fundingInstruments);
		// payer.setPaymentMethod("credit_card");
		payer.setPaymentMethod("PayPal");
				
		return payer;
	}
	
	public static Transaction generateTransaction(){
		// ###Details
		// Let's you specify details of a payment amount.
		Details details = new Details();
		details.setShipping("0.03");
		details.setSubtotal("-107.41");
		details.setTax("0.03");
		
		// ###Amount
		// Let's you specify a payment amount.
		Amount amount = new Amount();
		amount.setCurrency("EUR");
		amount.setTotal("107.47");
		amount.setDetails(details);
		
		// ###Transaction
		// A transaction defines the contract of a
		// payment - what is the payment for and who
		// is fulfilling it. Transaction is created with
		// a `Payee` and `Amount` types
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
				.setDescription("This is the payment transaction description.");
		
		return transaction;
	}
	
	
	
	public static String requestPemissions(){
		// Initialize apiContext with proper credentials and environment.
		APIContext content = new APIContext(PayPalConfig.getBusinessClientId(), PayPalConfig.getBusinessClientSecret(), PayPalConfig.getMode());
		
		Map<String, String> configurationMap = content.getConfigurationMap();
		configurationMap.put("clientId", PayPalConfig.getBusinessClientId());
		configurationMap.put("clientSecret", PayPalConfig.getBusinessClientSecret());

		content.setConfigurationMap(configurationMap);


		ClientCredentials clientCredentials = new ClientCredentials();
		clientCredentials.setClientID(PayPalConfig.getBusinessClientId());
		clientCredentials.setClientSecret(PayPalConfig.getBusinessClientSecret());
		

		List<String> scopes = new ArrayList<String>() {{
		    /**
		    * 'openid'
		    * 'profile'
		    * 'address'
		    * 'email'
		    * 'phone'
		    * 'https://uri.paypal.com/services/paypalattributes'
		    * 'https://uri.paypal.com/services/expresscheckout'
		    * 'https://uri.paypal.com/services/invoicing'
		    */
		    add("openid");
		    add("profile");
		    add("email");
		}};
		String redirectUrl = Session.getRedirectURL(PayPalConfig.getUrlBase() + "/actor/PayPal/capture.do", scopes, content);
		return redirectUrl;
	}
	
	public static String obtainRefreshToken(String code) throws PayPalRESTException{
		// Initialize apiContext with proper credentials and environment.
		APIContext context = new APIContext(PayPalConfig.getBusinessClientId(), PayPalConfig.getBusinessClientSecret(), PayPalConfig.getMode());

		// Replace the code with the code value returned from the redirect on previous step.
		Tokeninfo info = Tokeninfo.createFromAuthorizationCode(context, code);
		String accessToken = info.getAccessToken();
		String refreshToken = info.getRefreshToken();
		
		return refreshToken;
	}
	

}
