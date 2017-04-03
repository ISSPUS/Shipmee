package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String explanation;
	private String type;
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = 5000)
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}



	// Relationships ----------------------------------------------------------
	private User moderator;
	private User creator;
	private User involved;
	
	@Valid
	@ManyToOne(optional=true)
	public User getModerator() {
		return moderator;
	}
	public void setModerator(User moderator) {
		this.moderator = moderator;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getInvolved() {
		return involved;
	}
	public void setInvolved(User involved) {
		this.involved = involved;
	}
	
	
	
}
