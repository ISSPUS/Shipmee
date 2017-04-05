package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import repositories.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private AdministratorRepository administratorRepository;

	//Supporting services ----------------------------------------------------
	
	
	
	//Constructors -----------------------------------------------------------

	public AdministratorService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------

	public Administrator save(Administrator administrator){
		
		Assert.notNull(administrator);
		
		administrator = administratorRepository.save(administrator);
		
		return administrator;
	}
	
	//Other business methods -------------------------------------------------


}
