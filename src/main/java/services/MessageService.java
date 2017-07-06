package services;

import java.util.Date;
import java.util.Locale;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import domain.Actor;
import domain.Message;
import domain.RouteOffer;
import domain.ShipmentOffer;
import repositories.MessageRepository;
import services.form.MessageFormService;
import utilities.PayPalConfig;
import utilities.SendMail;

@Service
@Transactional
public class MessageService {
	
	static Logger log = Logger.getLogger(MessageService.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Mail.xml");

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	
	@Autowired
	private MessageSource messageSource;

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		Message result;
		Actor actor;
		long segundos;
		Date momento;

		result = new Message();
		actor = actorService.findByPrincipal();
		segundos = System.currentTimeMillis() - 1;
		momento = new Date(segundos);

		result.setMoment(momento);
		Assert.notNull(actor);
		result.setSender(actor);

		return result;
	}
	
	public Message save(Message message) {
		Assert.notNull(message);
		Message result;
		//Actor actor;
		long segundos;
		Date momento;
		
		//actor = actorService.findByPrincipal();
		segundos = System.currentTimeMillis() - 1;
		momento = new Date(segundos);
		message.setMoment(momento);
		/*Assert.notNull(actor);
		message.setSender(actor);*/
		
		result = messageRepository.save(message);
		//log.trace(System.getenv("mailPassword"));
		log.trace("Testing: "+System.getenv("testing"));
		if(System.getenv("testing")==null || !System.getenv("testing").equals("true")){
			SendMail mail = (SendMail) context.getBean("sendMail");
			mail.sendMail("shipmee.contact@gmail.com",
	    		   message.getRecipient().getEmail(),
	    		   "Shipmee - "+message.getSubject(),
	    		   message.getBody());
		}
		
		return result;
	}
	
	public Message save(MessageFormService messageFormService) {
		Message message;
		Message res;
		Assert.notNull(messageFormService);

		message = MessageFormToMessage(messageFormService);
		
		res = this.save(message);
		
		return res;
	}

	// Other business methods -------------------------------------------------
	
	public Message sendMessage(Actor sender, Actor recipient, String subject, String body) {
		Message result;

		result = create();
		result.setSender(sender);
		result.setRecipient(recipient);
		result.setSubject(subject);
		result.setBody(body);
		
		result = save(result);
		
		return result;
	}
	
	public MessageFormService messageToMessageForm(Message message) {
		MessageFormService res;

		res = new MessageFormService();

		res.setSender(message.getSender().getUserAccount().getUsername());
		res.setMoment(message.getMoment());
		
		return res;

	}

	public Message MessageFormToMessage(MessageFormService messageFormService) {
		Message res;
		Actor recipient;

		res = create();
			
		// Comprobamos si existe el usuario al que le queremos enviar el mensaje

		recipient = actorService.findByUsername(messageFormService.getRecipient());
		Assert.notNull(recipient,"message.error.recipient");
		res.setRecipient(recipient);
		
		
		res.setMoment(messageFormService.getMoment());
		res.setSubject(messageFormService.getSubject());
		res.setBody(messageFormService.getBody());
		
		//Comprobamos que el sender y el recipient sean diferentes
		
		Assert.isTrue(!res.getSender().equals(res.getRecipient()),"message.error.not.allow.send.yourself");
		
		return res;

	}
	
	public void autoMessageAcceptShipmentOffer(ShipmentOffer shipmentOffer){
		
		Actor sender;
		Actor recipient;
		String subject;
		String body;
		Locale locale;
		
		sender = actorService.findByUsername("shipmee");
		recipient = shipmentOffer.getUser();
		
		locale = new Locale(recipient.getLocalePreferences());

		subject = messageSource.getMessage("message.auto.acceptShipmentOffer.subject", null, locale);
		String[] args_body = {shipmentOffer.getShipment().getItemName(), 
				shipmentOffer.getShipment().getOrigin(), 
				shipmentOffer.getShipment().getDestination(),
				String.valueOf(shipmentOffer.getAmount()),
				shipmentOffer.getShipment().getCreator().getUserAccount().getUsername(),
				PayPalConfig.getUrlBase() + "/shipment/display.do?shipmentId=" + shipmentOffer.getShipment().getId()
				};
		
		body = messageSource.getMessage("message.auto.acceptShipmentOffer.body", args_body, locale);
		
		sendMessage(sender, recipient, subject, body);
	}
	
	public void autoMessageDenyShipmentOffer(ShipmentOffer shipmentOffer){
		
		Actor sender;
		Actor recipient;
		String subject;
		String body;
		Locale locale;
		
		sender = actorService.findByUsername("shipmee");
		recipient = shipmentOffer.getUser();
		
		locale = new Locale(recipient.getLocalePreferences());

		subject = messageSource.getMessage("message.auto.denyShipmentOffer.subject", null, locale);
		String[] args_body = {shipmentOffer.getShipment().getItemName(), 
				shipmentOffer.getShipment().getOrigin(), 
				shipmentOffer.getShipment().getDestination(),
				String.valueOf(shipmentOffer.getAmount()),
				shipmentOffer.getShipment().getCreator().getUserAccount().getUsername(),
				PayPalConfig.getUrlBase() + "/shipment/display.do?shipmentId=" + shipmentOffer.getShipment().getId()
				};
		body = messageSource.getMessage("message.auto.denyShipmentOffer.body", args_body, locale);
		
		sendMessage(sender, recipient, subject, body);
	}
	
	public void autoMessageAcceptRouteOffer(RouteOffer routeOffer){
		
		Actor sender;
		Actor recipient;
		String subject;
		String body;
		Locale locale;
		
		sender = actorService.findByUsername("shipmee");
		recipient = routeOffer.getUser();
		
		locale = new Locale(recipient.getLocalePreferences());
		
		subject = messageSource.getMessage("message.auto.acceptRouteOffer.subject", null, locale);
		String[] args_body = {routeOffer.getRoute().getOrigin(), 
				routeOffer.getRoute().getDestination(), 
				String.valueOf(routeOffer.getAmount()),
				routeOffer.getRoute().getCreator().getUserAccount().getUsername(), 
				PayPalConfig.getUrlBase() + "/route/display.do?routeId=" + routeOffer.getRoute().getId()
				};

		body = messageSource.getMessage("message.auto.acceptRouteOffer.body", args_body, locale);
		
		sendMessage(sender, recipient, subject, body);
	}
	
	public void autoMessageDenyRouteOffer(RouteOffer routeOffer){
		
		Actor sender;
		Actor recipient;
		String subject;
		String body;
		Locale locale;
		
		sender = actorService.findByUsername("shipmee");
		recipient = routeOffer.getUser();
		
		locale = new Locale(recipient.getLocalePreferences());

		subject = messageSource.getMessage("message.auto.denyRouteOffer.subject", null, locale);
		String[] args_body = {routeOffer.getRoute().getOrigin(), 
				routeOffer.getRoute().getDestination(), 
				String.valueOf(routeOffer.getAmount()),
				routeOffer.getRoute().getCreator().getUserAccount().getUsername(), 
				PayPalConfig.getUrlBase() + "/route/display.do?routeId=" + routeOffer.getRoute().getId()
				};
		body = messageSource.getMessage("message.auto.denyRouteOffer.body", args_body, locale);

		
		sendMessage(sender, recipient, subject, body);
	}

	public Page<Message> findAllReceived(Pageable page) {
		Page<Message> result;
		Actor actor;

		actor = actorService.findByPrincipal();
		Assert.notNull(actor);

		result = messageRepository.messagesReceived(actor.getId(), page);
		Assert.notNull(result);
		return result;
	}

	public Page<Message> findAllSent(Pageable page) {
		Page<Message> result;
		Actor actor;

		actor = actorService.findByPrincipal();
		Assert.notNull(actor);

		result = messageRepository.messagesSent(actor.getId(), page);
		Assert.notNull(result);
		return result;
	}
	
	public int countMessagesSentByActor(){
		int res;
		Actor actor;
		
		actor = actorService.findByPrincipal();
		
		Assert.notNull(actor);
		
		res = messageRepository.countMessagesSentByActorId(actor.getId());
		
		return res;
	}
	
	public int countMessagesReceivedtByActor(){
		int res;
		Actor actor;
		
		actor = actorService.findByPrincipal();
		
		Assert.notNull(actor);
		
		res = messageRepository.countMessagesReceivedtByActorId(actor.getId());
		
		return res;
	}
	
}
