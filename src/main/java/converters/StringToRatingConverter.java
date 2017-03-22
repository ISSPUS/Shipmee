package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RatingRepository;
import domain.Rating;

@Component
@Transactional
public class StringToRatingConverter implements Converter<String, Rating> {

	@Autowired
	RatingRepository ratingRepository;

	@Override
	public Rating convert(String text) {
		Rating result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = ratingRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
