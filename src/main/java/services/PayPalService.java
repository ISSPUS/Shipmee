package services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
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

import domain.FeePayment;
import domain.PayPalObject;
import repositories.PayPalRepository;
import utilities.PayPal;
import utilities.PayPalConfig;

@Service
@Transactional
public class PayPalService {
	
	static Logger log = Logger.getLogger(PayPalService.class);

	// Managed repository -----------------------------------------------------

	@Autowired
	private PayPalRepository payPalRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private FeePaymentService feePaymentService;
	

	// Constructors -----------------------------------------------------------

	public PayPalService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	private PayPalObject create() {
		PayPalObject result;
		
		result = new PayPalObject();
		result.setTrackingId(this.generateTrackingId());
		result.setRefundStatus("UNKNOWN");
		result.setPayStatus("UNKNOWN");
		
		return result;
	}
	
	private PayPalObject save(PayPalObject payPalObject) {
		Assert.notNull(payPalObject, "message.error.PayPalObject.notNull");
		
		payPalObject = payPalRepository.save(payPalObject);
			
		return payPalObject;
	}
	
	public PayPalObject findOne(int payPalObjectId) {
		PayPalObject result;
		
		result = payPalRepository.findOne(payPalObjectId);
		
		return result;
	}

	// Other business methods -------------------------------------------------
	
	public PayResponse authorizePay(int feePaymentId) {
		
		FeePayment fp;
		PayResponse res;
		
		fp = feePaymentService.findOne(feePaymentId);
		
		Assert.notNull(fp, "PayPalService.authorizePay.error.FeePaymentNotFound");
		
		PayPalObject payObject = this.create();
		payObject.setFeePayment(fp);;

		payObject = this.save(payObject);	// Comentar para evitar tantas escrituras a la DB
		
		Assert.isTrue(!fp.getCarrier().getFundTransferPreference().getPaypalEmail().equals(""), "PayPalService.authorizePay.error.CarrierWithoutPayPalEmail");

		try {
			res = PayPal.startAdaptiveTransaction(
					fp.getCarrier().getFundTransferPreference().getPaypalEmail(), fp.getAmount() + fp.getCommission(),
					fp.getCommission(),
					payObject.getTrackingId(),
					"user/payPal/returnPayment.do");
		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | IOException | InterruptedException e) {
			log.error(e, e.getCause());
			return null;
		}
		
		Assert.isTrue(res.getError().isEmpty(), "PayPalService.authorizePay.error.startTransaction");
		payObject.setPayStatus(res.getPaymentExecStatus());
		
		this.save(payObject);

		return res;
	}
	
	public PaymentDetailsResponse refreshPaymentStatusFromPaypal(String trackingId)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, PayPalRESTException, IOException, InterruptedException {

		PayPalObject payObject = this.findByTrackingId(trackingId);

		PaymentDetailsResponse details = PayPal.fetchDetailsAdaptiveTransaction(payObject.getTrackingId());

		Assert.isTrue(details.getError().isEmpty(), "PayPalService.returnFromPaypal.error.RetrievingDetailsFromPaypal");

		payObject.setPayStatus(details.getStatus());

		this.save(payObject);
		
		return details;
	}
	
	public void payToShipper(int feePaymentID)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, PayPalRESTException, IOException, InterruptedException {
		ExecutePaymentResponse res;
		
		PayPalObject po = this.findByFeePaymentId(feePaymentID);

		PaymentDetailsResponse payObject = this.refreshPaymentStatusFromPaypal(po.getTrackingId());

		Assert.isTrue(payObject.getStatus().equals("INCOMPLETE"));

		res = PayPal.adaptiveSendToSenconds(payObject.getPayKey());

		if (res.getError().size() != 0){
			log.error(res.getError().get(0).getMessage());
			
			Assert.isTrue(res.getError().size() == 0,
					"PayPalService.payToShipper.error.payPalError");
			
		}
	}
	
	public void refundToSender(int feePaymentID)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, PayPalRESTException, IOException, InterruptedException {
		RefundResponse res;
		
		PayPalObject po = this.findByFeePaymentId(feePaymentID);

		PaymentDetailsResponse payObject = this.refreshPaymentStatusFromPaypal(po.getTrackingId());

		// Actualmente no tenemos permsisos por parte de PayPal para devolver una transacción ya pagada al usuario final
		//		por lo que ese podría ser el error
		Assert.isTrue(payObject.getStatus().equals("INCOMPLETE"), "PayPalService.refundToSender.error.NotIncomplete");

		res = PayPal.refundAdaptiveTransaction(po.getTrackingId(), 
				payObject.getPaymentInfoList().getPaymentInfo().get(0).getReceiver(), 
				payObject.getPaymentInfoList().getPaymentInfo().get(1).getReceiver());

		if (res.getError().size() != 0){
			log.error(res.getError().get(0).getMessage());
			
			Assert.isTrue(res.getError().size() == 0,
					"PayPalService.refundToSender.error.payPalError");
			
		}
	}

	
	public PayPalObject findByTrackingId(String trackingId) {
		PayPalObject result;
		
		result = payPalRepository.findByTrackingId(trackingId);
		
		return result;
	}
	
	public PayPalObject findByFeePaymentId(int feePaymentId) {
		PayPalObject result;
		
		result = payPalRepository.findByFeePayment(feePaymentId);
		
		return result;
	}
	
	public PayPalObject findByRouteOfferId(int routeOfferId) {
		PayPalObject result;
		
		result = payPalRepository.findByRouteOfferId(routeOfferId);
		
		return result;
	}
	
	public String generateTrackingId() {
		String CARACTERES = PayPalConfig.CHARACTERS_TRACKING_ID;
		StringBuilder salt = new StringBuilder();
		String saltStr = "";
		while (true) {
			Random rnd = new Random();
			while (salt.length() < PayPalConfig.LENGTH_TRACKING_ID) {
				int index = (int) (rnd.nextFloat() * CARACTERES.length());
				salt.append(CARACTERES.charAt(index));
			}
			saltStr = salt.toString();
			if (payPalRepository.countPayPalObjectWithTrackingId(saltStr) == 0) {
				break;
			}
		}
		return saltStr;

	}
}
