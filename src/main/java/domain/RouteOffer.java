package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class RouteOffer extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private double amount;
	private String description;
	private boolean acceptedByCarrier;
	private boolean rejectedByCarrier;
	
	@Min(0)
	@Digits(integer=9,fraction=2)
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(length = 5000)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getAcceptedByCarrier() {
		return acceptedByCarrier;
	}
	public void setAcceptedByCarrier(boolean acceptedByCarrier) {
		this.acceptedByCarrier = acceptedByCarrier;
	}

	public boolean getRejectedByCarrier() {
		return rejectedByCarrier;
	}
	public void setRejectedByCarrier(boolean rejectedByCarrier) {
		this.rejectedByCarrier = rejectedByCarrier;
	}


	// Relationships ----------------------------------------------------------
	private Route route;
	private User user;
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
