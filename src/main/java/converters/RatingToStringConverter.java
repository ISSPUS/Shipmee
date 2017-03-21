package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Rating;

@Component
@Transactional
public class RatingToStringConverter implements Converter<Rating, String> {
	
	@Override
	public String convert(Rating rating) {
		String result;

		if (rating == null)
			result = null;
		else
			result = String.valueOf(rating.getId());

		return result;
	}

}