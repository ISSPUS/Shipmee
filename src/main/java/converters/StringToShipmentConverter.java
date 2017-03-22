package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ShipmentRepository;
import domain.Shipment;

@Component
@Transactional
public class StringToShipmentConverter implements Converter<String, Shipment> {

	@Autowired
	ShipmentRepository shipmentRepository;

	@Override
	public Shipment convert(String text) {
		Shipment result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = shipmentRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
