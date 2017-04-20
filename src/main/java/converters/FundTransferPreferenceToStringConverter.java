package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FundTransferPreference;

@Component
@Transactional
public class FundTransferPreferenceToStringConverter implements Converter<FundTransferPreference, String> {
	
	@Override
	public String convert(FundTransferPreference fundTransferPreference) {
		String result;

		if (fundTransferPreference == null)
			result = null;
		else
			result = fundTransferPreference.getIBAN();

		return result;
	}

}