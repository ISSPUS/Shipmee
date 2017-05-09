package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;
	
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
		
		for(Alert alert: alerts){
			if(alert.getType().equals("Route")){
				messageService.sendMessage( actorService.findByUsername("shipmee"), alert.getUser(), "Nueva Alerta", 
						"Se ha creado una nueva ruta "+alert.getOrigin()+" -> "+alert.getDestination()+" el día "+dateFormat.format(alert.getDate())+"."+
						"Haz click en el enlace para ver la ruta: https://shipmee.es/route/search.do?origin="+alert.getOrigin()+"&destination="+alert.getDestination()+"&date="+dateFormat.format(alert.getDate()));
			}else{
				messageService.sendMessage(actorService.findByUsername("shipmee"), alert.getUser(), "Nueva Alerta", 
						"Se ha creado un nuevo envío "+alert.getOrigin()+" -> "+alert.getDestination()+" el día "+dateFormat.format(alert.getDate())+"."+
						"Haz click en el enlace para ver el envío: https://shipmee.es/shipment/search.do?origin="+alert.getOrigin()+"&destination="+alert.getDestination()+"&date="+dateFormat.format(alert.getDate()));
			}
		}
	}
	
	public Collection<Alert> getAlertsByPrincipal(){
		Collection<Alert> result;
		
		result = alertRepository.getAlertsOfUser(userService.findByPrincipal().getId());
		
		return result;
	}
	
}
