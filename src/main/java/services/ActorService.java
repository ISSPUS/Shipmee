package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
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
import utilities.PayPalConfig;
import utilities.SendMail;

@Service
@Transactional
public class ActorService {
	
	static Logger log = Logger.getLogger(ActorService.class);
	private ApplicationContext context = new ClassPathXmlApplicationContext("Mail.xml");

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository actorRepository;
	
	// Supporting services ----------------------------------------------------
	
	@Autowired
	private MessageSource messageSource;
	
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
		actorRepository.save(actor);
		
		//log.trace(System.getenv("mailPassword"));
		SendMail mail = (SendMail) context.getBean("sendMail");
		Locale locale = new Locale(actor.getLocalePreferences());
		String url;
		
		url = PayPalConfig.getUrlBase()+"/passwordRecovery/reset.do?passwordResetToken="+passwordResetToken;

		// https://stackoverflow.com/a/2764993

		String[] args_body = { actor.getName(), url};
		
		mail.sendMail("shipmee.contact@gmail.com",
    		   actor.getEmail(),
    		   messageSource.getMessage("user.forgotPassword.subject", null, locale),
    		   messageSource.getMessage("user.forgotPassword.body", args_body, locale));
		
		return actor;
	}
	
	public Actor resetPassword(Actor actor, String password){
		Assert.notNull(actor);
		Assert.notNull(password);
		Assert.isTrue(!password.equals(""));
		
		Md5PasswordEncoder encoder;
		String hash;

		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(password, null);

		actor.setPasswordResetToken("");
		actor.getUserAccount().setPassword(hash);
		actorRepository.save(actor);
	
		return actor;
	}
	
	public static boolean checkBirthDate(Date bithdate){
		int minimumAge = 18;
		
		Date limitBirthDate;
		Calendar calTmp;
		boolean res = false;
		
		if (bithdate != null){
			calTmp = Calendar.getInstance();
			
			calTmp.add(Calendar.YEAR, -minimumAge);
			
			limitBirthDate = calTmp.getTime();
			
			res = ! (limitBirthDate.compareTo(bithdate) < 0);
		}
		return res;
	}
}
