package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ShipmentOffer;

@Component
@Transactional
public class ShipmentOfferToStringConverter implements Converter<ShipmentOffer, String> {
	
	@Override
	public String convert(ShipmentOffer shipmentOffer) {
		String result;

		if (shipmentOffer == null)
			result = null;
		else
			result = String.valueOf(shipmentOffer.getId());

		return result;
	}

}