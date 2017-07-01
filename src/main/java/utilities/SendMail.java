package utilities;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendMail{
	
	static Logger log = Logger.getLogger(SendMail.class);
	
	private JavaMailSenderImpl mailSender;
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendMail(String from, String to, String subject, String msg) {
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		//SimpleMailMessage message = new SimpleMailMessage();
		
		MimeMessageHelper message;
		try {
			message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			message.setFrom(from);
			message.setTo(to);
			message.setSubject(subject);
			message.setText("", msg);
		} catch (MessagingException e) {
			log.error("Error al enviar el email",e);
		}
		
		mailSender.send(mimeMessage);	
	}
}
