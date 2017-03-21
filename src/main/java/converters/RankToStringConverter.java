package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Rank;

@Component
@Transactional
public class RankToStringConverter implements Converter<Rank, String> {
	
	@Override
	public String convert(Rank rank) {
		String result;

		if (rank == null)
			result = null;
		else
			result = String.valueOf(rank.getId());

		return result;
	}

}