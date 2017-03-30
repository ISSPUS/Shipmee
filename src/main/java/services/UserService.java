package services;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.User;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private UserRepository userRepository;

	//Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;
	
	//Constructors -----------------------------------------------------------

	public UserService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------

	/**
	 * 
	 * @param user - Current user
	 * @return - Updated user
	 * 
	 * THIS VERSION IS DONE FOR PRIOR ANY USER CRUD DEVELOPMENT (EXPECTED FOR SPRINT 2).
	 * MUST BE REDONE.
	 * CHECK THAT selectRoute STILL WORKS!!
	 */
	public User save(User user){
		
		Assert.notNull(user);
		
		user = userRepository.save(user);
		
		return user;
	}
	
	//Other business methods -------------------------------------------------

	/**
	 * Devuelve el user que está realizando la operación
	 */
	public User findByPrincipal(){
		User result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = userRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		
		return result;
	}
	
	public User findOne(int userId){
		User result;
		
		result = userRepository.findOne(userId);
		Assert.notNull(result);
		
		return result;
	}

	public Collection<User> findAllByRoutePurchased(int routeId) {
		Collection<User> result;
		
		result = userRepository.findAllByRoutePurchased(routeId);

		return result;
	}
	
	public User findByUsername(String username) {
		User result;

		result = userRepository.findByUsername(username);

		return result;
	}
	
	public Page<User> findAllVerified(Pageable page){
		Assert.isTrue(actorService.checkAuthority("ADMIN"));
		
		Page<User> result;
		
		result = userRepository.findAllVerified(page);
		
		return result;
	}
	
	public int countAllVerified(){
		Assert.isTrue(actorService.checkAuthority("ADMIN"));

		int result = 0;
		
		result = userRepository.countAllVerified();
		
		return result;
	}
	
	public Page<User> findAllPending(Pageable page){
		Assert.isTrue(actorService.checkAuthority("ADMIN"));

		Page<User> result;
		
		result = userRepository.findAllPending(page);
		
		return result;
	}
	
	public int countAllPending(){
		Assert.isTrue(actorService.checkAuthority("ADMIN"));

		int result = 0;
		
		result = userRepository.countAllPending();
		
		return result;
	}
	
}
