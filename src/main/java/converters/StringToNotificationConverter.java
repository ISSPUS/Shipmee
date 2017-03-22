package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.NotificationRepository;
import domain.Notification;

@Component
@Transactional
public class StringToNotificationConverter implements Converter<String, Notification> {

	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public Notification convert(String text) {
		Notification result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = notificationRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
