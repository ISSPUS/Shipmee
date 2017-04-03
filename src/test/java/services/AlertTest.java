package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.Alert;
import repositories.AlertRepository;
import repositories.UserRepository;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AlertTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private AlertService alertService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private AlertRepository alertRepository;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private UserRepository userRepository;
	
	// Test cases -------------------------------------------------------------


	@Test
	public void alertCreatePositive1(){
		
		authenticate("user1");
		
		Collection<Alert> previous;
		int userId;
		Alert alert;
		Alert savedAlert;
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(new Date());
		tomorrow.add(Calendar.DATE, 1);  // number of days to add
		
		previous = alertRepository.findAll();
		userId = actorService.findByPrincipal().getId();
		
		alert = alertService.create();
		alert.setDate(tomorrow.getTime());
		alert.setDestination("San Roque");
		alert.setOrigin("Sevilla");
		alert.setType("Route");
		
		savedAlert = alertService.save(alert);
		
		Assert.isTrue(alertRepository.findAll().contains(savedAlert));
		Assert.isTrue(savedAlert.getDate().equals(alert.getDate()) &&
				savedAlert.getDestination().equals(alert.getDestination()) &&
				savedAlert.getOrigin().equals(alert.getOrigin()) &&
				savedAlert.getType().equals(alert.getType()) &&
				savedAlert.getUser().getId() == userId);
		Assert.isTrue(alertService.getAlertsByPrincipal().contains(savedAlert));
		Assert.isTrue(previous.size() + 1 == alertRepository.findAll().size());
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void alertCreateNegative1(){
		
		authenticate("user1");
		
		Alert alert;
		Alert savedAlert;
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(new Date());
		tomorrow.add(Calendar.DATE, -1);  // FECHA EN PASADO ! !
				
		alert = alertService.create();
		alert.setDate(tomorrow.getTime());
		alert.setDestination("San Roque");
		alert.setOrigin("Sevilla");
		alert.setType("Route");
		
		savedAlert = alertService.save(alert);
		
		Assert.isTrue(savedAlert.getDate().equals(alert.getDate()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void alertCreateNegative2(){
		
		authenticate("user1");
		
		Alert alert;
		Alert savedAlert;
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(new Date());
		tomorrow.add(Calendar.DATE, 1);  // number of days to add
				
		alert = alertService.create();
		alert.setDate(tomorrow.getTime());
		alert.setDestination("San Roque");
		alert.setOrigin("Sevilla");
		alert.setType("Routeeeeeeeeeeeeeeeeeeeeeeeee");
		
		savedAlert = alertService.save(alert);
		
		Assert.isTrue(savedAlert.getType().equals(alert.getType()));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void alertCreateNegative3(){
		
		authenticate("user1");
		
		int userId;
		Alert alert;
		Alert savedAlert;
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(new Date());
		tomorrow.add(Calendar.DATE, 1);  // number of days to add
		
		userId = actorService.findByPrincipal().getId();

		alert = alertService.create();
		alert.setDate(tomorrow.getTime());
		alert.setDestination("San Roque");
		alert.setOrigin("Sevilla");
		alert.setType("Route");
		
		// Simulamos cambio de usuario por postHacking
		alert.setUser(userRepository.findOne(UtilTest.getIdFromBeanName("user2")));
		
		savedAlert = alertService.save(alert);
		
		Assert.isTrue(!(savedAlert.getUser().getId() == userId));
	}

	
	/**
	 * @Test List all my alerts
	 * @result The alerts are list
	 */
	@Test
	public void alertListPositive1() {
		authenticate("user2");
	
		Collection<Alert> alertsPage;
		
		Integer userId;
		int counter = 0;
		

		userId = actorService.findByPrincipal().getId();
		
		alertsPage = alertService.getAlertsByPrincipal();
		
		for(Alert a:alertRepository.findAll()){
			if(a.getUser().getId() == userId){
				Assert.isTrue(alertsPage.contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(counter == alertsPage.size());
	}
	
	/**
	 * @Test List all my alerts
	 * @result The alerts are list
	 */
	@Test
	public void alertListPositive2() {
		authenticate("user1");
	
		Collection<Alert> alertsPage;
		
		Integer userId;
		int counter = 0;
		

		userId = actorService.findByPrincipal().getId();
		
		alertsPage = alertService.getAlertsByPrincipal();
		
		for(Alert a:alertRepository.findAll()){
			if(a.getUser().getId() == userId){
				Assert.isTrue(alertsPage.contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(counter == alertsPage.size());
	}
	
	/**
	 * @Test List all my alerts
	 * @result The alerts are list
	 */
	@Test
	public void alertListPositive3() {
		authenticate("moderator1");
	
		Collection<Alert> alertsPage;
		
		Integer userId;
		int counter = 0;
		

		userId = actorService.findByPrincipal().getId();
		
		alertsPage = alertService.getAlertsByPrincipal();
		
		for(Alert a:alertRepository.findAll()){
			if(a.getUser().getId() == userId){
				Assert.isTrue(alertsPage.contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(counter == alertsPage.size());
	}
	/**
	 * @Test List all my alerts
	 * @result The alerts are not list because the act user is an admin
	 */
	@Test(expected = IllegalArgumentException.class)
	public void alertListNegative1() {
		authenticate("admin");
	
		alertService.getAlertsByPrincipal();
	}
	
	/**
	 * @Test List all my alerts
	 * @result The alerts are not list because the act user is unauthenticated
	 */
	@Test(expected = IllegalArgumentException.class)
	public void alertListNegative2() {
			
		alertService.getAlertsByPrincipal();
				
	}
	
	@Test
	public void alertDeletePositive1(){
		
		authenticate("user2");
		
		Collection<Alert> previous;
		Alert alert;
		
		previous = alertRepository.findAll();
		
		alert = alertService.findOne(UtilTest.getIdFromBeanName("alert1"));
		
		alertService.delete(alert);
		
		Assert.isTrue(!alertRepository.findAll().contains(alert));
		Assert.isTrue(!alertService.getAlertsByPrincipal().contains(alert));
		Assert.isTrue(previous.size() - 1 == alertRepository.findAll().size());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void alertDeleteNegative1(){
		
		authenticate("user2");
		
		Alert alert;
				
		alert = alertService.findOne(UtilTest.getIdFromBeanName("alert1"));
		
		authenticate("user1");
		
		alertService.delete(alert);
		
		authenticate("user2");
		
		Assert.isTrue(!alertRepository.findAll().contains(alert));
	}


}

