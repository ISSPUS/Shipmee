package services.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import domain.FeePayment;
import domain.RouteOffer;
import domain.form.FeePaymentForm;
import services.FeePaymentService;
import services.RouteOfferService;

@Service
@Transactional
public class FeePaymentFormService {

	// Supporting services ----------------------------------------------------

	@Autowired
	private FeePaymentService feePaymentService;
	
	@Autowired
	private RouteOfferService routeOfferService;
	
	// Constructors -----------------------------------------------------------

	public FeePaymentFormService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------


	public FeePaymentForm create(int type, int id, int sizePriceId, double amount, String description) {
		FeePaymentForm result;
		
		result = new FeePaymentForm();
		
		result.setType(type);
		
		/**
		 * Type == 1 -> Contract a route
		 * Type == 2 -> Create a routeOffer
		 * Type == 3 -> Accept a shipmentOffer
		 */
		switch (type) {
		case 1:
			result.setId(id);
			result.setSizePriceId(sizePriceId);
			break;

		default:
			break;
		}
		
		
		return result;
	}
	
	public FeePayment reconstruct(FeePaymentForm feePaymentForm) {
		FeePayment result;
		
		result = feePaymentService.create();
		
		switch (feePaymentForm.getType()) {
		case 1:
			RouteOffer routeOffer;
			
			routeOffer = routeOfferService.findOne(feePaymentForm.getOfferId());
			
			result.setRouteOffer(routeOffer);
			result.setAmount(routeOffer.getAmount());
			result.setCreditCard(feePaymentForm.getCreditCard());
			result.setCarrier(routeOffer.getRoute().getCreator());
			break;

		default:
			break;
		}
		
		return result;
	}

}
