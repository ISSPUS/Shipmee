package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RouteOffer;

@Component
@Transactional
public class RouteOfferToStringConverter implements Converter<RouteOffer, String> {
	
	@Override
	public String convert(RouteOffer routeOffer) {
		String result;

		if (routeOffer == null)
			result = null;
		else
			result = String.valueOf(routeOffer.getId());

		return result;
	}

}