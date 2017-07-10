package services;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;
import domain.Vehicle;
import repositories.VehicleRepository;

@Service
@Transactional
public class VehicleService {

	static Logger log = Logger.getLogger(VehicleService.class);

	// Managed repository -----------------------------------------------------

	@Autowired
	private VehicleRepository vehicleRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public VehicleService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Vehicle create() {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"message.error.vehicle.create.user");
		
		Vehicle result;
		User user;
		
		result = new Vehicle();
		user = userService.findByPrincipal();
		
		result.setUser(user);
		result.setDeleted(false);
		
		return result;
	}
	
	public Vehicle save(Vehicle vehicle) {
		Assert.notNull(vehicle, "message.error.vehicle.notNull");
		
		Vehicle vehiclePreSave;
		User user;
		
		user = userService.findByPrincipal();
				
		if(vehicle.getId() == 0) {
			vehicle.setUser(user);
			vehicle.setDeleted(false);
			
			vehicle = vehicleRepository.save(vehicle);
			
		} else {
			vehiclePreSave = this.findOne(vehicle.getId());
			
			Assert.isTrue(user.getId() == vehiclePreSave.getUser().getId(), "message.error.vehicle.save.user.own");
			Assert.isTrue(vehiclePreSave.isDeleted() == false, "message.error.vehicle.save.user.deleted");
			
			vehicle.setDeleted(false);
			
			vehicle = vehicleRepository.save(vehicle);
		}
			
		return vehicle;
	}
	
	public void delete(Vehicle vehicle) {
		Assert.notNull(vehicle, "message.error.vehicle.notNull");
		Assert.isTrue(vehicle.getId() != 0, "message.error.vehicle.mustExist");
		Assert.isTrue(actorService.checkAuthority("USER"), "message.error.vehicle.delete.user");

		User user;
		
		user = userService.findByPrincipal();

		Assert.isTrue(user.getId() == vehicle.getUser().getId(), "message.error.vehicle.delete.user.own");
						
		vehicle.setDeleted(true);
		vehicleRepository.save(vehicle);
	}
	
	public Vehicle findOne(int vehicleId) {
		Vehicle result;
		User user;

		user=userService.findByPrincipal();
		result = vehicleRepository.findOne(vehicleId);
		
		Assert.notNull(result);
		Assert.isTrue(result.getUser().equals(user));

		return result;
	}
	
	public Collection<Vehicle> findAll() {
		Collection<Vehicle> result;

		result = vehicleRepository.findAll();

		return result;
	}
	

	// Other business methods -------------------------------------------------
	
	public Page<Vehicle> findAllNotDeletedByUser(Pageable page) {
		Page<Vehicle> result;
		User user;
		
		user = userService.findByPrincipal();

		result = vehicleRepository.findAllNotDeletedByUserId(user.getId(),page);
		
		return result;
	}
}
