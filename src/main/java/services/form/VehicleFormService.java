package services.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import domain.User;
import domain.Vehicle;
import domain.form.VehicleForm;
import services.UserService;
import services.VehicleService;
import utilities.ImageUpload;
import utilities.ServerConfig;

@Service
@Transactional
public class VehicleFormService {

	// Supporting services ----------------------------------------------------

	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public VehicleFormService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------


	public VehicleForm create() {
		VehicleForm result;
		
		result = new VehicleForm();
		
		result.setVehicleId(0);
		
		return result;
	}
	
	public Vehicle reconstruct(VehicleForm vehicleForm) {
		Vehicle result;
		CommonsMultipartFile imageVehicleUpload = vehicleForm.getPicture();
		String nameImgVehicle =null;
		
		if (imageVehicleUpload.getSize()>0){
			try {
				nameImgVehicle = ImageUpload.subirImagen(imageVehicleUpload,ServerConfig.getPATH_UPLOAD());

			} catch (Exception e) {
			
			}
		}
		
		if (vehicleForm.getVehicleId() == 0) {
			result = vehicleService.create();
			
			result.setBrand(vehicleForm.getBrand());
			result.setColor(vehicleForm.getColor());
			result.setModel(vehicleForm.getModel());
			
			Assert.notNull(nameImgVehicle, "message.error.imageUpload.notNull");

			result.setPicture(ServerConfig.getURL_IMAGE()+nameImgVehicle);
			
			
		} else if(vehicleForm.getVehicleId() != 0) {			
			result = vehicleService.findOne(vehicleForm.getVehicleId());
			User user = userService.findByPrincipal();
			
			result.setBrand(vehicleForm.getBrand());
			result.setColor(vehicleForm.getColor());
			result.setModel(vehicleForm.getModel());
			
			Assert.notNull(nameImgVehicle, "message.error.imageUpload.notNull");

			result.setPicture(ServerConfig.getURL_IMAGE()+nameImgVehicle);

			result.setUser(user);

		} else {
			result = null;
		}
		
		return result;
	}

	public VehicleForm contruct(int vehicleId) {
		VehicleForm result;
		Vehicle vehicle;
		
		
		result = this.create();
		vehicle = vehicleService.findOne(vehicleId);
		
		
		result.setBrand(vehicle.getBrand());
		result.setColor(vehicle.getColor());
		result.setModel(vehicle.getModel());
		result.setVehicleId(vehicle.getId());

		return result;
	}
}
