package domain.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

public class SizePriceForm {
	
	private Double priceS;
	private Double priceM;
	private Double priceL;
	private Double priceXL;
	private int routeId;
	private int sizePriceFormId;
	
	@Min(0)
	@Digits(integer=9,fraction=2)
	public Double getPriceS() {
		return priceS;
	}
	public void setPriceS(Double priceS) {
		this.priceS = priceS;
	}
	
	@Min(0)
	@Digits(integer=9,fraction=2)
	public Double getPriceM() {
		return priceM;
	}
	public void setPriceM(Double priceM) {
		this.priceM = priceM;
	}
	
	@Min(0)
	@Digits(integer=9,fraction=2)
	public Double getPriceL() {
		return priceL;
	}
	public void setPriceL(Double priceL) {
		this.priceL = priceL;
	}
	
	@Min(0)
	@Digits(integer=9,fraction=2)
	public Double getPriceXL() {
		return priceXL;
	}
	public void setPriceXL(Double priceXL) {
		this.priceXL = priceXL;
	}
	
	@Min(0)
	public int getRouteId() {
		return routeId;
	}
	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}
	
	@Min(0)
	public int getSizePriceFormId() {
		return sizePriceFormId;
	}
	public void setSizePriceFormId(int sizePriceFormId) {
		this.sizePriceFormId = sizePriceFormId;
	}
	
}
