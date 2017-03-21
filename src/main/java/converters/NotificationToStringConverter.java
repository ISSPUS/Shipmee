package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Notification;

@Component
@Transactional
public class NotificationToStringConverter implements Converter<Notification, String> {
	
	@Override
	public String convert(Notification notification) {
		String result;

		if (notification == null)
			result = null;
		else
			result = String.valueOf(notification.getId());

		return result;
	}

}