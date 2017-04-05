package converters; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Message;
import repositories.MessageRepository;

@Component
@Transactional
public class StringToMessageConverter implements Converter<String, Message> {

	@Autowired
	MessageRepository messageRepository;

	@Override
	public Message convert(String text) {
		Message result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = messageRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
