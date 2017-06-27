package controllers.actor;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.Message;
import services.form.MessageFormService;
import services.ActorService;
import services.MessageService;
@Controller
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {
	
	static Logger log = Logger.getLogger(MessageActorController.class);

	// Services ---------------------------------------------------------------

	@Autowired
	MessageService messageService;
	
	@Autowired
	ActorService actorService;

	// Constructors -----------------------------------------------------------

	public MessageActorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam (required=false, defaultValue="0") String userId , @RequestParam (required=false, defaultValue="") String subject) {
		ModelAndView result;
		MessageFormService messageForm;
		Message message;
		Actor recipient = null;

		message = messageService.create();
		messageForm = messageService.messageToMessageForm(message);
				
		if(!userId.equals("0")) {
			recipient = actorService.findOne(new Integer(userId));
			messageForm.setRecipient(recipient.getUserAccount().getUsername());
		}
		
		if(!subject.isEmpty()){
			messageForm.setSubject(subject);
		}
		
		result = createEditModelAndView(messageForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageFormService messageForm, BindingResult binding) {
		ModelAndView result;
		String messageError;
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(messageForm);
		} else {
			try {
				messageService.save(messageForm);
				result = new ModelAndView("redirect:received.do?page=1");
			} catch (Throwable oops) {
				log.error(oops.getMessage());
				messageError="message.commit.error";
				if(oops.getMessage().contains("message.error")){
					messageError=oops.getMessage();
				}
				result = createEditModelAndView(messageForm, messageError);
			}
		}

		return result;
	}

	@RequestMapping("/received")
	public ModelAndView messagesReceived(@RequestParam int page) {
		ModelAndView result;
		Page<Message> items;
		Pageable pageable;
		pageable = new PageRequest(page - 1, 5);

		items = messageService.findAllReceived(pageable);
		
		result = this.createListModelAndView(items, "messages.received", "message/actor/received.do?page=");

		return result;
	}

	@RequestMapping("/sent")
	public ModelAndView messagesSent(@RequestParam int page) {
		ModelAndView result;
		Page<Message> items;
		Pageable pageable;
		pageable = new PageRequest(page - 1, 5);

		items = messageService.findAllSent(pageable);
		
		result = this.createListModelAndView(items, "messages.sent", "message/actor/sent.do?page=");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(MessageFormService messageForm) {
		ModelAndView result;

		result = createEditModelAndView(messageForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(MessageFormService messageFormService, String messageError) {
		ModelAndView result;

		result = new ModelAndView("message/edit");
		result.addObject("messageFormService", messageFormService);
		result.addObject("messageError", messageError);
		result.addObject("total_received", messageService.countMessagesReceivedtByActor());
		result.addObject("total_sent", messageService.countMessagesSentByActor());

		return result;
	}
	protected ModelAndView createListModelAndView(Page<Message> messages, String infoMessages, String urlPage) {
		ModelAndView result;

		result = new ModelAndView("message/list");
		result.addObject("messages", messages.getContent());
		result.addObject("p", messages.getNumber() + 1);
		result.addObject("total_pages", messages.getTotalPages());
		result.addObject("total_received", messageService.countMessagesReceivedtByActor());
		result.addObject("total_sent", messageService.countMessagesSentByActor());
		result.addObject("infoMessages", infoMessages);
		result.addObject("urlPage", urlPage);

		return result;
	}

}
