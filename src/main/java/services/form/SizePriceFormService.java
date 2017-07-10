package services.form;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Route;
import domain.SizePrice;
import domain.User;
import domain.form.SizePriceForm;
import services.RouteService;
import services.SizePriceService;
import services.UserService;

@Service
@Transactional
public class SizePriceFormService {

	// Supporting services ----------------------------------------------------

	@Autowired
	private SizePriceService sizePriceService;
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public SizePriceFormService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SizePriceForm create(int routeId) {
		SizePriceForm result;
		
		result = new SizePriceForm();
		
		result.setRouteId(routeId);
		
		return result;
	}
	
	public Collection<SizePrice> reconstruct(SizePriceForm sizePriceForm) {
		Collection<SizePrice> result;
						
		this.delete(sizePriceForm);
		result = saveCreate(sizePriceForm);
		
		return result;
	}

	public SizePriceForm findOne(int routeId) {
		SizePriceForm result;
		Route route;
		User user;
		
		route = routeService.findOne(routeId);
		user = userService.findByPrincipal();
		
		Assert.isTrue(user.getId() == route.getCreator().getId());
		
		result = this.create(routeId);
		
		result = constructForm(result);
		
		return result;
	}

	public void delete(SizePriceForm sizePriceForm) {
		Collection<SizePrice> res;
		
		res = sizePriceService.findAllByRouteId(sizePriceForm.getRouteId());
		
		for(SizePrice sp : res) {
			sizePriceService.delete(sp);
		}
	}
	
	private Collection<SizePrice> saveCreate(SizePriceForm sizePriceForm) {
		Assert.isTrue(checkSizePrices(sizePriceForm), "message.error.sizePrice.checkSizePrices");
		
		Collection<SizePrice> result;
		SizePrice sizePrice;
		Route route;
		
		result = new ArrayList<SizePrice>();
		route = routeService.findOne(sizePriceForm.getRouteId());
		
		if(sizePriceForm.getPriceS() != null) {
			sizePrice = sizePriceService.create();
			
			sizePrice.setSize("S");
			sizePrice.setPrice(Math.round((sizePriceForm.getPriceS()*1.15) * 100.0)/100.0);
			sizePrice.setRoute(route);
			
			sizePrice = sizePriceService.save(sizePrice);
			result.add(sizePrice);
		}
		
		if(sizePriceForm.getPriceM() != null) {
			sizePrice = sizePriceService.create();
			
			sizePrice.setSize("M");
			sizePrice.setPrice(Math.round((sizePriceForm.getPriceM()*1.15) * 100.0)/100.0);
			sizePrice.setRoute(route);
			
			sizePrice = sizePriceService.save(sizePrice);
			result.add(sizePrice);
		}
		
		if(sizePriceForm.getPriceL() != null) {
			sizePrice = sizePriceService.create();
			
			sizePrice.setSize("L");
			sizePrice.setPrice(Math.round((sizePriceForm.getPriceL()*1.15) * 100.0)/100.0);
			sizePrice.setRoute(route);
			
			sizePrice = sizePriceService.save(sizePrice);
			result.add(sizePrice);
		}
		
		if(sizePriceForm.getPriceXL() != null) {
			sizePrice = sizePriceService.create();
			
			sizePrice.setSize("XL");
			sizePrice.setPrice(Math.round((sizePriceForm.getPriceXL()*1.15) * 100.0)/100.0);
			sizePrice.setRoute(route);
			
			sizePrice = sizePriceService.save(sizePrice);
			result.add(sizePrice);
		}
		
		return result;
	}

	private SizePriceForm constructForm(SizePriceForm result) {
		Collection<SizePrice> sizePrices;
		
		sizePrices = sizePriceService.findAllByRouteId(result.getRouteId());
		
		for(SizePrice sp : sizePrices) {
			if(sp.getSize().equals("S")) {
				result.setPriceS(Math.round((sp.getPrice()/1.15) * 100.0)/100.0);
			} else if(sp.getSize().equals("M")) {
				result.setPriceM(Math.round((sp.getPrice()/1.15) * 100.0)/100.0);
			} else if(sp.getSize().equals("L")) {
				result.setPriceL(Math.round((sp.getPrice()/1.15) * 100.0)/100.0);
			} else if(sp.getSize().equals("XL")) {
				result.setPriceXL(Math.round((sp.getPrice()/1.15) * 100.0)/100.0);
			}
		}
		
		return result;
	}
	
	public boolean checkSizePrices(SizePriceForm sizePriceForm) {
		boolean result;
		
		result = true;
		
		if(sizePriceForm.getPriceS() == null && sizePriceForm.getPriceM() == null &&
				sizePriceForm.getPriceL() == null && sizePriceForm.getPriceXL() == null) {
			result = false;
		} 
		
		return result;
	}
}
