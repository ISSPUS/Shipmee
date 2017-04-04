package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Message;
import repositories.MessageRepository;
import services.form.MessageFormService;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MessageTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private MessageService messageService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ActorService actorService;
	
	// Test cases -------------------------------------------------------------


	@Test
	public void messageCreateAPositive1(){
		
		authenticate("user1");
		
		Collection<Message> previous;
		Message message;
		Message savedMessage;
		Actor recipient;
		MessageFormService messForm;
		
		previous = messageRepository.findAll();
		
		message = messageService.create();
		messForm = messageService.messageToMessageForm(message);
		recipient = actorService.findOne(UtilTest.getIdFromBeanName("user2"));
		
		messForm.setBody("Este es el cuerpo");
		messForm.setSubject("Este es el asunto");
		messForm.setRecipient(recipient.getUserAccount().getUsername());
		
		savedMessage = messageService.save(messForm);
		
		Assert.isTrue(messageRepository.findAll().contains(savedMessage));
		Assert.isTrue(savedMessage.getSubject().equals(messForm.getSubject()) &&
				savedMessage.getBody().equals(messForm.getBody()) &&
				savedMessage.getRecipient().getUserAccount().getUsername()
					.equals(recipient.getUserAccount().getUsername()) &&
				savedMessage.getSender().equals(actorService.findByPrincipal()));
		Assert.isTrue(previous.size() + 1 == messageRepository.findAll().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void messageCreateANegative1(){
		
		authenticate("user1");
		
		Message message;
		Message savedMessage;
		Actor fakeSender;
		Actor recipient;
		MessageFormService messForm;
		
		message = messageService.create();
		messForm = messageService.messageToMessageForm(message);
		fakeSender = actorService.findOne(UtilTest.getIdFromBeanName("user2"));
		recipient = actorService.findOne(UtilTest.getIdFromBeanName("user3"));

		
		messForm.setBody("Este es el cuerpo");
		messForm.setSubject("Este es el asunto");
		
		// Cambiamos quien lo envia
		messForm.setRecipient(recipient.getUserAccount().getUsername());
		messForm.setSender(fakeSender.getUserAccount().getUsername());
		
		savedMessage = messageService.save(messForm);
		
		Assert.isTrue(savedMessage.getSender().getUserAccount().getUsername()
					.equals(messForm.getSender()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void messageCreateANegative2(){
		
		authenticate("user1");
		
		Collection<Message> previous;
		Message message;
		Message savedMessage;
		Actor recipient;
		MessageFormService messForm;
		Calendar lastWeek = Calendar.getInstance();
		lastWeek.setTime(new Date());
		lastWeek.add(Calendar.DATE, -7);  // Fecha muy en pasado
		
		previous = messageRepository.findAll();
		
		message = messageService.create();
		messForm = messageService.messageToMessageForm(message);
		recipient = actorService.findOne(UtilTest.getIdFromBeanName("user2"));
		
		messForm.setBody("Este es el cuerpo");
		messForm.setSubject("Este es el asunto");
		messForm.setRecipient(recipient.getUserAccount().getUsername());
		messForm.setMoment(lastWeek.getTime()); // Debería de sobreescribirse en el momento del guardado
		
		savedMessage = messageService.save(messForm);
		
		Assert.isTrue(savedMessage.getMoment().equals(messForm.getMoment()));
		Assert.isTrue(messageRepository.findAll().contains(savedMessage));
		Assert.isTrue(!(previous.size() == messageRepository.findAll().size()));
	}

	@Test
	public void messageListInboxPositive1() {
		authenticate("user1");
	
		Page<Message> alertsPage;
		Collection<Message> allMessages;
		Pageable pageable;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();
		pageable = new PageRequest(0, allMessages.size() + 1);

		userId = actorService.findByPrincipal().getId();
		
		alertsPage = messageService.findAllReceived(pageable);
		
		for(Message a:allMessages){
			if(a.getRecipient().getId() == userId){
				Assert.isTrue(alertsPage.getContent().contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(counter == alertsPage.getContent().size());
	}
	
	@Test
	public void messageListInboxPositive2() {
		authenticate("user3");
	
		Page<Message> alertsPage;
		Collection<Message> allMessages;
		Pageable pageable;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();
		pageable = new PageRequest(0, allMessages.size() + 1);

		userId = actorService.findByPrincipal().getId();
		
		alertsPage = messageService.findAllReceived(pageable);
		
		for(Message a:allMessages){
			if(a.getRecipient().getId() == userId){
				Assert.isTrue(alertsPage.getContent().contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(counter == alertsPage.getContent().size());
	}
	
	@Test
	public void messageListOutboxPositive1() {
		authenticate("user2");
	
		Page<Message> alertsPage;
		Collection<Message> allMessages;
		Pageable pageable;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();
		pageable = new PageRequest(0, allMessages.size() + 1);

		userId = actorService.findByPrincipal().getId();
		
		alertsPage = messageService.findAllSent(pageable);
		
		for(Message a:allMessages){
			if(a.getSender().getId() == userId){
				Assert.isTrue(alertsPage.getContent().contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(counter == alertsPage.getContent().size());
	}
	
	@Test
	public void messageListOutboxPositive2() {
		authenticate("user3");
	
		Page<Message> alertsPage;
		Collection<Message> allMessages;
		Pageable pageable;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();
		pageable = new PageRequest(0, allMessages.size() + 1);

		userId = actorService.findByPrincipal().getId();
		
		alertsPage = messageService.findAllSent(pageable);
		
		for(Message a:allMessages){
			if(a.getSender().getId() == userId){
				Assert.isTrue(alertsPage.getContent().contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(counter == alertsPage.getContent().size());
	}
	
	@Test
	public void messageCountSentPositive1() {
		authenticate("user2");
	
		Collection<Message> allMessages;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();

		userId = actorService.findByPrincipal().getId();
				
		for(Message a:allMessages){
			if(a.getSender().getId() == userId){
				counter++;
			}
		}
		
		Assert.isTrue(counter == messageService.countMessagesSentByActor());
	}
	
	@Test
	public void messageCountSentPositive2() {
		authenticate("user3");
	
		Collection<Message> allMessages;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();

		userId = actorService.findByPrincipal().getId();
				
		for(Message a:allMessages){
			if(a.getSender().getId() == userId){
				counter++;
			}
		}
		
		Assert.isTrue(counter == messageService.countMessagesSentByActor());
	}
	
	@Test
	public void messageCountReceivedPositive1() {
		authenticate("user1");
	
		Collection<Message> allMessages;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();

		userId = actorService.findByPrincipal().getId();
				
		for(Message a:allMessages){
			if(a.getRecipient().getId() == userId){
				counter++;
			}
		}
		
		Assert.isTrue(counter == messageService.countMessagesReceivedtByActor());
	}
	
	@Test
	public void messageCountReceivedPositive2() {
		authenticate("user3");
	
		Collection<Message> allMessages;
		Integer userId;
		int counter = 0;
		
		allMessages = messageRepository.findAll();

		userId = actorService.findByPrincipal().getId();
				
		for(Message a:allMessages){
			if(a.getRecipient().getId() == userId){
				counter++;
			}
		}
		
		Assert.isTrue(counter == messageService.countMessagesReceivedtByActor());
	}

}

