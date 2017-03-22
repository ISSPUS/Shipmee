package utilities;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.xml.sax.SAXException;

import security.LoginService;

public abstract class AbstractTest {

	// Supporting services --------------------------------

	@Autowired
	private LoginService	loginService;


	// Set up and tear down -------------------------------
	private static boolean populate = true;

	@Before
	public void setUp() {
		if (populate) {
			PopulateDatabase.main(null);
			populate = false;
			
			try {
				UtilTest.mapBeansToIds();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}

	}

	@After
	public void tearDown() {
		unauthenticate();
	}

	// Supporting methods ---------------------------------

	public void authenticate(final String username) {
		UserDetails userDetails;
		TestingAuthenticationToken authenticationToken;
		SecurityContext context;

		if (username == null)
			authenticationToken = null;
		else {
			userDetails = this.loginService.loadUserByUsername(username);
			authenticationToken = new TestingAuthenticationToken(userDetails, null);
		}

		context = SecurityContextHolder.getContext();
		context.setAuthentication(authenticationToken);
	}

	public void unauthenticate() {
		this.authenticate(null);
	}
	
	public void checkExceptions(final Class<?> expected, final Class<?> caught) {
		if (expected != null && caught == null)
			throw new RuntimeException(expected.getName() + " was expected");
		else if (expected == null && caught != null)
			throw new RuntimeException(caught.getName() + " was unexpected");
		else if (expected != null && caught != null && !expected.equals(caught))
			throw new RuntimeException(expected.getName() + " was expected, but " + caught.getName() + " was thrown");
	}
}
