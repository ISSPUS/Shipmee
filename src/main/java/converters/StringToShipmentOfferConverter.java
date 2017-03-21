package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ShipmentOfferRepository;
import domain.ShipmentOffer;

@Component
@Transactional
public class StringToShipmentOfferConverter implements Converter<String, ShipmentOffer> {

	@Autowired
	ShipmentOfferRepository shipmentOfferRepository;

	@Override
	public ShipmentOffer convert(String text) {
		ShipmentOffer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = shipmentOfferRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
