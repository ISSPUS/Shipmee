package services.form;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import domain.Actor;
import domain.User;
import domain.form.ActorForm;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.UserService;
import utilities.ImageUpload;
import utilities.ServerConfig;

@Service
@Transactional
public class ActorFormService {
	
	static Logger log = Logger.getLogger(ActorFormService.class);

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private Validator validator;
	
	// Constructors -----------------------------------------------------------

	public ActorFormService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public ActorForm createForm(Boolean isCreate) {
		ActorForm result;
				
		if(actorService.checkLogin() && !isCreate){
			result = this.createFromActor(actorService.findByPrincipal());
		}else{
			result = new ActorForm();
			result.setLocalePreferences("es");
		}
		
		return result;
	}
	
	private ActorForm createFromActor(Actor a){
		ActorForm res;
		
		res = new ActorForm();
		res.setName(a.getName());
		res.setSurname(a.getSurname());
		res.setEmail(a.getEmail());
		res.setBirthDate(a.getBirthDate());
		res.setPhone(a.getPhone());
		res.setDni(a.getDni());
		res.setUserName(a.getUserAccount().getUsername());
		res.setId(a.getId());
		res.setLocalePreferences(a.getLocalePreferences());
		
		
		return res;
	}
	
	public Object reconstruct(ActorForm actorForm, BindingResult binding) {
		Actor userWithUserName;
		Actor actActor;
		
		userWithUserName = actorService.findByUsername(actorForm.getUserName());
		
		validator.validate(actorForm, binding);

		
		// Chequear nombre de usuario único
		
		if (!actorService.checkLogin() ||
				(actorForm.getId() == 0 && actorService.checkAuthority(Authority.ADMIN))){
			// Registry
			
			this.addBinding(binding, actorForm.getPassword().equals(actorForm.getRepeatedPassword()),
					"repeatedPassword", "user.passwordMismatch", null);
			this.addBinding(binding, actorForm.getPassword().equals(actorForm.getRepeatedPassword()),
					"password", "user.passwordMismatch", null);
			this.addBinding(binding, actorForm.getPassword().length() > 4 && actorForm.getPassword().length() < 33,
					"password", "org.hibernate.validator.constraints.Length.message.personalize", null);
			this.addBinding(binding, userWithUserName == null, "userName", "user.userName.inUse", null);
			
			this.addBinding(binding, ActorService.checkBirthDate(actorForm.getBirthDate()), "birthDate", "user.birthDate.younger", null);
			
			if (!actorService.checkLogin() && !binding.hasErrors()){
				// Registry User
				User res;
				UserAccount uAccount;
				
				res = userService.create();
					// Commons
				uAccount = res.getUserAccount();
				res.setName(actorForm.getName());
				res.setSurname(actorForm.getSurname());
				res.setEmail(actorForm.getEmail());
				res.setBirthDate(actorForm.getBirthDate());
				uAccount.setUsername(actorForm.getUserName());
				uAccount.setPassword(actorForm.getPassword());
				
				uAccount = userAccountService.encodePassword(uAccount);
				res.setUserAccount(uAccount);
					// User
				this.addBinding(binding, actorForm.getAcceptLegalCondition(),
						"acceptLegalCondition", "user.rejectedLegalConditions", null);
				
				return res;
			}else if (!binding.hasErrors()){
				Assert.notNull(null, "Registro de usuarios (de cualquier rol) como admin no implementado");
				return null;
			}else{
				return null;
			}
			
		}else{
			// Editing 
			
			actActor = actorService.findByPrincipal();
			
			if(!actorForm.getPassword().equals("") || !actorForm.getRepeatedPassword().equals("")){
				// Password modified
				this.addBinding(binding, actorForm.getPassword().equals(actorForm.getRepeatedPassword()),
						"repeatedPassword", "user.passwordMismatch", null);
				this.addBinding(binding, actorForm.getPassword().equals(actorForm.getRepeatedPassword()),
						"password", "user.passwordMismatch", null);
				
				this.addBinding(binding, actorForm.getPassword().length() > 4 && actorForm.getPassword().length() < 33,
				"password", "org.hibernate.validator.constraints.Length.message.personalize", null);
				
			}
			
			if(actorForm.getDni()!=null && !actorForm.getDni().equals("")){
				// Password modified
				this.addBinding(binding, userService.checkDNI(actorForm.getDni()),
						"dni", "user.edit.profile.dni.wrongPattern", null);
				
			}
			
			if(!actorForm.getUserName().equals(actActor.getUserAccount().getUsername())){
				// UserName modified
				this.addBinding(binding, userWithUserName == null, "userName", "user.userName.inUse", null);
			}
			
			this.addBinding(binding, ActorService.checkBirthDate(actorForm.getBirthDate()), "birthDate", "user.birthDate.younger", null);

			
			if (actorService.checkAuthority(Authority.USER) && !binding.hasErrors()){
				// Registry User
				User res;
				UserAccount uAccount;
				
				res = userService.findOne(actActor.getId());
					// Commons
				uAccount = res.getUserAccount();
				res.setName(actorForm.getName());
				res.setSurname(actorForm.getSurname());
				res.setEmail(actorForm.getEmail());
				res.setBirthDate(actorForm.getBirthDate());
				res.setPhone(actorForm.getPhone());
				res.setDni(actorForm.getDni());
				res.setLocalePreferences(actorForm.getLocalePreferences());
				String nameImgDni = null;
				String nameImgProfile = null;
				
				CommonsMultipartFile imageProfileUpload = actorForm.getPhoto();
				CommonsMultipartFile imageDniUpload = actorForm.getDniPhoto();
				
				if (imageProfileUpload.getSize() > 0) {
					try {
						nameImgProfile = ImageUpload.subirImagen(imageProfileUpload, ServerConfig.getPATH_UPLOAD());

						res.setPhoto(ServerConfig.getURL_IMAGE() + nameImgProfile);
					} catch (Exception e) {
						log.error(e, e.getCause());
						switch (e.getMessage()) {
						case "message.error.imageUpload.incompatibleType":
							this.addBinding(binding, false, "photo", "message.error.imageUpload.incompatibleType", null);
							break;
						case "message.error.imageUpload.tooBig":
							this.addBinding(binding, false, "photo", "message.error.imageUpload.tooBig", null);
							break;
						default:
							this.addBinding(binding, false, "photo", "message.error.imageUpload.others", null);
							break;
						}
					}
				}

				if (imageDniUpload.getSize() > 0) {
					try {
						nameImgDni = ImageUpload.subirImagen(imageDniUpload, ServerConfig.getPATH_UPLOAD());
						
						res.setDniPhoto(ServerConfig.getURL_IMAGE() + nameImgDni);
					} catch (Exception e) {
						log.error(e, e.getCause());
						switch (e.getMessage()) {
						case "message.error.imageUpload.incompatibleType":
							this.addBinding(binding, false, "dniPhoto", "message.error.imageUpload.incompatibleType", null);
							break;
						case "message.error.imageUpload.tooBig":
							this.addBinding(binding, false, "dniPhoto", "message.error.imageUpload.tooBig", null);
							break;
						default:
							this.addBinding(binding, false, "dniPhoto", "message.error.imageUpload.others", null);
							break;
						}
					}
					}

					uAccount.setUsername(actorForm.getUserName());

					if (!actorForm.getPassword().equals("") || !actorForm.getRepeatedPassword().equals("")) {
						uAccount.setPassword(actorForm.getPassword());

						uAccount = userAccountService.encodePassword(uAccount);
					}
					res.setUserAccount(uAccount);
				
				return res;
			}else{
				return null;
			}
		}

	}
	
	private void addBinding(Errors errors, boolean mustBeTrue, String field, String validationError, Object[] other){
		if (!mustBeTrue){
			errors.rejectValue(field, validationError, other, "");
		}
	}
}
