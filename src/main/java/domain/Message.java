package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	// Constructors

	public Message() {
		super();
	}

	// Attributes
	private String subject;
	private String body;
	private Date moment;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotBlank
	@Column(length = 5000)
	//@SafeHtml(whitelistType = WhiteListType.BASIC)
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	

	// Relationships

	private Actor sender;
	private Actor recipient;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSender() {
		return sender;
	}

	public void setSender(Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getRecipient() {
		return recipient;
	}

	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}

}
