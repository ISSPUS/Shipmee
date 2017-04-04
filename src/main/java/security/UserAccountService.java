package security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserAccountService {

	// Managed repository --------------------
	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructors --------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods -------------------

	public UserAccount create(String authority) {
		Assert.notNull(authority);
		UserAccount res;
		Authority a;
		Collection<Authority> authorities = new ArrayList<Authority>();

		a = new Authority();
		a.setAuthority(authority);
		authorities.add(a);

		res = new UserAccount();
		res.setAuthorities(authorities);

		return res;
	}

	public UserAccount save(UserAccount ua) {
		Assert.notNull(ua);

		UserAccount res;
		res = userAccountRepository.save(ua);

		return res;
	}

	public UserAccount findByUsername(String username) {
		Assert.notNull(username);
		UserAccount res;

		res = userAccountRepository.findByUsername(username);
		Assert.notNull(res);

		return res;
	}
	
	public UserAccount findByUserNameWithoutAsserts(String username) {
		Assert.notNull(username);
		UserAccount res;

		res = userAccountRepository.findByUsername(username);

		return res;
	}
	
	// Ancillary ---------------------------
	
	public UserAccount createComplete(String username, String Password, String authority){
		UserAccount result;
		
		result = this.create(authority);
		
		result.setUsername(username);
		result.setPassword(Password);
		
		return result;
	}
	
	/**
	 * This method hashCode the password
	 */
	public UserAccount encodePassword(UserAccount input){
		Md5PasswordEncoder encoder;
		String newPassword;
		
		encoder = new Md5PasswordEncoder();
				
		newPassword = encoder.encodePassword(input.getPassword(), null);

		input.setPassword(newPassword);
		
		return input;
	}
}
