package domain.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import domain.Vehicle;

public class SizePriceForm {
	
	private Double priceS;
	private Double priceM;
	private Double priceL;
	private Double priceXL;
	private int routeId;
	private int sizePriceFormId;
	
	private String departureTime;
	private String arriveTime;
	private String origin;
	private String destination;
	private String itemEnvelope;
	private Vehicle vehicle;
	

	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getItemEnvelope() {
		return itemEnvelope;
	}
	public void setItemEnvelope(String itemEnvelope) {
		this.itemEnvelope = itemEnvelope;
	}
	
	@Min(0)
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	@Digits(integer=9,fraction=2)
	@DecimalMin("0.01")
	public Double getPriceS() {
		return priceS;
	}
	public void setPriceS(Double priceS) {
		this.priceS = priceS;
	}
	
	@Digits(integer=9,fraction=2)
	@DecimalMin("0.01")
	public Double getPriceM() {
		return priceM;
	}
	public void setPriceM(Double priceM) {
		this.priceM = priceM;
	}
	
	@Digits(integer=9,fraction=2)
	@DecimalMin("0.01")
	public Double getPriceL() {
		return priceL;
	}
	public void setPriceL(Double priceL) {
		this.priceL = priceL;
	}
	
	@Digits(integer=9,fraction=2)
	@DecimalMin("0.01")
	public Double getPriceXL() {
		return priceXL;
	}
	public void setPriceXL(Double priceXL) {
		this.priceXL = priceXL;
	}
	
	@Min(0)
	public int getSizePriceFormId() {
		return sizePriceFormId;
	}
	public void setSizePriceFormId(int sizePriceFormId) {
		this.sizePriceFormId = sizePriceFormId;
	}
	
}
