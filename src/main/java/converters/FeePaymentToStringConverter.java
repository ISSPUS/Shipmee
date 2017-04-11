package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FeePayment;

@Component
@Transactional
public class FeePaymentToStringConverter implements Converter<FeePayment, String> {
	
	@Override
	public String convert(FeePayment feepayment) {
		String result;

		if (feepayment == null)
			result = null;
		else
			result = String.valueOf(feepayment.getId());

		return result;
	}

}