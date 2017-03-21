package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class SizePrice extends DomainEntity {

	// Attributes -------------------------------------------------------------
	private String size;
	private double price;
	
	@NotNull
	@NotBlank
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	@Min(value=0)
	@Digits(integer=9,fraction=2)
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	// Relationships ----------------------------------------------------------
	private Route route;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	
}
