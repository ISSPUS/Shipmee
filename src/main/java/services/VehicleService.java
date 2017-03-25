package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;
import domain.Vehicle;
import repositories.VehicleRepository;

@Service
@Transactional
public class VehicleService {

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
				"Only an user can create a vehicle");
		
		Vehicle result;
		User user;
		
		result = new Vehicle();
		user = userService.findByPrincipal();
		
		result.setUser(user);
		
		return result;
	}
	
	public Vehicle save(Vehicle vehicle) {
		Assert.notNull(vehicle);
		
		Vehicle vehiclePreSave;
		User user;
		
		user = userService.findByPrincipal();
				
		if(vehicle.getId() == 0) {
			vehicle.setUser(user);
			
			vehicle = vehicleRepository.save(vehicle);
			
		} else {
			vehiclePreSave = this.findOne(vehicle.getId());
			
			Assert.isTrue(user.getId() == vehiclePreSave.getUser().getId(), "Only the owner can save this vehicle.");
			
			vehicle = vehicleRepository.save(vehicle);
		}
			
		return vehicle;
	}
	
	public void delete(Vehicle vehicle) {
		Assert.notNull(vehicle);
		Assert.isTrue(vehicle.getId() != 0);
		Assert.isTrue(actorService.checkAuthority("USER"), "Only an user can delete vehicles");

		User user;
		
		user = userService.findByPrincipal();

		Assert.isTrue(user.getId() == vehicle.getUser().getId(), "Only the user who created the vehicle can delete it");
						
		vehicleRepository.delete(vehicle);
	}
	
	public Vehicle findOne(int vehicleId) {
		Vehicle result;
		
		result = vehicleRepository.findOne(vehicleId);
		
		return result;
	}
	
	public Collection<Vehicle> findAll() {
		Collection<Vehicle> result;

		result = vehicleRepository.findAll();

		return result;
	}
	

	// Other business methods -------------------------------------------------
	
	public Collection<Vehicle> findAllByUser() {
		Collection<Vehicle> result;
		User user;
		
		user = userService.findByPrincipal();

		result = vehicleRepository.findAllByUserId(user.getId());

		return result;
	}
	
	public Collection<Vehicle> findAllByUserId(int userId) {
		Collection<Vehicle> result;
		User user;
		
		user = userService.findOne(userId);

		result = vehicleRepository.findAllByUserId(user.getId());

		return result;
	}	
}
