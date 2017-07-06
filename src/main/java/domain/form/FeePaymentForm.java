package domain.form;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.CreditCard;


public class FeePaymentForm {
	
	private CreditCard creditCard;
	private double amount;
	private String description;
	private int id;
	private int sizePriceId;
	private int offerId;
	private int type;
	private int shipmentId;

	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@NotNull
	@Digits(integer = 9, fraction = 2)
	@DecimalMin("0.01")
	@Valid
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Min(0)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Min(0)
	public int getSizePriceId() {
		return sizePriceId;
	}

	public void setSizePriceId(int sizePriceId) {
		this.sizePriceId = sizePriceId;
	}

	@Min(0)
	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}
	
	/**
	 * Type == 1 -> Contract a route
	 * Type == 2 -> Create a routeOffer
	 * Type == 3 -> Accept a shipmentOffer
	 */
	@Min(0)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Min(0)
	public int getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}

	
	
}
