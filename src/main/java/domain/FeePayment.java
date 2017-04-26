package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class FeePayment extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private Date paymentMoment;
	private CreditCard creditCard;
	private double amount;
	private double commission;
	private String type;
	private boolean isPayed;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/dd/MM HH:mm")
	public Date getPaymentMoment() {
		return paymentMoment;
	}

	public void setPaymentMoment(Date paymentMoment) {
		this.paymentMoment = paymentMoment;
	}

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 9, fraction = 2)
	@Valid
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@NotNull
	@Min(0)
	@Digits(integer = 9, fraction = 2)
	@Valid
	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean getIsPayed() {
		return isPayed;
	}

	public void setIsPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}


	// Relationships ----------------------------------------------------------

	private RouteOffer routeOffer;
	private ShipmentOffer shipmentOffer;
	private User purchaser;
	private User carrier;

	@Valid
	@ManyToOne(optional=true)
	public RouteOffer getRouteOffer() {
		return routeOffer;
	}
	public void setRouteOffer(RouteOffer routeOffer) {
		this.routeOffer = routeOffer;
	}
	
	@Valid
	@ManyToOne(optional = true)
	public ShipmentOffer getShipmentOffer() {
		return shipmentOffer;
	}
	public void setShipmentOffer(ShipmentOffer shipmentOffer) {
		this.shipmentOffer = shipmentOffer;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public User getPurchaser() {
		return purchaser;
	}
	public void setPurchaser(User purchaser) {
		this.purchaser = purchaser;
	}
	
	@Valid
	@ManyToOne(optional=false)
	public User getCarrier() {
		return carrier;
	}
	public void setCarrier(User carrier) {
		this.carrier = carrier;
	}

}
