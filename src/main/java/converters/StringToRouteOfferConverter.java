package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RouteOfferRepository;
import domain.RouteOffer;

@Component
@Transactional
public class StringToRouteOfferConverter implements Converter<String, RouteOffer> {

	@Autowired
	RouteOfferRepository routeOfferRepository;

	@Override
	public RouteOffer convert(String text) {
		RouteOffer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = routeOfferRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
