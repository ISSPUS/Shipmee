package domain.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class VehicleForm {
	
	private String brand;
	private String model;
	private CommonsMultipartFile picture;
	private String color;
	private int vehicleId;
	
	
	@Min(0)
	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	@NotNull
	public CommonsMultipartFile getPicture() {
		return picture;
	}
	
	public void setPicture(CommonsMultipartFile picture) {
		this.picture = picture;
	}
	
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
}
