package services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
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
import com.paypal.svcs.types.ap.Receiver;
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
	
	@Autowired
	private UserService userService;
	

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
		
		payPalObject = payPalRepository.save(payPalObject); // No añadir flush puesto que falla todo
			
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
		String carrierPayPalEmail;
		
		fp = feePaymentService.findOne(feePaymentId);
		
		Assert.notNull(fp, "PayPalService.authorizePay.error.FeePaymentNotFound");
		
		PayPalObject payObject = this.create();
		payObject.setFeePayment(fp);;

//		payObject = this.save(payObject);	// Comentar para evitar tantas escrituras a la DB
		
// 		Assert.isTrue(!fp.getCarrier().getFundTransferPreference().getPaypalEmail().equals(""), "PayPalService.authorizePay.error.CarrierWithoutPayPalEmail");
		try{
			carrierPayPalEmail = fp.getCarrier().getFundTransferPreference().getPaypalEmail();
		} catch (NullPointerException e) {
			carrierPayPalEmail = "";
		}

		try {
			res = PayPal.startAdaptiveTransaction(
					carrierPayPalEmail, fp.getAmount(),
					fp.getCommission(),
					payObject.getTrackingId(),
					"user/payPal/returnPayment.do");
		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | IOException | InterruptedException e) {
			log.error(e.getStackTrace(), e);
			return null;
		}
		
		Assert.isTrue(res.getError().isEmpty(), "PayPalService.authorizePay.error.startTransaction");
		payObject.setPayStatus(res.getPaymentExecStatus());
		
		payObject = this.save(payObject);
		// payObject = payPalRepository.saveAndFlush(payObject);

		return res;
	}
	
	public PayPalObject refreshPaymentStatusFromPaypal(String trackingId)
//	public PaymentDetailsResponse refreshPaymentStatusFromPaypal(String trackingId, PayPalObject response)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, PayPalRESTException, IOException, InterruptedException {

		PayPalObject payObject = this.findByTrackingId(trackingId);

		PaymentDetailsResponse details = PayPal.fetchDetailsAdaptiveTransaction(payObject.getTrackingId());

		Assert.isTrue(details.getError().isEmpty(), "PayPalService.returnFromPaypal.error.RetrievingDetailsFromPaypal");
		
		// payObject = this.findByTrackingId(trackingId);

		payObject.setPayStatus(details.getStatus());

		// this.save(payObject);
		payObject = payPalRepository.saveAndFlush(payObject);
		
//		return details;
		return payObject;
	}
	
	public void payToShipper(int feePaymentID)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, PayPalRESTException, IOException, InterruptedException {
		ExecutePaymentResponse res;
		
		PayPalObject po = this.findByFeePaymentId(feePaymentID);

//		PaymentDetailsResponse payObject = this.refreshPaymentStatusFromPaypal(po.getTrackingId(), po);
//
//		if (payObject.getStatus().equals("INCOMPLETE")) {
//			res = PayPal.adaptiveSendToSenconds(payObject.getPayKey());
//
//			if (res.getError().size() != 0) {
//				log.error(res.getError().get(0).getMessage());
//
//				Assert.isTrue(res.getError().size() == 0, "PayPalService.payToShipper.error.payPalError");
//
//			}
//		}
	}
	
	public void refundToSender(int feePaymentID)
			throws SSLConfigurationException, InvalidCredentialException, UnsupportedEncodingException,
			HttpErrorException, InvalidResponseDataException, ClientActionRequiredException, MissingCredentialException,
			OAuthException, PayPalRESTException, IOException, InterruptedException {
		RefundResponse res;

		PayPalObject po = this.findByFeePaymentId(feePaymentID);

//		PaymentDetailsResponse payObject = this.refreshPaymentStatusFromPaypal(po.getTrackingId(), po);
//
//		// Actualmente no tenemos permsisos por parte de PayPal para devolver
//		// una transacción ya pagada al usuario final
//		// por lo que ese podría ser el error
//
//		Receiver rec1 = null;
//		Receiver rec2 = null;
//
//		if (!payObject.getPaymentInfoList().getPaymentInfo().isEmpty()) {
//			rec1 = payObject.getPaymentInfoList().getPaymentInfo().get(0).getReceiver();
//		}
//
//		if (payObject.getPaymentInfoList().getPaymentInfo().size() > 1) {
//			rec2 = payObject.getPaymentInfoList().getPaymentInfo().get(1).getReceiver();
//		}
//
//		res = PayPal.refundAdaptiveTransaction(po.getTrackingId(), rec1, rec2);
//
//		if (res.getError().size() != 0) {
//			log.error(res.getError().get(0).getMessage());
//
//			Assert.isTrue(res.getError().size() == 0, "PayPalService.refundToSender.error.payPalError");
//
//		}

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
	
	public Collection<PayPalObject> findByRouteId(int routeId) {
		Collection<PayPalObject> result;
		
		result = payPalRepository.findByRouteId(routeId);
		
		return result;
	}
	
	public Collection<PayPalObject> findByShipmentId(int shipmentId) {
		Collection<PayPalObject> result;
		
		result = payPalRepository.findByShipmentId(shipmentId);
		
		return result;
	}
	
	public String generateTrackingId() {
		String CARACTERES = PayPalConfig.CHARACTERS_TRACKING_ID;
		StringBuilder salt = new StringBuilder();
		String saltStr = "";
		while (true) {
			Random rnd = new Random();
			while (salt.length() < PayPalConfig.LENGTH_TRACKING_ID) {
				int index = rnd.nextInt(CARACTERES.length());
				salt.append(CARACTERES.charAt(index));
			}
			saltStr = salt.toString();
			if (payPalRepository.countPayPalObjectWithTrackingId(saltStr) == 0) {
				break;
			}
		}
		return saltStr;

	}
	
	public void delete(int payPalObjectId){
		
		PayPalObject po = this.findOne(payPalObjectId);
		
		Assert.notNull(po); //PayPalObject not found
		Assert.isTrue(po.getPayStatus().equals("CREATED"), ""); // Only permitted cancel with paypalStatus created
		Assert.isTrue(po.getFeePayment().getPurchaser().equals(userService.findByPrincipal())); // Only permitted cancel by purchaser


		payPalRepository.delete(payPalObjectId);
	}
}
