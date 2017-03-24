package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Message;
import domain.User;
import services.form.MessageFormService;
import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		Message result;
		User user;
		long segundos;
		Date momento;

		result = new Message();
		user = userService.findByPrincipal();
		segundos = System.currentTimeMillis() - 1;
		momento = new Date(segundos);

		result.setMoment(momento);
		Assert.notNull(user);
		result.setSender(user);

		return result;
	}
	
	public Message findOne(int messageId) {
		Message result;

		result = messageRepository.findOne(messageId);

		return result;
	}
	
	public Message save(Message message) {
		Assert.notNull(message);
		Message result;

		result = messageRepository.save(message);
		
		return result;
	}
	
	public Message save(MessageFormService messageFormService) {
		Message message;
		Message res;
		Assert.notNull(messageFormService);

		message = MessageFormToMessage(messageFormService);

	
		res = messageRepository.save(message);
		
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

		res = create();
			
		// Comprobamos si existe el usuario al que le queremos enviar el mensaje

		User recipient = userService.findByUsername(messageFormService.getRecipient());
		Assert.notNull(recipient,"message.error.recipient");
		res.setRecipient(recipient);
		
		
		res.setMoment(messageFormService.getMoment());
		res.setSubject(messageFormService.getSubject());
		res.setBody(messageFormService.getBody());
		
		//Comprobamos que el sender y el recipient sean diferentes
		
		Assert.isTrue(!res.getSender().equals(res.getRecipient()),"message.error.not.allow.send.yourself");
		
		return res;

	}

	public Page<Message> findAllReceived(Pageable page) {
		Page<Message> result;
		User user;

		user = userService.findByPrincipal();
		Assert.notNull(user);

		result = messageRepository.messagesReceived(user.getId(), page);
		Assert.notNull(result);
		return result;
	}

	public Page<Message> findAllSent(Pageable page) {
		Page<Message> result;
		User user;

		user = userService.findByPrincipal();
		Assert.notNull(user);

		result = messageRepository.messagesSent(user.getId(), page);
		Assert.notNull(result);
		return result;
	}
	
	public int countMessagesSentByUser(){
		int res;
		User user;
		
		user = userService.findByPrincipal();
		
		Assert.notNull(user);
		
		res = messageRepository.countMessagesSentByUserId(user.getId());
		
		return res;
	}
	
	public int countMessagesReceivedtByUser(){
		int res;
		User user;
		
		user = userService.findByPrincipal();
		
		Assert.notNull(user);
		
		res = messageRepository.countMessagesReceivedtByUserId(user.getId());
		
		return res;
	}
	
}