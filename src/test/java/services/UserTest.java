package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	//@Test
	public void positiveEditUser() {
		User user;

		user = userService.findOne(UtilTest.getIdFromBeanName("user2"));

		Assert.isTrue(user.getName().equals("Guillermo"));

		user.setName("Mariano");
		user = userService.save(user);

		Assert.isTrue(user.getName().equals("Mariano"));
	}
}