package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Notification;
import repositories.NotificationRepository;

@Service
@Transactional
public class NotificationService {
	
	static Logger log = Logger.getLogger(NotificationService.class);

	// Managed repository -----------------------------------------------------

	@Autowired
	private NotificationRepository notificationRepository;

	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------

	public NotificationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Notification create() {
		Notification result;
		
		result = new Notification();
		
		return result;
	}
	
	public Notification save(Notification notification) {
		Assert.notNull(notification);
		
		notification = notificationRepository.save(notification);
			
		return notification;
	}
	
	public void delete(Notification notification) {
		Assert.notNull(notification);
		Assert.isTrue(notification.getId() != 0);
						
		notificationRepository.delete(notification);
	}
	
	public Notification findOne(int notificationId) {
		Notification result;
		
		result = notificationRepository.findOne(notificationId);
		
		return result;
	}
	
	public Collection<Notification> findAll() {
		Collection<Notification> result;

		result = notificationRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------
	
	public Collection<Notification> checkNotifications(String origin, String destination, Date date, String type){
		Collection<Notification> result;
		
		result = new HashSet<Notification>();
		
		return result;
	}
	
}
