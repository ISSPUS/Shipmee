package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	 * @result We create various offers from different users
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
		
		Assert.isTrue(shipments.size() == 2);
		
		
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
					
		shipmentService.searchShipment(null, "Sevilla", "12/03/2017", "23:59", null, null);
				
		unauthenticate();
	}
	
	/**
	 * @Test List all Shipments
	 * @result The user tries to search wihtout destination
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListShipment2() {
		authenticate("user1");
					
		shipmentService.searchShipment("Almeria", null, "12/03/2017", "23:59", null, null);
				
		unauthenticate();
	}

}