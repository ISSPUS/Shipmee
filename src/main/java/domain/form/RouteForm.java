package domain.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Vehicle;

public class RouteForm {

	private String departureTime;
	private String arriveTime;
	private String origin;
	private String destination;
	private String itemEnvelope;
	private Vehicle vehicle;
	private int routeId;
	
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
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
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
	
	
	
}
