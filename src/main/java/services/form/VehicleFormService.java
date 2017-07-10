package services.form;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
	
	static Logger log = Logger.getLogger(ActorFormService.class);

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
	
	public Vehicle reconstruct(VehicleForm vehicleForm, BindingResult binding) {
		Vehicle result = null;
		CommonsMultipartFile imageVehicleUpload = vehicleForm.getPicture();
		String nameImgVehicle = null;
		boolean errorImg = false;
		
		if (imageVehicleUpload.getSize()>0){
			try {
				nameImgVehicle = ImageUpload.subirImagen(imageVehicleUpload,ServerConfig.getPATH_UPLOAD());

			} catch (Exception e) {
				log.error(e, e.getCause());
				errorImg = true;
				switch (e.getMessage()) {
				case "message.error.imageUpload.incompatibleType":
					this.addBinding(binding, false, "picture", "message.error.imageUpload.incompatibleType", null);
					break;
				case "message.error.imageUpload.tooBig":
					this.addBinding(binding, false, "picture", "message.error.imageUpload.tooBig", null);
					break;
				default:
					this.addBinding(binding, false, "picture", "message.error.imageUpload.others", null);
					break;
				}
			}
		}
		
		if (vehicleForm.getVehicleId() == 0) {
			this.addBinding(binding, nameImgVehicle != null || errorImg, "picture", "message.error.imageUpload.notNull", null);

			if (!binding.hasErrors()){
				result = vehicleService.create();
				
				result.setBrand(vehicleForm.getBrand());
				result.setColor(vehicleForm.getColor());
				result.setModel(vehicleForm.getModel());
				
				if(nameImgVehicle != null)
					result.setPicture(ServerConfig.getURL_IMAGE()+nameImgVehicle);
			}
			
		} else if(vehicleForm.getVehicleId() != 0 && !binding.hasErrors()) {
				result = vehicleService.findOne(vehicleForm.getVehicleId());
				//User user = userService.findByPrincipal();
				
				result.setBrand(vehicleForm.getBrand());
				result.setColor(vehicleForm.getColor());
				result.setModel(vehicleForm.getModel());
				
				if(nameImgVehicle != null)
					result.setPicture(ServerConfig.getURL_IMAGE()+nameImgVehicle);
			//result.setUser(user);

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
	
	private void addBinding(Errors errors, boolean mustBeTrue, String field, String validationError, Object[] other){
		if (!mustBeTrue){
			errors.rejectValue(field, validationError, other, "");
		}
	}
}
