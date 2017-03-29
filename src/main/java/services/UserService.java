package services;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import domain.Rank;
import domain.Route;
import domain.User;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class UserService {
	//Managed repository -----------------------------------------------------
	
	@Autowired
	private UserRepository userRepository;

	//Supporting services ----------------------------------------------------
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private RankService rankService;
	
	//Constructors -----------------------------------------------------------

	public UserService(){
		super();
	}
	
	//Simple CRUD methods ----------------------------------------------------

	public User create(){
		User res;
		Collection<Route> routes;
		Rank rank;
		UserAccount userAccount;
		
		routes = new ArrayList<>();
		rank = rankService.initializeUser();
		userAccount = userAccountService.createComplete("", "", "USER");
		
		res = new User();
		
		res.setIsActive(true);
		res.setIsVerified(false);
		res.setRoutes(routes);
		res.setRank(rank);
		res.setUserAccount(userAccount);
		
		return res;
	}
	
	
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
		
		System.out.println("SaveUser need checks");
		
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

		result = new User();
		result = userRepository.findByUsername(username);

		return result;
	}

}
