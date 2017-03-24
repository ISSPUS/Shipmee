package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Alert;
import repositories.AlertRepository;

@Service
@Transactional
public class AlertService {
	
	static Logger log = Logger.getLogger(AlertService.class);

	// Managed repository -----------------------------------------------------

	@Autowired
	private AlertRepository alertRepository;

	// Supporting services ----------------------------------------------------
	
	// Constructors -----------------------------------------------------------

	public AlertService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Alert create() {
		Alert result;
		
		result = new Alert();
		
		return result;
	}
	
	public Alert save(Alert alert) {
		Assert.notNull(alert);
		
		alert = alertRepository.save(alert);
			
		return alert;
	}
	
	public void delete(Alert alert) {
		Assert.notNull(alert);
		Assert.isTrue(alert.getId() != 0);
						
		alertRepository.delete(alert);
	}
	
	public Alert findOne(int alertId) {
		Alert result;
		
		result = alertRepository.findOne(alertId);
		
		return result;
	}
	
	public Collection<Alert> findAll() {
		Collection<Alert> result;

		result = alertRepository.findAll();

		return result;
	}

	// Other business methods -------------------------------------------------
	
	public Collection<Alert> checkAlerts(String origin, String destination, Date date, String type){
		Collection<Alert> result;
		
		result = new HashSet<Alert>();
		
		return result;
	}
	
}
