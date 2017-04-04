package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.Complaint;
import domain.User;
import repositories.ComplaintRepository;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ComplaintTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ComplaintRepository complaintRepository;
	
	@Autowired
	private UserService userService;
	
	// Test cases -------------------------------------------------------------


	/** As a user, you can create a complaint about other user
	 * 
	 */
	@Test
	public void complaintCreatePositive1(){

		authenticate("user1");
		
		Complaint result;
		User userToComplaint;
		Integer allBefore;
		Integer allAfter;
		
		userToComplaint = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		allBefore = complaintRepository.findAll().size();
		
		result = complaintService.create(userToComplaint.getId());
		result.setExplanation("Esto es una queja de prueba");
		complaintService.save(result);
		
		allAfter = complaintRepository.findAll().size();
		
		Assert.isTrue(allAfter - allBefore == 1);
		
		unauthenticate();
	}
	
	/** As a user, you cannot create a complaint about yourself
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void complaintCreateNegative1(){

		authenticate("user1");
		
		Complaint result;
		User userToComplaint;
		Integer allBefore;
		Integer allAfter;
		
		userToComplaint = userService.findOne(UtilTest.getIdFromBeanName("user1"));
		allBefore = complaintRepository.findAll().size();
		
		result = complaintService.create(userToComplaint.getId());
		result.setExplanation("Esto es una queja de prueba");
		complaintService.save(result);
		
		allAfter = complaintRepository.findAll().size();
		
		Assert.isTrue(allAfter - allBefore == 1);
		
		unauthenticate();
	}
	
	/** As a unlogged user, you cannot create a complaint
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void complaintCreateNegative2(){
		
		Complaint result;
		User userToComplaint;
		Integer allBefore;
		Integer allAfter;
		
		userToComplaint = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		allBefore = complaintRepository.findAll().size();
		
		result = complaintService.create(userToComplaint.getId());
		result.setExplanation("Esto es una queja de prueba");
		complaintService.save(result);
		
		allAfter = complaintRepository.findAll().size();
		
		Assert.isTrue(allAfter - allBefore == 1);
		
	}
	
	/** As a user, you cannot create a complaint without a explanation
	 * 	 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void complaintCreateNegative3(){

		authenticate("user1");
		
		Complaint result;
		User userToComplaint;
		Integer allBefore;
		Integer allAfter;
		
		userToComplaint = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		allBefore = complaintRepository.findAll().size();
		
		result = complaintService.create(userToComplaint.getId());
		complaintService.save(result);
		
		allAfter = complaintRepository.findAll().size();
		
		Assert.isTrue(allAfter - allBefore == 1);
		
		unauthenticate();
	}
	
	/** As a moderator, you can modify a complaint.
	 * 
	 */
	@Test
	public void complaintModeratePositive1(){

		authenticate("user1");
		
		Complaint result;
		User userToComplaint;
		Integer allBefore;
		Integer allAfter;
		
		userToComplaint = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		allBefore = complaintRepository.findAll().size();
		
		result = complaintService.create(userToComplaint.getId());
		result.setExplanation("Esto es una queja de prueba");
		result = complaintService.save(result);
		
		allAfter = complaintRepository.findAll().size();
		
		Assert.isTrue(allAfter - allBefore == 1);
		
		unauthenticate();
		
		authenticate("moderator1");
		
		result.setType("Serious");
		result = complaintService.save(result);
		
		Assert.isTrue(result.getType().equals("Serious"));
						
		unauthenticate();
	}
	
	/** As a user, you cannot modify a complaint.
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void complaintModerateNegative1(){

		authenticate("user1");
		
		Complaint result;
		User userToComplaint;
		Integer allBefore;
		Integer allAfter;
		
		userToComplaint = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		allBefore = complaintRepository.findAll().size();
		
		result = complaintService.create(userToComplaint.getId());
		result.setExplanation("Esto es una queja de prueba");
		result = complaintService.save(result);
		
		allAfter = complaintRepository.findAll().size();
		
		Assert.isTrue(allAfter - allBefore == 1);		
		
		result.setType("Serious");
		result = complaintService.save(result);
		
		Assert.isTrue(result.getType().equals("Serious"));
				
		unauthenticate();
	}
	
	/** As a unlogged user, you cannot modify a complaint.
	 * 
	 */
	@Test(expected = IllegalArgumentException.class)
	public void complaintModerateNegative2(){

		authenticate("user1");
		
		Complaint result;
		User userToComplaint;
		Integer allBefore;
		Integer allAfter;
		
		userToComplaint = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		allBefore = complaintRepository.findAll().size();
		
		result = complaintService.create(userToComplaint.getId());
		result.setExplanation("Esto es una queja de prueba");
		result = complaintService.save(result);
		
		allAfter = complaintRepository.findAll().size();
		
		Assert.isTrue(allAfter - allBefore == 1);
		
		unauthenticate();
				
		result.setType("Serious");
		result = complaintService.save(result);
		
		Assert.isTrue(result.getType().equals("Serious"));
				
	}


}

