package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SizePriceRepository;
import domain.SizePrice;

@Component
@Transactional
public class StringToSizePriceConverter implements Converter<String, SizePrice> {

	@Autowired
	SizePriceRepository sizePriceRepository;

	@Override
	public SizePrice convert(String text) {
		SizePrice result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = sizePriceRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
