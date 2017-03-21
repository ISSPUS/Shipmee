package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.SizePrice;
import domain.User;
import repositories.SizePriceRepository;

@Service
@Transactional
public class SizePriceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SizePriceRepository sizePriceRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserService userService;
	
	
	// Constructors -----------------------------------------------------------

	public SizePriceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public SizePrice create() {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can create a sizePrice");
		
		SizePrice result;
		
		result = new SizePrice();
		
		return result;
	}
	
	public SizePrice save(SizePrice sizePrice) {
		Assert.notNull(sizePrice);
		Assert.isTrue(actorService.checkAuthority("USER"), "Only an user can save sizePrices");
		
		User user;
		
		user = userService.findByPrincipal();
		
		Assert.isTrue(sizePrice.getRoute().getCreator().getId() == user.getId(), "Both Ids must be the same.");
		
		sizePrice = sizePriceRepository.save(sizePrice);
			
		return sizePrice;
	}
	
	public void delete(SizePrice sizePrice) {
		Assert.notNull(sizePrice);
		Assert.isTrue(sizePrice.getId() != 0);
		Assert.isTrue(actorService.checkAuthority("USER"), "Only an user can delete sizePrices");

		sizePriceRepository.delete(sizePrice);
	}
	
	public SizePrice findOne(int sizePriceId) {
		SizePrice result;
		
		result = sizePriceRepository.findOne(sizePriceId);
		
		return result;
	}

	// Other business methods -------------------------------------------------
	
	public void flush() {
		sizePriceRepository.flush();
	}

	public Collection<SizePrice> findAllByRouteId(int routeId) {
		Collection<SizePrice> result;
		
		result = sizePriceRepository.findAllByRouteId(routeId);

		return result;
	}
	
}
