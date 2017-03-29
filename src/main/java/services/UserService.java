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
import security.Authority;
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
	
	@Autowired
	private ActorService actorService;
	
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
		userAccount = userAccountService.create("USER");
		
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
		
		this.checkUser(user);
		
		user = userRepository.save(user);
		
		return user;
	}
	
	//Other business methods -------------------------------------------------

	
	private void checkUser(User a){
		boolean isAdmin;
		boolean isAuthenticated;
		User actUser = null;
		User userInDB = null;
		Authority userAuth = new Authority();
		Authority adminAuth = new Authority();

		isAdmin = actorService.checkAuthority("ADMIN");
		isAuthenticated = actorService.checkLogin();
		userAuth.setAuthority(Authority.USER);
		adminAuth.setAuthority(Authority.ADMIN);
		
		if(!isAdmin && isAuthenticated){
			actUser = this.findByPrincipal();
		}
		
		if(a.getId() != 0){
			userInDB = this.findOne(a.getId());
			
			Assert.isTrue((a.getId() == actUser.getId()) || isAdmin,
					"UserService.checkUser.modifyByOtherUser");
			
			if(!(a.getDniPhoto().equals(userInDB.getDniPhoto()) && 
					a.getDni().equals(userInDB.getDni()) &&
					a.getPhone().equals(userInDB.getPhone()))){
				a.setIsVerified(false);
			}
		}else{
			Assert.isTrue(!isAuthenticated, 
					"UserService.checkLogin.creatingUserAuthenticated");
		}
		
		Assert.isTrue(a.getUserAccount().getAuthorities().contains(userAuth) &&
				!a.getUserAccount().getAuthorities().contains(adminAuth),
				"UserService.checkLogin.authorityIncorrect");

	}
	

	
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
