package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Shipment;

@Component
@Transactional
public class ShipmentToStringConverter implements Converter<Shipment, String> {
	
	@Override
	public String convert(Shipment shipment) {
		String result;

		if (shipment == null)
			result = null;
		else
			result = String.valueOf(shipment.getId());

		return result;
	}

}