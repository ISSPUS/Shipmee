package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Rating extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private int value;
	private String valoration;
	private String comment;
	
	@Range(min=0,max=5)
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@NotNull
	@NotBlank
	public String getValoration() {
		return valoration;
	}
	public void setValoration(String valoration) {
		this.valoration = valoration;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = 5000)
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	// Relationships ----------------------------------------------------------
	private User user;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
