package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.Shipment;
import domain.ShipmentOffer;
import domain.User;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ShipmentTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private ShipmentService shipmentService;

	// Supporting services ----------------------------------------------------

	 @Autowired 
	 private UserService userService;

	// Test cases -------------------------------------------------------------

	 /**
	  * @Test Create a Shipment
	  * @result The shipment is created.
	  */
	 @Test
	 public void positiveCreateShipment1(){
		 
		 authenticate("user1");
		 
		 Integer numberOfShipmentBefore = shipmentService.findAll().size();
		 
		 Shipment shipment;
		 Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		 Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		 
		 shipment = shipmentService.create();
		 shipment.setOrigin("Sevilla");
		 shipment.setDestination("Madrid");
		 shipment.setDepartureTime(departureTime);
		 shipment.setMaximumArriveTime(maximumArrivalTime);
		 shipment.setItemSize("L");
		 shipment.setPrice(20.0);
		 shipment.setItemName("Bolsa de deporte");
		 shipment.setItemPicture("https://www.elitefts.com/black-red-crescent-duffel-bag.html");
		 shipment.setItemEnvelope("Open");
		 
		 shipmentService.save(shipment);
		 
		 Integer numberOfShipmentAfter = shipmentService.findAll().size();

		 Assert.isTrue(numberOfShipmentAfter - numberOfShipmentBefore == 1);
		 
		 unauthenticate();
		 
	 }
	 
	 /**
	  * @Test Create a Shipment while been unauthenticated
	  * @result The shipment is not created.
	  */
	 @Test(expected = IllegalArgumentException.class)
	 public void negativeCreateShipment1(){
		 		 
		 Integer numberOfShipmentBefore = shipmentService.findAll().size();
		 
		 Shipment shipment;
		 Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		 Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();

		 shipment = shipmentService.create();
		 shipment.setOrigin("Sevilla");
		 shipment.setDestination("Madrid");
		 shipment.setDepartureTime(departureTime);
		 shipment.setMaximumArriveTime(maximumArrivalTime);
		 shipment.setItemSize("L");
		 shipment.setPrice(20.0);
		 shipment.setItemName("Bolsa de deporte");
		 shipment.setItemPicture("https://www.elitefts.com/black-red-crescent-duffel-bag.html");
		 shipment.setItemEnvelope("Open");
		 
		 shipmentService.save(shipment);
		 
		 Integer numberOfShipmentAfter = shipmentService.findAll().size();

		 Assert.isTrue(numberOfShipmentAfter - numberOfShipmentBefore == 1);
		 
	 }
	 
	 /**
	  * @Test Create a Shipment with a MaximumArrivalTime before the DepartureTime.
	  * @result The shipment is  not created.
	  */
	 @Test(expected = IllegalArgumentException.class)
	 public void negativeCreateShipment2(){
		 
		 
		 authenticate("user1");
		 
		 Integer numberOfShipmentBefore = shipmentService.findAll().size();
		 
		 Shipment shipment;
		 Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		 Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();

		 shipment = shipmentService.create();
		 shipment.setOrigin("Sevilla");
		 shipment.setDestination("Madrid");
		 shipment.setDepartureTime(departureTime);
		 shipment.setMaximumArriveTime(maximumArrivalTime);
		 shipment.setItemSize("L");
		 shipment.setPrice(20.0);
		 shipment.setItemName("Bolsa de deporte");
		 shipment.setItemPicture("https://www.elitefts.com/black-red-crescent-duffel-bag.html");
		 shipment.setItemEnvelope("Open");
		 
		 shipmentService.save(shipment);
		 
		 Integer numberOfShipmentAfter = shipmentService.findAll().size();

		 Assert.isTrue(numberOfShipmentAfter - numberOfShipmentBefore == 1);
		 
		 unauthenticate();
	 }
	 
	 /**
	  * @Test Edit an own shipment
	  * @result The shipment is edited.
	  */
	 @Test
	 public void positiveEditShipment1(){
		 
		 authenticate("user2");
		 
		 Shipment shipmentBefore = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		 
		 Assert.isTrue(shipmentBefore.getItemName().equals("Equipo de música"));
		 
		 shipmentBefore.setItemName("Nuevo Equipo de Música");
		 
		 shipmentService.save(shipmentBefore);
		 
		 Shipment shipmentAfter = shipmentService.findOne(shipmentBefore.getId());
		 
		 Assert.isTrue(shipmentAfter.getItemName().equals("Nuevo Equipo de Música"));
		 
		 unauthenticate();
	 }
	 
	 /**
	  * @Test Edit the dates of a shipment.
	  * @Return The shipment will be updated.
	  */
	 @Test()
	 public void positivestiveEditShipment2(){
		 
		 authenticate("user2");
		 
		 Shipment shipmentBefore = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));

		 Date departureTimeAfter = new GregorianCalendar(2018, Calendar.JULY, 01).getTime();
		 Date maximumArrivalTimeAfter = new GregorianCalendar(2018, Calendar.JULY, 02).getTime();
		 
		 shipmentBefore.setDepartureTime(departureTimeAfter);
		 shipmentBefore.setMaximumArriveTime(maximumArrivalTimeAfter);
		 
		 shipmentService.save(shipmentBefore);
		 
		 Shipment shipmentAfter = shipmentService.findOne(shipmentBefore.getId());
		 
		 Assert.isTrue(shipmentAfter.getDepartureTime().equals(departureTimeAfter));
		 Assert.isTrue(shipmentAfter.getMaximumArriveTime().equals(maximumArrivalTimeAfter));
		 
		 unauthenticate();
	 }
	 
	 /**
	  * @Test Edit an shipment been unauthenticated.
	  * @result The shipment will not be edited.
	  */
	 @Test(expected = IllegalArgumentException.class)
	 public void negativeEditShipment1(){
		 		 
		 Shipment shipmentBefore = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		 
		 Assert.isTrue(shipmentBefore.getItemName().equals("Equipo de música"));
		 
		 shipmentBefore.setItemName("Nuevo Equipo de Música");
		 
		 shipmentService.save(shipmentBefore);
		 
		 Shipment shipmentAfter = shipmentService.findOne(shipmentBefore.getId());
		 
		 Assert.isTrue(shipmentAfter.getItemName() == "Nuevo Equipo de Música");
		 
	 }
	 
	 /**
	  * @Test Edit the dates of a shipment. Dates are wrong.
	  * @Return The shipment will not be updated.
	  */
	 @Test(expected = IllegalArgumentException.class)
	 public void negativeEditShipment2(){
		 
		 authenticate("user2");
		 
		 Shipment shipmentBefore = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));

		 Date departureTimeAfter = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		 Date maximumArrivalTimeAfter = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		 
		 shipmentBefore.setDepartureTime(departureTimeAfter);
		 shipmentBefore.setMaximumArriveTime(maximumArrivalTimeAfter);
		 
		 shipmentService.save(shipmentBefore);
		 
		 Shipment shipmentAfter = shipmentService.findOne(shipmentBefore.getId());
		 
		 Assert.isTrue(shipmentAfter.getDepartureTime().equals(departureTimeAfter));
		 Assert.isTrue(shipmentAfter.getMaximumArriveTime().equals(maximumArrivalTimeAfter));
		 
		 unauthenticate();
	 }
	 
	 /**
	  * @Test A user deletes his own shipment.
	  * @return The shipment is deleted.
	  */
	 @Test
	 public void positiveDeleteShipment1(){
		 
		 authenticate("user2");
		 
		 Shipment shipmentBefore = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		 Integer numberOfShipmentBefore = shipmentService.findAll().size();
		 
		 shipmentService.delete(shipmentBefore);
		 		 
		 Integer numberOfShipmentAfter = shipmentService.findAll().size();
		 
		 Assert.isTrue(numberOfShipmentBefore - numberOfShipmentAfter == 1);
		 
		 unauthenticate();
	 }
	 
	 /**
	  * @Test A user deletes his own shipment while been unauthenticated.
	  * @return The shipment will not be deleted.
	  */
	 @Test(expected = IllegalArgumentException.class)
	 public void negativeDeleteShipment1(){
		 		 
		 Shipment shipmentBefore = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		 Integer numberOfShipmentBefore = shipmentService.findAll().size();
		 
		 shipmentService.delete(shipmentBefore);
		 		 
		 Integer numberOfShipmentAfter = shipmentService.findAll().size();
		 
		 Assert.isTrue(numberOfShipmentBefore - numberOfShipmentAfter == 1);
		 
	 }
	 
	 /**
	  * @Test A user tries to delete another user's shipment.
	  * @return The shipment will not be deleted.
	  */
	 @Test(expected = IllegalArgumentException.class)
	 public void negativeDeleteShipment2(){
		 
		 authenticate("user1");
		 
		 Shipment shipmentBefore = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		 Integer numberOfShipmentBefore = shipmentService.findAll().size();
		 
		 shipmentService.delete(shipmentBefore);
		 		 
		 Integer numberOfShipmentAfter = shipmentService.findAll().size();
		 
		 Assert.isTrue(numberOfShipmentBefore - numberOfShipmentAfter == 1);
		 
		 unauthenticate();
	 }
	
	 /**
	 * @Test Carry a Shipment
	 * @result The shipment is associated to the carrier
	 */
	@Test
	public void possitiveCarryShipmentTest() {
		authenticate("user1");
		Shipment shipment;
		ShipmentOffer shipmentOffer;

		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipment.getCarried() == null);

		shipmentOffer = shipmentService.carryShipment(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipmentOffer.getId() != 0);
		Assert.isTrue(shipmentOffer.getShipment().equals(shipment));

		unauthenticate();
	}

	/**
	 * @Test Carry a Shipment
	 * @result We create various offers from same user
	 */
	@Test
	public void possitiveCarryShipmentTest1() {
		authenticate("user1");
		Shipment shipment;
		ShipmentOffer shipmentOffer;
		ShipmentOffer shipmetOffer1;

		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipment.getCarried() == null);

		shipmentOffer = shipmentService.carryShipment(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipmentOffer.getId() != 0);
		Assert.isTrue(shipmentOffer.getShipment().equals(shipment));

		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipment.getCarried() == null);

		shipmetOffer1 = shipmentService.carryShipment(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipmetOffer1.getId() != 0);
		Assert.isTrue(shipmetOffer1.getShipment().equals(shipment));

		unauthenticate();
	}

	/**
	 * @Test Carry a Shipment
	 * @result We try to carry our own shipment
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCarryShipmentTest1() {
		authenticate("user2");
		Shipment shipment;
		ShipmentOffer shipmentOffer;

		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipment.getCarried() == null);

		shipmentOffer = shipmentService.carryShipment(UtilTest.getIdFromBeanName("shipment1"));
		Assert.isTrue(shipmentOffer.getId() != 0);
		Assert.isTrue(shipmentOffer.getShipment().equals(shipment));

		unauthenticate();
	}

	/**
	 * @Test Carry a Shipment
	 * @result We try to carry a Shipment that doesn't exists
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCarryShipmentTest2() {
		authenticate("user1");
		ShipmentOffer shipmentOffer;

		shipmentOffer = shipmentService.carryShipment(-200);
		Assert.isTrue(shipmentOffer.getId() != 0);

		unauthenticate();
	}

	/**
	 * @Test Carry a Shipment
	 * @result We try carry a Shipment that is already carried
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCarryShipmentTest3() {
		authenticate("user1");
		Shipment shipment;
		User user;
		ShipmentOffer shipmentOffer;
		
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		user = userService.findByPrincipal();
		shipment.setCarried(user);
		
		shipmentOffer = shipmentService.carryShipment(shipment.getId());
		Assert.isTrue(shipmentOffer.getId() != 0);

		unauthenticate();
	}
	
	/**
	 * @Test List all Shipments
	 * @result The shipments are list
	 */
	@Test
	public void positiveListShipment1() {
		authenticate("user1");
	
		Collection<Shipment> shipments;
		Shipment shipment, shipmentResult;
				
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment3"));
		shipments = shipmentService.searchShipment("Almeria", "Sevilla", "12/07/2017", "15:00", "Open", "M");
		shipmentResult = shipments.iterator().next();

		Assert.isTrue(shipments.size() == 1);
		Assert.isTrue(shipment.getId() == shipmentResult.getId());
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all Shipments
	 * @result The shipments are list
	 */
	@Test
	public void positiveListShipment2() {
		authenticate("user1");
	
		Collection<Shipment> shipments;
		Shipment shipment1, shipment2;
				
		shipment1 = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		shipment2 = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment2"));
		shipments = shipmentService.searchShipment("Almeria", "Sevilla", "12/03/2017", "15:00", "Open", null);

		for(Shipment s : shipments) {
			if(s.getId() != shipment1.getId() && s.getId() != shipment2.getId())
				Assert.isTrue(false);
		}
		
		Assert.isTrue(shipments.size() == 1);
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all Shipments
	 * @result The shipments are list
	 */
	@Test
	public void positiveListShipment3() {
		authenticate("user1");
	
		Collection<Shipment> shipments;
				
		shipments = shipmentService.searchShipment("Almeria", "Sevilla", "12/03/2017", "23:59", null, null);
		
		Assert.isTrue(shipments.isEmpty());
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all Shipments
	 * @result The user tries to search wihtout origin
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListShipment1() {
		authenticate("user1");
					
		shipmentService.searchShipment("", "Sevilla", "12/03/2017", "23:59", null, null);
				
		unauthenticate();
	}
	
	/**
	 * @Test List all Shipments
	 * @result The user tries to search wihtout destination
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListShipment2() {
		authenticate("user1");
					
		shipmentService.searchShipment("Almeria", "", "12/03/2017", "23:59", null, null);
				
		unauthenticate();
	}
	
	/**
	 * @Test View details of a shipment
	 * @result The user views details of a shipment
	 */
	@Test
	public void positiveViewDetailsShipment() {
		authenticate("user2");
		
		Shipment shipment;
		
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment3"));
		
		Assert.notNull(shipment);
	}
	
	/**
	 * @Test View details of a shipment
	 * @result The user cannot view details of a shipment because its Id is wrong
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeViewDetailsShipment() {
		authenticate("user2");
		
		Shipment shipment;
		
		shipment = shipmentService.findOne(935634865);
		
		Assert.notNull(shipment);
	}

	/**
	 * @Test Find all shipments by user
	 */
	@Test
	public void positiveFindAllShipmentsByUser() {
		Collection<Shipment> shipments;
		Page<Shipment> pages;
		Pageable pageable;
		User user;
		int count;
		
		authenticate("user2");
		
		user = userService.findByPrincipal();
		pageable = new PageRequest(1 - 1, 5);

		count = 0;
		pages = shipmentService.findAllByCurrentUser(pageable);
		shipments = pages.getContent();
		
		for(Shipment shipment : shipments) {
			Assert.isTrue(shipment.getCreator().getId() == user.getId());
		}
		
		for(Shipment shipment : shipmentService.findAll()) {
			if(shipment.getCreator().getId() == user.getId()) {
				count++;
			}
		}
		
		Assert.isTrue(count == shipments.size());
	}
}