package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SizePrice;

@Component
@Transactional
public class SizePriceToStringConverter implements Converter<SizePrice, String> {
	
	@Override
	public String convert(SizePrice sizePrice) {
		String result;

		if (sizePrice == null)
			result = null;
		else
			result = String.valueOf(sizePrice.getId());

		return result;
	}

}