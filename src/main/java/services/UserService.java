package services;


import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.iban4j.BicFormatException;
import org.iban4j.BicUtil;
import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		res.setLocalePreferences("es");
		
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
		if(user.getDni()!=null && !user.getDni().equals("")){
			Assert.isTrue(this.checkDNI(user.getDni()), "user.edit.profile.dni.wrongPattern");
		}
		
		user = userRepository.save(user);
		
		return user;
	}
	
	//Other business methods -------------------------------------------------
	
	public boolean checkDNI(String dni) {
	 
     
        String letraMayuscula = ""; //Guardaremos la letra introducida en formato mayúscula
             
        // Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y también si el último caracter no es una letra
        if(dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false ) {
            return false;
        }
 
        // Al superar la primera restricción, la letra la pasamos a mayúscula
        letraMayuscula = (dni.substring(8)).toUpperCase();
 
        // Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres y que la letra introducida es igual a la de la ecuación
        // Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
        if(soloNumeros(dni) == true && letraDNI(dni).equals(letraMayuscula)) {
            return true;
        }
        else {
            return false;
        }
    }
 
    private boolean soloNumeros(String dni) {
 
            int i, j = 0;
            String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8 primeros dígitos
            String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
            String[] unoNueve = {"0","1","2","3","4","5","6","7","8","9"};
 
            for(i = 0; i < dni.length() - 1; i++) {
                numero = dni.substring(i, i+1);
 
                for(j = 0; j < unoNueve.length; j++) {
                    if(numero.equals(unoNueve[j])) {
                        miDNI += unoNueve[j];
                    }
                }
            }
 
            if(miDNI.length() != 8) {
                return false;
            }
            else {
                return true;
            }
        }
 
     private String letraDNI(String dni) {

        int miDNI = Integer.parseInt(dni.substring(0,8));
        int resto = 0;
        String miLetra = "";
        String[] asignacionLetra = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
 
        resto = miDNI % 23;
 
        miLetra = asignacionLetra[resto];
 
        return miLetra;
    }
	
	private void checkUser(User a){
		boolean isAdmin;
		boolean isAuthenticated;
		int actUserId;
		
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
		
		Assert.isTrue(!dbUser.getPhoto().equals("images/anonymous.png"),"UserService.verifyUser.PhotoNotFound");
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

	
	public Boolean IBANBICValidator(String IBAN, String BIC){
		Boolean result = false;
		
		if(IBAN.isEmpty() && BIC.isEmpty()){
			result = true;
		}else{
			try{
				IbanUtil.validate(IBAN);
				BicUtil.validate(BIC);
				
				result = true;
			}catch (IbanFormatException | InvalidCheckDigitException | UnsupportedCountryException | BicFormatException  e) {
				log.error("Error al validar el IBAN O BIC", e);
			}
		}
		
		return result;
	}
}
