package services.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Shipment;
import domain.User;
import domain.form.ShipmentForm;
import services.ShipmentService;
import services.UserService;

@Service
@Transactional
public class ShipmentFormService {

	// Supporting services ----------------------------------------------------

	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public ShipmentFormService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ShipmentForm create() {
		ShipmentForm result;
		
		result = new ShipmentForm();
		
		result.setShipmentId(0);
		
		return result;
	}
	
	public Shipment reconstruct(ShipmentForm shipmentForm) {
		Shipment result;
		Date departureTime, maximumArriveTime;
		
		departureTime = null;
		maximumArriveTime = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		try {
			departureTime = formatter.parse(shipmentForm.getDepartureTime());
			maximumArriveTime = formatter.parse(shipmentForm.getMaximumArriveTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (shipmentForm.getShipmentId() == 0) {
			result = shipmentService.create();
			
			result.setOrigin(shipmentForm.getOrigin());
			result.setDestination(shipmentForm.getDestination());
			result.setItemEnvelope(shipmentForm.getItemEnvelope());
			result.setPrice(shipmentForm.getPrice());
			result.setItemName(shipmentForm.getItemName());
			result.setItemPicture(shipmentForm.getItemPicture());
			result.setItemSize(shipmentForm.getItemSize());
			result.setMaximumArriveTime(maximumArriveTime);
			result.setDepartureTime(departureTime);			
		} else if(shipmentForm.getShipmentId() != 0) {			
			result = shipmentService.findOne(shipmentForm.getShipmentId());
			result.setOrigin(shipmentForm.getOrigin());
			result.setDestination(shipmentForm.getDestination());
			result.setItemEnvelope(shipmentForm.getItemEnvelope());
			result.setPrice(shipmentForm.getPrice());
			result.setItemName(shipmentForm.getItemName());
			result.setItemPicture(shipmentForm.getItemPicture());
			result.setItemSize(shipmentForm.getItemSize());
			result.setMaximumArriveTime(maximumArriveTime);
			result.setDepartureTime(departureTime);
		} else {
			result = null;
		}
		
		return result;
	}

	public ShipmentForm findOne(int shipmentId) {
		ShipmentForm result;
		Shipment shipment;
		String maximumArriveTime, departureTime;
		User user;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		result = this.create();
		shipment = shipmentService.findOne(shipmentId);
		user = userService.findByPrincipal();
		
		Assert.isTrue(user.getId() == shipment.getCreator().getId());
		
		maximumArriveTime = formatter.format(shipment.getMaximumArriveTime());
		departureTime = formatter.format(shipment.getDepartureTime());
		
		result.setOrigin(shipment.getOrigin());
		result.setDestination(shipment.getDestination());
		result.setItemEnvelope(shipment.getItemEnvelope());
		result.setPrice(shipment.getPrice());
		result.setItemName(shipment.getItemName());
		result.setItemPicture(shipment.getItemPicture());
		result.setItemSize(shipment.getItemSize());
		result.setMaximumArriveTime(maximumArriveTime);
		result.setDepartureTime(departureTime);
		
		return result;
	}

	public void delete(ShipmentForm shipmentForm) {
		Shipment shipment;
		shipment = shipmentService.findOne(shipmentForm.getShipmentId());
		shipmentService.delete(shipment);
	}
}
