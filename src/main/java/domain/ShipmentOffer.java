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
public class ShipmentOffer extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private double amount;
	private String description;
	private boolean acceptedBySender;
	private boolean rejectedBySender;	
	
	@Min(0)
	@Digits(integer=9, fraction=2)
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
	
	public boolean getAcceptedBySender() {
		return acceptedBySender;
	}
	public void setAcceptedBySender(boolean acceptedBySender) {
		this.acceptedBySender = acceptedBySender;
	}
	
	public boolean getRejectedBySender() {
		return rejectedBySender;
	}
	public void setRejectedBySender(boolean rejectedBySender) {
		this.rejectedBySender = rejectedBySender;
	}



	// Relationships ----------------------------------------------------------
	private User user;
	private Shipment shipment;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Shipment getShipment() {
		return shipment;
	}
	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}
}
