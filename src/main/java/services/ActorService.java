package services;

import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import utilities.SendMail;

@Service
@Transactional
public class ActorService {
	
	static Logger log = Logger.getLogger(ShipmentService.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Mail.xml");

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserAccountService userAccountService;
	
	// Constructors -----------------------------------------------------------
	
	public ActorService(){
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll(){
		Collection<Actor> result;
		
		result = actorRepository.findAll();
		
		return result;
	}
	
	public Actor findOne(int actorId){
		Actor result;
		
		result = actorRepository.findOne(actorId);
		
		return result;
	}
	
	// Other business methods -------------------------------------------------7

	/**
	 *  Devuelve el actor que está realizando la operación
	 */
	//req: 24.1, 24.2
	public Actor findByPrincipal(){
		Actor result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);
		
		return result;
	}

	/**
	 * Comprueba si el usuario que está ejecutando tiene la AuthoritySolicitada
	 * @return boolean -> false si no es customer
	 * @param authority [ADMIN, USER, MODERATOR]
	 */
	public boolean checkAuthority(String authority){
		boolean result;
		Actor actor;
		Collection<Authority> authorities;
		result = false;

		try {
			actor = this.findByPrincipal();
			authorities = actor.getUserAccount().getAuthorities();
			
			for (Authority a : authorities) {
				if(a.getAuthority().equalsIgnoreCase(authority)){
					result = true;
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			result = false;
		}
		return result;
	}
	
	/**
	 * Comprueba si un usuario está autenticado
	 */
	public boolean checkLogin(){
		boolean result;
		
		result = true;
		
		try{
			this.findByPrincipal();
		} catch (Throwable e) {
			result = false;
		}
		return result;
	}	
	
	public Actor findByUsername(String username) {
		Actor result;

		result = actorRepository.findByUsername(username);

		return result;
	}
	
	public Actor findByPasswordResetToken(String passwordResetToken){
		Actor result;
		
		result = actorRepository.findByPasswordResetToken(passwordResetToken);
		
		return result;
	}
	
	public Actor forgotPassword(Actor actor){
		Assert.notNull(actor);
		Md5PasswordEncoder encoder;
		String passwordResetToken;

		encoder = new Md5PasswordEncoder();
		int i = (int) (new Date().getTime()/1000);
		
		passwordResetToken = encoder.encodePassword(""+actor.getId()+i, null);
		actor.setPasswordResetToken(passwordResetToken);
		actor = actorRepository.save(actor);
		
		//log.trace(System.getenv("mailPassword"));
		SendMail mail = (SendMail) context.getBean("sendMail");
		mail.sendMail("shipmee.contact@gmail.com",
    		   actor.getEmail(),
    		   "Shipmee - Recuperación de contraseña",
    		   "¡Hola "+actor.getName()+"! \n\n"
    		   		+ "Para recuperar tu contraseña te pedimos que accedas al siguiente enlace: \n\n"
    		   		+ "http://localhost:8080/Shipmee/passwordRecovery/reset.do?passwordResetToken="+passwordResetToken
    		   		+ "\n\nSi no has sido tú quien ha pedido restablecer la contraseña te rogamos que ignores éste mensaje."
    		   		+ "\n\nUn Saludo. \nShipmee.");
		
		return actor;
	}
	
	public Actor resetPassword(Actor actor, String password){
		Assert.isTrue(actor!=null && password!=null & password!="");
		UserAccount userAccount;
		
		actor.setPasswordResetToken("");
		actor = actorRepository.save(actor);
		
		userAccount = actor.getUserAccount();
		userAccount.setPassword(password);
		userAccountService.save(userAccount);
	
		return actor;
	}
}
