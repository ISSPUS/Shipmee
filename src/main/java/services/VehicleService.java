package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	
	// Constructors -----------------------------------------------------------

	public VehicleService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	
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
	
	public void flush() {
		vehicleRepository.flush();
	}
	
}
