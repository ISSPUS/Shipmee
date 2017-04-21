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
				"message.error.sizePrice.create.user");
		
		SizePrice result;
		
		result = new SizePrice();
		
		return result;
	}
	
	public SizePrice save(SizePrice sizePrice) {
		Assert.notNull(sizePrice, "message.error.sizePrice.notNull");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.sizePrice.save.user");
		
		User user;
		
		user = userService.findByPrincipal();
		
		Assert.isTrue(sizePrice.getRoute().getCreator().getId() == user.getId(), "message.error.sizePrice.save.user.own");
		
		sizePrice = sizePriceRepository.save(sizePrice);
			
		return sizePrice;
	}
	
	public void delete(SizePrice sizePrice) {
		Assert.notNull(sizePrice, "message.error.sizePrice.notNull");
		Assert.isTrue(sizePrice.getId() != 0, "message.error.sizePrice.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.sizePrice.delete.user");

		sizePriceRepository.delete(sizePrice);
	}
	
	public SizePrice findOne(int sizePriceId) {
		SizePrice result;
		
		result = sizePriceRepository.findOne(sizePriceId);
		
		return result;
	}

	// Other business methods -------------------------------------------------

	public Collection<SizePrice> findAllByRouteId(int routeId) {
		Collection<SizePrice> result;
		
		result = sizePriceRepository.findAllByRouteId(routeId);

		return result;
	}
	
}
