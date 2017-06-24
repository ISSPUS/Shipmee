package services;


import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.FundTransferPreference;
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
	
	static Logger log = Logger.getLogger(UserService.class);

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
		res.setDni("");
		res.setDniPhoto("");
		res.setPhone("");
		res.setPhoto("images/anonymous.png");
		res.setPasswordResetToken("");
		
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
		this.checkDNI(user);
		
		user = userRepository.save(user);
		
		return user;
	}
	
	//Other business methods -------------------------------------------------

	private void checkDNI(User user){
		
		log.trace(user.getDni());
		
		Pattern pattern;
		Matcher matcher;
		boolean match;
		
		pattern = Pattern.compile("^[0-9A-Z]{1}[0-9]{7}[A-Z]{1}$");
		matcher = pattern.matcher(user.getDni());
		match = matcher.find();
		
		log.trace(match);
		
		Assert.isTrue(user.getDni().equals("") || match, "user.edit.profile.dni.wrongPattern");
		
	}
	
	private void checkUser(User a){
		boolean isAdmin;
		boolean isAuthenticated;
		int actUserId;
		FundTransferPreference fundTransferPreference;
		
		User userInDB = null;
		Authority userAuth = new Authority();
		Authority adminAuth = new Authority();

		isAdmin = actorService.checkAuthority("ADMIN");
		isAuthenticated = actorService.checkLogin();
		userAuth.setAuthority(Authority.USER);
		adminAuth.setAuthority(Authority.ADMIN);

		if(a.getId() != 0){
			actUserId = actorService.findByPrincipal().getId();
			userInDB = this.findOne(a.getId());
			
			Assert.isTrue(isAdmin || (a.getId() == actUserId),
					"UserService.checkUser.modifyByOtherUser");
			
			if(!(a.getDniPhoto().equals(userInDB.getDniPhoto()) && 
					a.getDni().equals(userInDB.getDni()) &&
					a.getPhone().equals(userInDB.getPhone()) &&
					a.getName().equals(userInDB.getName()) &&
					a.getSurname().equals(userInDB.getSurname()) &&
					a.getBirthDate().equals(userInDB.getBirthDate()) &&
					a.getEmail().equals(userInDB.getEmail())
					)){
				a.setIsVerified(false);
			}

			if(a.getFundTransferPreference() != null) {
				fundTransferPreference = a.getFundTransferPreference();
				if(fundTransferPreference.getPaypalEmail() != null &&
						fundTransferPreference.getPaypalEmail().equals("")){
					Assert.isTrue(false,"You must fill in the information of your PayPal.");
				} else if((fundTransferPreference.getCountry() != null &&
						fundTransferPreference.getCountry().equals("")) ||
						(fundTransferPreference.getAccountHolder() != null &&
						fundTransferPreference.getAccountHolder().equals("")) ||
						(fundTransferPreference.getBankName() != null &&
						fundTransferPreference.getBankName().equals("")) ||
						(fundTransferPreference.getIBAN() != null &&
						fundTransferPreference.getIBAN().equals("")) ||
						(fundTransferPreference.getBIC() != null &&
						fundTransferPreference.getBIC().equals(""))) {
					Assert.isTrue(false,"You must fill in the information of your bank account.");
				}
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
	
	public Page<User> findAllByVerifiedActiveVerificationPending(int isVerified, int isActive, int verificationPending,
			int isModerator, Pageable page){
		Page<User> result;
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "UserService.findAllByVerifiedActive.RoleNotPermitted");
		
		result = userRepository.findAllByVerifiedActiveVerificationPending(isVerified, isActive, verificationPending,
				isModerator, page);
		
		return result;		
	}
	
	public void turnIntoModerator(int userId){
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "UserService.turnIntoModerator.RoleNotPermitted");

		User dbUser;
		Collection<Authority> authorities;
		Authority modAuthority;
		UserAccount userAccount;
		
		dbUser = this.findOne(userId);
		Assert.isTrue(dbUser.getIsVerified(), "UserService.turnIntoModerator.UserNotVerified");

		modAuthority = new Authority();
		
		modAuthority.setAuthority(Authority.MODERATOR);
		
		userAccount = dbUser.getUserAccount();
		authorities = userAccount.getAuthorities();
		
		if(!authorities.contains(modAuthority)){
			authorities.add(modAuthority);
			
			userAccount.setAuthorities(authorities);
			
			dbUser.setUserAccount(userAccount);
			
			this.save(dbUser);
		}
	}
	
	public void unturnIntoModerator(int userId){
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "UserService.unturnIntoModerator.RoleNotPermitted");

		User dbUser;
		Collection<Authority> authorities;
		Authority modAuthority;
		UserAccount userAccount;
		
		dbUser = this.findOne(userId);
		modAuthority = new Authority();
		
		modAuthority.setAuthority(Authority.MODERATOR);
		
		userAccount = dbUser.getUserAccount();
		authorities = userAccount.getAuthorities();
		
		if(authorities.contains(modAuthority)){
			authorities.remove(modAuthority);
			
			userAccount.setAuthorities(authorities);
			
			dbUser.setUserAccount(userAccount);
			
			this.save(dbUser);
		}
	}
	
	public void verifyUser(int userId){
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "UserService.verifyUser.RoleNotPermitted");

		User dbUser;
		
		dbUser = this.findOne(userId);
		
		Assert.isTrue(!dbUser.getPhoto().equals(""),"UserService.verifyUser.PhotoNotFound");
		Assert.isTrue(!dbUser.getDniPhoto().equals(""),"UserService.verifyUser.PhotoDniNotFound");
		Assert.isTrue(!dbUser.getDni().equals(""),"UserService.verifyUser.DniNumberNotFound");
		Assert.isTrue(!dbUser.getPhone().equals(""),"UserService.verifyUser.PhoneNotFound");
		
		dbUser.setIsVerified(true);
		
		this.save(dbUser);
	}
	
	public void unVerifyUser(int userId){
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "UserService.unVerifyUser.RoleNotPermitted");

		User dbUser;
		
		dbUser = this.findOne(userId);
		
		Assert.isTrue(dbUser.getIsVerified(),"UserService.unVerifyUser.NotIsVerified");
		
		dbUser.setIsVerified(false);
		
		this.save(dbUser);
	}

}
