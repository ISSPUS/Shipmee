package services.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import domain.FeePayment;
import domain.RouteOffer;
import domain.ShipmentOffer;
import domain.form.FeePaymentForm;
import services.FeePaymentService;
import services.RouteOfferService;
import services.RouteService;
import services.ShipmentOfferService;

@Service
@Transactional
public class FeePaymentFormService {

	// Supporting services ----------------------------------------------------

	@Autowired
	private FeePaymentService feePaymentService;
	
	@Autowired
	private RouteOfferService routeOfferService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	@Autowired
	private RouteService routeService;
	
	// Constructors -----------------------------------------------------------

	public FeePaymentFormService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------


	public FeePaymentForm create(int type, int id, int sizePriceId, double amount, String description, int shipmentId) {
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
			result.setAmount(0.1); // Posteriormente se reseteará al valor oportuno
			break;
			
		case 2:
			result.setId(id);
			result.setAmount(amount);
			result.setDescription(description);
			result.setShipmentId(shipmentId);
			break;
			
		case 3:
			ShipmentOffer shipmentOffer;
			
			shipmentOffer = shipmentOfferService.findOne(id);
			
			result.setId(shipmentOffer.getShipment().getId());
			result.setOfferId(id);
			result.setAmount(0.1);
			break;

		default:
			break;
		}
		
		
		return result;
	}
	
	public FeePayment reconstruct(FeePaymentForm feePaymentForm) {
		FeePayment result;
		RouteOffer routeOffer;
		
		result = feePaymentService.create();
		
		switch (feePaymentForm.getType()) {
		case 1:
			routeOffer = routeService.contractRoute(feePaymentForm.getId(), feePaymentForm.getSizePriceId());
			feePaymentForm.setOfferId(routeOffer.getId());
			
			result.setRouteOffer(routeOffer);
			result.setAmount(routeOffer.getAmount());
			result.setCreditCard(feePaymentForm.getCreditCard());
			result.setCarrier(routeOffer.getRoute().getCreator());
			break;
			
		case 2:
			routeOffer = routeOfferService.create(feePaymentForm.getId(), feePaymentForm.getShipmentId());
			routeOffer.setAmount(feePaymentForm.getAmount());
			routeOffer.setDescription(feePaymentForm.getDescription());
			routeOffer = routeOfferService.save(routeOffer);
			
			feePaymentForm.setOfferId(routeOffer.getId());
			
			result.setRouteOffer(routeOffer);
			result.setAmount(routeOffer.getAmount());
			result.setCreditCard(feePaymentForm.getCreditCard());
			result.setCarrier(routeOffer.getRoute().getCreator());
			break;
			
		case 3:
			ShipmentOffer shipmentOffer;

			shipmentOffer = shipmentOfferService.accept(feePaymentForm.getOfferId());
			
			feePaymentForm.setOfferId(shipmentOffer.getId());
			
			result.setShipmentOffer(shipmentOffer);
			result.setAmount(shipmentOffer.getAmount());
			result.setCreditCard(feePaymentForm.getCreditCard());
			result.setCarrier(shipmentOffer.getUser());
			break;

		default:
			break;
		}
		
		return result;
	}

}
