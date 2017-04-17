package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FundTransferPreference;

@Component
@Transactional
public class StringToFundTransferPreferenceConverter implements Converter<String, FundTransferPreference> {

	@Override
	public FundTransferPreference convert(String text) {
		FundTransferPreference result;

		try {
			result = new FundTransferPreference();
			result.setIBAN(text);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
