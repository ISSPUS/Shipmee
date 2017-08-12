package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import domain.CreditCard;
import domain.FeePayment;
import domain.PayPalObject;
import domain.RouteOffer;
import domain.ShipmentOffer;
import domain.User;
import repositories.FeePaymentRepository;

@Service
@Transactional
public class FeePaymentService {
	
	static Logger log = Logger.getLogger(FeePaymentService.class);


	// Managed repository -----------------------------------------------------

	@Autowired
	private FeePaymentRepository feePaymentRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private RouteOfferService routeOfferService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	@Autowired
	private PayPalService payPalService;
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private MessageService messageService;
	
	// Constructors -----------------------------------------------------------

	public FeePaymentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FeePayment create() {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can create a feepayment");
		
		FeePayment result;
		User user;
		
		result = new FeePayment();
		user = userService.findByPrincipal();
		
		result.setPurchaser(user);
		result.setPaymentMoment(new Date());
		result.setType("Pending");
		result.setIsPayed(false);
		
		return result;
	}
	
	public FeePayment save(FeePayment feePayment) {
		Assert.notNull(feePayment);
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can save a feepayment");
		
		User user;
		FeePayment feePaymentPreSave;
		PayPalObject po;
		
		user = userService.findByPrincipal();		
		Assert.isTrue(user.getId() == feePayment.getPurchaser().getId());
		
		po = payPalService.findByFeePaymentId(feePayment.getId());

		if(feePayment.getId() == 0) {
			if(feePayment.getCreditCard() != null) {
				Assert.isTrue(compruebaFecha(feePayment.getCreditCard()), "Credit card cannot be expired");
			}
			
			feePayment.setPurchaser(user);
			feePayment.setPaymentMoment(new Date());
			
			feePayment.setCommission(Math.round((feePayment.getAmount()/15) * 100) / 100.0);
			
			feePayment = feePaymentRepository.save(feePayment);
			
			if(feePayment.getShipmentOffer() != null) {
				messageService.autoMessageAcceptShipmentOffer(feePayment.getShipmentOffer());
			}
		} else {
			
			Assert.isTrue(po != null ^ feePayment.getCreditCard() != null, "FeePaymentService.save.error.OrCreditCardOrPayPal");
			
			feePaymentPreSave = this.findOne(feePayment.getId());
			feePaymentPreSave.setType(feePayment.getType());
			
			feePayment = feePaymentRepository.save(feePaymentPreSave);
		}
		
		feePayment.setIsPayed((po != null && po.getPayStatus().equals("COMPLETED")) || feePayment.getCreditCard() != null);

		return feePayment;
	}
	
	public FeePayment manageFeePayment(int feepaymentId, String type) {
		FeePayment feePayment;
		FeePayment res = null;
		PayPalObject payPal;

		feePayment = this.findOne(feepaymentId);
		feePayment.setType(type);

		try {
			payPal = payPalService.findByFeePaymentId(feepaymentId);
			if (payPal != null) {
				payPalService.payToShipper(feepaymentId);
			}

			res = this.save(feePayment);
		} catch (SSLConfigurationException | InvalidCredentialException | HttpErrorException
				| InvalidResponseDataException | ClientActionRequiredException | MissingCredentialException
				| OAuthException | PayPalRESTException | IOException | InterruptedException e) {
			log.error(e,e);
		}

		return res;
	}

	
	public FeePayment findOne(int feePaymentId) {
		FeePayment result;
		
		result = feePaymentRepository.findOne(feePaymentId);
		
		return result;
	}
	
	public Collection<FeePayment> findAll() {
		Collection<FeePayment> result;

		result = feePaymentRepository.findAll();

		return result;
	}

	public Page<FeePayment> findAllPendingByUser(Pageable pageable) {
		Page<FeePayment> result;
		List<FeePayment> allFeePaymentsPending;
		User user;
		
		user = userService.findByPrincipal();
		allFeePaymentsPending = new ArrayList<FeePayment>();

		allFeePaymentsPending.addAll(feePaymentRepository.findAllPendingRouteOffersByUser(user.getId()));
		allFeePaymentsPending.addAll(feePaymentRepository.findAllPendingShipmentOffersByUser(user.getId()));
		
		result = new PageImpl<FeePayment>(allFeePaymentsPending, pageable, allFeePaymentsPending.size());
		Assert.notNull(result);
		
		return result;
	}
	
	public Page<FeePayment> findAllAcceptedByUser(Pageable pageable) {
		Page<FeePayment> result;
		List<FeePayment> allFeePaymentsAccepted;
		User user;
		
		user = userService.findByPrincipal();
		allFeePaymentsAccepted = new ArrayList<FeePayment>();

		allFeePaymentsAccepted.addAll(feePaymentRepository.findAllAcceptedRouteOffersByUser(user.getId()));
		allFeePaymentsAccepted.addAll(feePaymentRepository.findAllAcceptedShipmentOffersByUser(user.getId()));
		
		result = new PageImpl<FeePayment>(allFeePaymentsAccepted, pageable, allFeePaymentsAccepted.size());
		Assert.notNull(result);
		
		return result;
	}
	
	public Page<FeePayment> findAllRejectedByUser(Pageable pageable) {
		Page<FeePayment> result;
		List<FeePayment> allFeePaymentsRejected;
		User user;
		
		user = userService.findByPrincipal();
		allFeePaymentsRejected = new ArrayList<FeePayment>();

		allFeePaymentsRejected.addAll(feePaymentRepository.findAllRejectedRouteOffersByUser(user.getId()));
		allFeePaymentsRejected.addAll(feePaymentRepository.findAllRejectedShipmentOffersByUser(user.getId()));
		
		result = new PageImpl<FeePayment>(allFeePaymentsRejected, pageable, allFeePaymentsRejected.size());
		Assert.notNull(result);
		
		return result;
	}
	
	public void cancelPaymentInProgress(int feePaymentId){
		FeePayment feePayment = this.findOne(feePaymentId);
		PayPalObject po = payPalService.findByFeePaymentId(feePaymentId);
		int routeOfferId = -1;
		
		Assert.notNull(feePayment); //FeePayment not found
		
		Assert.isTrue(!feePayment.getIsPayed(), "message.error.feePayment.notPayed"); // Only permitted cancel not Payed
		Assert.notNull(po); // Only permitted cancel with PayPal
		Assert.isTrue(feePayment.getPurchaser().equals(userService.findByPrincipal())); // Only permitted cancel by purchaser
		
		if (feePayment.getRouteOffer() != null)
			routeOfferId = feePayment.getRouteOffer().getId();
		
		
		payPalService.delete(po.getId());
		feePaymentRepository.delete(feePaymentId);
		
		if (routeOfferId > 0)
			routeOfferService.delete(routeOfferId);
	}

	// Other business methods -------------------------------------------------
	
	
	public FeePayment createAndSave(int type, int id, int sizePriceId, double amount, String description, int shipmentId){
		FeePayment res;
		RouteOffer ro;
		ShipmentOffer so;

		
		res = this.create();
		
		/**
		 * Type == 1 -> Contract a route
		 * Type == 2 -> Create a routeOffer
		 * Type == 3 -> Accept a shipmentOffer
		 */
		switch (type) {
		case 1:
			ro = routeService.contractRoute(id, sizePriceId);
			res.setRouteOffer(ro);
			
			res.setAmount(ro.getAmount());
			res.setCarrier(ro.getRoute().getCreator());
			
			res.setPurchaser(ro.getUser());
			break;
			
		case 2:
			ro = routeOfferService.create(id, shipmentId);
			ro.setAmount(amount);
			ro.setDescription(description);
			
			ro = routeOfferService.save(ro);
			
			res.setRouteOffer(ro);
			res.setAmount(ro.getAmount());
			res.setCarrier(ro.getRoute().getCreator());
			res.setPurchaser(ro.getUser());
			break;
			
		case 3:
			so = shipmentOfferService.findOne(id);
			
			res.setShipmentOffer(so);
			res.setAmount(so.getAmount());
			res.setCarrier(so.getUser());
			res.setPurchaser(so.getShipment().getCreator());
			break;

		default:
			break;
		}
		
		res = this.save(res);
		
		return res;
	}
	
	public FeePayment returnPayed(String trackingId){
		FeePayment res;
		
		res = payPalService.findByTrackingId(trackingId).getFeePayment();
		
		if (res.getShipmentOffer() != null){
			res.setType("Accepted");
		} else {
			res.setType("Pending");
		}
		
		return res;
	}
	
	
	public Page<FeePayment> findAllRejected(Pageable page) {
		Page<FeePayment> result;

		result = feePaymentRepository.findAllRejected(page);
		Assert.notNull(result);
		return result;
	}
	
	public Page<FeePayment> findAllPending(Pageable page) {
		Page<FeePayment> result;
		List<FeePayment> allFeePaymentsPending;
		
		allFeePaymentsPending = new ArrayList<FeePayment>();

		allFeePaymentsPending.addAll(feePaymentRepository.findAllPendingRouteOffers());
		allFeePaymentsPending.addAll(feePaymentRepository.findAllPendingShipmentOffers());
		
		result = new PageImpl<FeePayment>(allFeePaymentsPending, page, allFeePaymentsPending.size());
		Assert.notNull(result);
		
		return result;
	}
	
	public Page<FeePayment> findAllAccepted(Pageable page) {
		Page<FeePayment> result;

		result = feePaymentRepository.findAllAccepted(page);
		Assert.notNull(result);
		return result;
	}

	private boolean compruebaFecha(CreditCard creditCard) {
		boolean result;
		Calendar c;
		int cMonth, cYear;
		
		result = false;
		
		c = Calendar.getInstance();
				
		cMonth = c.get(2) + 1; //Obtenemos numero del mes (Enero es 0)
		cYear = c.get(1); //Obtenemos año
		
		if(creditCard.getExpirationYear() > cYear) {
			result = true;
		} else if(creditCard.getExpirationYear() == cYear) {
			if(creditCard.getExpirationMonth() >= cMonth) {
				result = true;
			}
		}
		return result;		
	}
}


