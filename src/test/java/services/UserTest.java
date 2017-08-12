package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.User;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserTest extends AbstractTest {

	static Logger log = Logger.getLogger(UserTest.class);

	// Service to test --------------------------------------------------------

	@Autowired
	private UserService userService;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ActorService actorService;

	// Test cases -------------------------------------------------------------

	/**
	 * @Test Register an User
	 * @result The user is registered and persisted into database
	 */
	@Test
	public void positiveRegisterUser() {
		User user;
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd/MM/yyyy");
		user = userService.create();
		user.setName("John");
		user.setSurname("Doe");
		user.setEmail("test@test.com");
		user.setPhone("123456789");
		try {
			user.setBirthDate(formatter.parse("02/02/1995"));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		user = userService.save(user);

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("John")
				&& userService.findOne(user.getId()).getEmail().equals("test@test.com"));
	}

	/**
	 * @test Modify an User
	 * @result The User is modified and persisted into database
	 */
	@Test
	public void positiveEditUser() {
		authenticate("user2");

		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user2"));

		Assert.isTrue(user.getName().equals("Lola"));

		user.setName("Mariano");
		user = userService.save(user);

		Assert.isTrue(user.getName().equals("Mariano"));

		unauthenticate();
	}

	/**
	 * @Test Register User
	 * @result We try register an User without email
	 *         <code>NullPointerException</code> is expected
	 */
	@Test(expected = NullPointerException.class)
	public void negativeRegisterUser() {
		User user;
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd/MM/yyyy");
		user = userService.create();
		user.setName("John");
		user.setSurname("Doe");
		user.setPhone("123456789");
		try {
			user.setBirthDate(formatter.parse("02/02/1995"));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		user = userService.save(user);

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("John")
				&& userService.findOne(user.getId()).getEmail().equals("test@test.com"));
	}

	/**
	 * @Test Register User
	 * @result We try register an User as logged User
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeRegisterUser1() {
		authenticate("user2");

		User user;
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd/MM/yyyy");
		user = userService.create();
		user.setName("John");
		user.setSurname("Doe");
		user.setEmail("test@test.com");
		user.setPhone("123456789");
		try {
			user.setBirthDate(formatter.parse("02/02/1995"));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		user = userService.save(user);

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("John")
				&& userService.findOne(user.getId()).getEmail().equals("test@test.com"));

		unauthenticate();
	}

	/**
	 * @Test Modify an User
	 * @result We try modify other User <code>IllegalArgumentException</code> is
	 *         expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeEditUser() {
		authenticate("user1");

		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user2"));

		Assert.isTrue(user.getName().equals("Lola"));

		user.setName("Mariano");
		user = userService.save(user);

		Assert.isTrue(user.getName().equals("Mariano"));

		unauthenticate();
	}

	/**
	 * @Test Check if a User is verified
	 * @result The result is correct
	 */
	@Test
	public void positiveCheckVerifiedUser() {
		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user2"));

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("Lola")
				&& userService.findOne(user.getId()).getIsVerified());
	}

	/**
	 * @Test Verify an User
	 * @result The user is verified and persisted into database
	 */
	@Test
	public void positiveVerifyUser() {
		authenticate("admin");
		
		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user3"));
		Assert.isTrue(!user.getIsVerified());
		user.setPhoto("images/anonymous2.png");
		userService.verifyUser(user.getId());

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("Carlos")
				&& userService.findOne(user.getId()).getIsVerified());
		
		unauthenticate();
	}

	/**
	 * @Test Check if a User is pending of verified
	 * @result The result is correct
	 */
	@Test
	public void positiveCheckVerifiedPendigUser() {
		authenticate("admin");
		
		User user;
		Pageable page;

		user = userService.findOne(UtilTest.getIdFromBeanName("user3"));
		page = new PageRequest(0, 10);

		Assert.isTrue(
				userService.findAllByVerifiedActiveVerificationPending(-1, -1, 1, -1, page).getContent().contains(user));
	
		unauthenticate();
	}
	
	/**
	 * @Test Unverify an user
	 * @result The result is correct
	 */
	@Test
	public void positiveCheckUnverifyUser() {
		authenticate("admin");
		
		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user3"));
		Assert.isTrue(!user.getIsVerified());
		user.setPhoto("images/anonymous2.png");
		userService.verifyUser(user.getId());

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("Carlos")
				&& userService.findOne(user.getId()).getIsVerified());
		
		userService.unVerifyUser(user.getId());
		
		Assert.isTrue(user.getIsVerified()==false);
		
		unauthenticate();
	}
	
	/**
	 * @Test Manage a moderator
	 * @result The result is correct
	 */
	@Test
	public void positiveManageModerator() {
		authenticate("user1");
		
		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user1"));
		Assert.isTrue(!actorService.checkAuthority("MODERATOR"));
		
		unauthenticate();
		authenticate("admin");
		
		userService.turnIntoModerator(user.getId());

		unauthenticate();
		authenticate("user1");
		
		Assert.isTrue(actorService.checkAuthority("MODERATOR"));
		
		unauthenticate();
		authenticate("admin");
		
		userService.unturnIntoModerator(user.getId());
		
		unauthenticate();
		authenticate("user1");
		
		Assert.isTrue(!actorService.checkAuthority("MODERATOR"));
		
		unauthenticate();
	}
	
	/**
	 * @Test Check if a User is pending of verified
	 * @result We try to verified an user that don't has photo of the DNI
	 * 			<code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeVerifyUser() {
		authenticate("admin");
		
		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user3"));
		user.setDniPhoto("");
		user = userService.save(user);
		Assert.isTrue(!user.getIsVerified() && user.getDniPhoto().equals(""));
		userService.verifyUser(user.getId());

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("Carlos")
				&& userService.findOne(user.getId()).getIsVerified());
		
		unauthenticate();
	}
	
	/**
	 * @Test Verify an User
	 * @result We try to verified an user without been an Admin
	 * 			<code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeVerifyUser1() {
		authenticate("user1");
		
		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user3"));
		user.setDniPhoto("");
		user = userService.save(user);
		Assert.isTrue(!user.getIsVerified() && user.getDniPhoto().equals(""));
		userService.verifyUser(user.getId());

		Assert.isTrue(user.getId() != 0 && userService.findOne(user.getId()).getName().equals("Carlos")
				&& userService.findOne(user.getId()).getIsVerified());
		
		unauthenticate();
	}
	
	/**
	 * @Test Check if a User is pending of verified
	 * @result We try to check if an user is verified without been an Admin
	 * 			<code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCheckVerifiedPendigUser() {
		authenticate("user1");
		
		User user;
		Pageable page;

		user = userService.findOne(UtilTest.getIdFromBeanName("user3"));
		page = new PageRequest(0, 10);

		Assert.isTrue(
				userService.findAllByVerifiedActiveVerificationPending(-1, -1, 1, -1, page).getContent().contains(user));
	
		unauthenticate();
	}
}