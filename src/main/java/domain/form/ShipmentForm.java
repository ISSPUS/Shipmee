package domain.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class ShipmentForm {

	private String departureTime;
	private String maximumArriveTime;
	private String origin;
	private String destination;
	private double price;
	private String itemName;
	private String itemSize;
	private String itemEnvelope;
	private int shipmentId;
	private CommonsMultipartFile imagen;
	
	@NotNull
	public CommonsMultipartFile getImagen() {
		return imagen;
	}
	public void setImagen(CommonsMultipartFile imagen) {
		this.imagen = imagen;
	}
	
	@NotNull
	@NotBlank
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	
	@NotNull
	@NotBlank
	public String getMaximumArriveTime() {
		return maximumArriveTime;
	}
	public void setMaximumArriveTime(String maximumArriveTime) {
		this.maximumArriveTime = maximumArriveTime;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Digits(integer=9,fraction=2)
	@DecimalMin("0.01")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getItemSize() {
		return itemSize;
	}
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getItemEnvelope() {
		return itemEnvelope;
	}
	public void setItemEnvelope(String itemEnvelope) {
		this.itemEnvelope = itemEnvelope;
	}
	
	@Min(0)
	public int getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}
	
}
