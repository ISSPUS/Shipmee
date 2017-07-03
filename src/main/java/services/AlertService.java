package services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Alert;
import repositories.AlertRepository;
import utilities.PayPalConfig;

@Service
@Transactional
public class AlertService {
	
	static Logger log = Logger.getLogger(AlertService.class);

	// Managed repository -----------------------------------------------------

	@Autowired
	private AlertRepository alertRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private MessageSource messageSource;
	
	// Constructors -----------------------------------------------------------

	public AlertService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Alert create() {
		Alert result;
		
		result = new Alert();
		result.setUser(userService.findByPrincipal());
		
		return result;
	}
	
	public Alert save(Alert alert) {
		Assert.notNull(alert, "message.error.alert.notNull");
		Assert.isTrue(alert.getUser().equals(userService.findByPrincipal()), "message.error.alert.currentUser");
		Assert.isTrue(alert.getType().equals("Route") || alert.getType().equals("Shipment"), "message.error.alert.wrongType");
		Assert.isTrue(alert.getDate().after(new Date()), "message.error.alert.past");
		
		alert = alertRepository.save(alert);
			
		return alert;
	}
	
	public void delete(Alert alert) {
		Assert.notNull(alert, "message.error.alert.notNull");
		Assert.isTrue(alert.getId() != 0, "message.error.alert.mustExist");
		Assert.isTrue(alert.getUser().equals(userService.findByPrincipal()), "message.error.alert.currentUser");
						
		alertRepository.delete(alert);
	}
	
	public Alert findOne(int alertId) {
		Alert result;
		
		result = alertRepository.findOne(alertId);
		Assert.isTrue(result.getUser().equals(userService.findByPrincipal()), "message.error.alert.currentUser");
		
		
		return result;
	}

	// Other business methods -------------------------------------------------
	
	/***
	 * 
	 * @param origin Origin of the Alert
	 * @param destination Destination of the Alert
	 * @param date Date of the Alert
	 * @param type Type of Alert, can be "Route" or "Shipment"
	 * @return The collection of Alerts founded
	 */
	public Collection<Alert> checkAlerts(String origin, String destination, Date date, String type){
		Collection<Alert> alerts;
		
		alerts = alertRepository.checkAlerts(origin, destination, date, type);
		log.trace(alerts);
		sendAlerts(alerts);
		
		return alerts;
	}
	
	public void sendAlerts(Collection<Alert> alerts){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		String origin = "";
		String destination = "";
		Locale locale;
				
		for(Alert alert: alerts){
			
			try{
				origin = URLEncoder.encode(alert.getOrigin(), "ISO-8859-1");
				destination = URLEncoder.encode(alert.getDestination(), "ISO-8859-1");
			}catch (UnsupportedEncodingException e) {
				log.error("Error al codificar la URL",e);
			}
			locale = new Locale(alert.getUser().getLocalePreferences());
			
			if(alert.getType().equals("Route")){
				String url;
				
				url = PayPalConfig.getUrlBase()+"/route/search.do?origin="+origin+"&destination="+destination+"&date="+dateFormat.format(alert.getDate());

				// https://stackoverflow.com/a/2764993

				String[] args_body = { alert.getOrigin(), alert.getDestination(), dateFormat.format(alert.getDate()), url};
				
				messageService.sendMessage(actorService.findByUsername("shipmee"), alert.getUser(),
						messageSource.getMessage("alert.toSend.route.subject", null, locale), 
						messageSource.getMessage("alert.toSend.route.body", args_body, locale));
			}else{
				String url;
				
				url = PayPalConfig.getUrlBase()+"/shipment/search.do?origin="+origin+"&destination="+destination+"&date="+dateFormat.format(alert.getDate());

				// https://stackoverflow.com/a/2764993

				String[] args_body = { alert.getOrigin(), alert.getDestination(), dateFormat.format(alert.getDate()), url};
				
				messageService.sendMessage( actorService.findByUsername("shipmee"), alert.getUser(),
						messageSource.getMessage("alert.toSend.shipment.subject", null, locale), 
						messageSource.getMessage("alert.toSend.shipment.body", args_body, locale));
			}
		}
	}
	
	public Page<Alert> getAlertsByPrincipal(Pageable page){
		Page<Alert> result;
		
		result = alertRepository.getAlertsOfUser(userService.findByPrincipal().getId(),page);
		
		return result;
	}
	
}
