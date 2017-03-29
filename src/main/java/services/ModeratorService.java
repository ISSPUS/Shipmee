package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Moderator;
import repositories.ModeratorRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ModeratorService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private ModeratorRepository moderatorRepository;

	//Supporting services ----------------------------------------------------
	
	
	
	//Constructors -----------------------------------------------------------

	public ModeratorService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------

	
	//Other business methods -------------------------------------------------

	/**
	 * Devuelve el user que está realizando la operación
	 */
	public Moderator findByPrincipal(){
		Moderator result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = moderatorRepository.findByModeratorAccountId(userAccount.getId());
		Assert.notNull(result);
		
		return result;
	}

}
