package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

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
import repositories.ShipmentOfferRepository;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ShipmentOfferTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private ShipmentOfferService shipmentOfferService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ShipmentService shipmentService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ShipmentOfferRepository shipmentOfferRepository;

	// Test cases -------------------------------------------------------------

	/**
	 * @test A user creates an offer for a shipment.
	 * @result The shipmentOffer is created.
	 */
	@Test
	public void positiveCreateShipmentOffer1(){
		
		authenticate("user1");
		
		ShipmentOffer shipmentOffer;
		Shipment shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment3"));
		Integer numberOfShipmentOfferBefore = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(35.0);
		shipmentOffer.setDescription("Lo pido muy caro");
		shipmentOfferService.save(shipmentOffer);
		
		Integer numberOfShipmentOfferAfter = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		Assert.isTrue(numberOfShipmentOfferAfter - numberOfShipmentOfferBefore == 1);

		unauthenticate();
	}
	
	/**
	 * @Test Clone ShipmentOffer
	 * @result The shipmentOffer is cloned
	 */
	@Test
	public void positiveCloneShipmentOffer() {
		authenticate("user3");
		
		ShipmentOffer shipmentOffer;
		ShipmentOffer shipmentOfferCloned;
		Shipment shipment;
		
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		
		unauthenticate();
		authenticate("user1");
		
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(1);
		shipmentOffer.setDescription("Test");
		shipmentOffer = shipmentOfferService.save(shipmentOffer);
		
		shipmentOfferCloned = shipmentOfferService.createFromClone(shipmentOffer.getId());

		Assert.isTrue(shipmentOfferCloned.getAmount()==shipmentOffer.getAmount() && shipmentOfferCloned.getDescription().equals(shipmentOffer.getDescription()));
		
		unauthenticate();
	}
	
	/**
	 * @test A user creates an offer for a shipment while been unauthenticated.
	 * @result The shipmentOffer is not created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateShipmentOffer1(){
				
		ShipmentOffer shipmentOffer;
		Shipment shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment3"));
		Integer numberOfShipmentOfferBefore = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(35.0);
		shipmentOffer.setDescription("Lo pido muy caro");
		shipmentOfferService.save(shipmentOffer);
		
		Integer numberOfShipmentOfferAfter = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		Assert.isTrue(numberOfShipmentOfferAfter - numberOfShipmentOfferBefore == 1);

	}
	
	/**
	 * @test A user creates an offer for a shipment that belongs to him/herself.
	 * @result The shipmentOffer is not created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateShipmentOffer2(){
		
		authenticate("user2");
		
		ShipmentOffer shipmentOffer;
		Shipment shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment3"));
		Integer numberOfShipmentOfferBefore = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(35.0);
		shipmentOffer.setDescription("Lo pido muy caro");
		shipmentOfferService.save(shipmentOffer);
		
		Integer numberOfShipmentOfferAfter = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		Assert.isTrue(numberOfShipmentOfferAfter - numberOfShipmentOfferBefore == 1);

		unauthenticate();
	}
	
	/**
	 * @test A user creates an offer for a shipment with a negative amount.
	 * @result The shipmentOffer is not created.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void negativeCreateShipmentOffer3(){
		
		authenticate("user1");
		
		ShipmentOffer shipmentOffer;
		Shipment shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment3"));
		Integer numberOfShipmentOfferBefore = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(-35.0);
		shipmentOffer.setDescription("Lo pido muy caro");
		shipmentOfferService.save(shipmentOffer);
		
		Integer numberOfShipmentOfferAfter = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		Assert.isTrue(numberOfShipmentOfferAfter - numberOfShipmentOfferBefore == 1);

		unauthenticate();
	}
	
	/**
	 * @test A user creates an offer for a shipment with a past arrival time.
	 * @result The shipmentOffer is not created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateShipmentOffer4(){
		
		authenticate("user1");
		
		ShipmentOffer shipmentOffer;
		Shipment shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment2"));
		Integer numberOfShipmentOfferBefore = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(35.0);
		shipmentOffer.setDescription("Lo pido muy caro");
		shipmentOfferService.save(shipmentOffer);
		
		Integer numberOfShipmentOfferAfter = shipmentOfferService.findAllByShipmentId2(shipment.getId()).size();
		
		Assert.isTrue(numberOfShipmentOfferAfter - numberOfShipmentOfferBefore == 1);

		unauthenticate();
	}
	
	/**
	 * @Test List all shipment Offers
	 * @result The shipments are list
	 */
	@Test
	public void positiveListShipmentOffer1() {
		authenticate("user2");
	
		Shipment shipment;
		Page<ShipmentOffer> shipmentOffersPage;
		Collection<ShipmentOffer> shipmentOffers;
		
		Integer shipmentId;
		Integer userId;
		Pageable pageable = new PageRequest(1 - 1, 10);
		
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		
		shipmentId = shipment.getId();
		userId = 0;
		
		shipmentOffersPage = shipmentOfferService.findAllByOrShipmentIdAndOrUserId(shipmentId, userId, pageable);
		shipmentOffers = shipmentOfferService.findAllByShipmentId(shipmentId);
		
		Assert.isTrue(shipmentOffersPage.getNumberOfElements() == 	shipmentOffers.size());
		unauthenticate();
	}


	/**
	 * @Test List shipments Offers of a user
	 * @result The shipments of user are list
	 */
	@Test
	public void positiveListShipmentOffer2() {
		authenticate("user1");
	
		Shipment shipment;
		Page<ShipmentOffer> shipmentOffers;
		Integer shipmentId;
		Pageable pageable = new PageRequest(1 - 1, 5);
	
		User user1;
		Integer user1Id;
	
		
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		user1 = userService.findOne(UtilTest.getIdFromBeanName("user1"));
		
		shipmentId = shipment.getId();
		user1Id = user1.getId();
	
		shipmentOffers = shipmentOfferService.findAllByOrShipmentIdAndOrUserId(shipmentId, user1Id, pageable);
		int counter = 0;
		for(ShipmentOffer a:shipmentOfferRepository.findAll()){
			if(a.getUser().getId() == user1Id && a.getShipment().getId() == shipmentId){
				Assert.isTrue(shipmentOffers.getContent().contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(shipmentOffers.getNumberOfElements() == counter);
	
		unauthenticate();
		
	}
	

	/**
	 * @Test List shipments Offers of a user
	 * @result The shipments of user are list
	 */
	@Test
	public void positiveListShipmentOffer3() {
		authenticate("user1");
	
		Page<ShipmentOffer> shipmentOffers;
		Pageable pageable = new PageRequest(1 - 1, 100);
	
		User user1;
		Integer user1Id;
	
		
		user1 = userService.findOne(UtilTest.getIdFromBeanName("user1"));
		
		user1Id = user1.getId();
	
		shipmentOffers = shipmentOfferService.findAllByOrShipmentIdAndOrUserId(0, user1Id, pageable);
		int counter = 0;
		for(ShipmentOffer a:shipmentOfferRepository.findAll()){
			if(a.getUser().getId() == user1Id){
				Assert.isTrue(shipmentOffers.getContent().contains(a));
				counter++;
			}
		}
		
		Assert.isTrue(shipmentOffers.getNumberOfElements() == counter);
	
	}

	/**
	 * @Test List Routes Offers of a user
	 * @result The routes are not list because the user do not is logged.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListRouteOffer1() {
		authenticate("user2");
	
		Shipment shipment;
		Page<ShipmentOffer> shipmentOffers;
		Page<ShipmentOffer> shipmentOffersByUser;
		Integer shipmentId;
		Pageable pageable = new PageRequest(1 - 1, 5);
	
		User user1;
		Integer user1Id;
	
		
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		user1 = userService.findOne(UtilTest.getIdFromBeanName("user1"));
		
		shipmentId = shipment.getId();
		user1Id = user1.getId();
	
		shipmentOffers = shipmentOfferService.findAllByOrShipmentIdAndOrUserId(shipmentId, user1Id, pageable);
		shipmentOffersByUser = shipmentOfferService.findAllByOrShipmentIdAndOrUserId(0, user1Id, pageable);
		
		Assert.isTrue(shipmentOffers.getNumberOfElements() == shipmentOffersByUser.getNumberOfElements());
	
	
		unauthenticate();
		
	}

	/**
	 * @Test Accept a Shipment Offer
	 * @result The user accepts a shipment offer
	 */
	@Test
	public void shipmentOfferAcceptPositive1() {
		authenticate("user2");
		
		Shipment shipment;
		ShipmentOffer ro;
		
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer4"));
		
		shipmentOfferService.accept(ro.getId());
		
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedBySender() && !ro.getRejectedBySender());
		
		for(ShipmentOffer a:shipmentOfferService.findAllByShipmentId(shipment.getId())){
			if(a.getId() != ro.getId())
				Assert.isTrue(!a.getAcceptedBySender());
		}
	}
	
	/**
	 * @Test Accept a Shipment Offer
	 * @result The user tries to accept their own shipment offer
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferAcceptNegative1() {
		authenticate("user2");
		
		ShipmentOffer ro;
		
		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer3"));
		
		authenticate("user1");
		
		shipmentOfferService.accept(ro.getId());
		
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedBySender() && !ro.getRejectedBySender());
		
	}
	
	/**
	 * @Test Accept a Shipment Offer
	 * @result The user tries to accept an offer without being logged in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferAcceptNegative2() {
		authenticate("user2");
		
		ShipmentOffer ro;
		
		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer3"));
		
		unauthenticate();
		
		shipmentOfferService.accept(ro.getId());
		
		authenticate("user2");

		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedBySender() && !ro.getRejectedBySender());
	}
	
	/**
	 * @Test Accept a Shipment Offer
	 * @result The user tries to accept an offer that is rejected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferAcceptNegative3() {
		authenticate("user2");
		
		ShipmentOffer ro;
		
		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer2"));			
		shipmentOfferService.accept(ro.getId());
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedBySender() && !ro.getRejectedBySender());
	}
	
	/**
	 * @Test Accept a Shipment Offer
	 * @result The user tries to accept an offer that contain a shipmentOffer accepted
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferAcceptNegative4() {
		authenticate("user2");
		
		ShipmentOffer ro;
		
		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer6"));				
		shipmentOfferService.accept(ro.getId());
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedBySender() && !ro.getRejectedBySender());
	}
	
	
	/**
	 * @Test Deny a Shipment Offer
	 * @result The user denies a shipment offer
	 */
	@Test
	public void shipmentOfferDenyPositive1() {
		authenticate("user2");
		
		ShipmentOffer ro;

		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer3"));
		
		shipmentOfferService.deny(ro.getId());
		
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(!ro.getAcceptedBySender() && ro.getRejectedBySender());
	}
	
	
	/**
	 * @Test Deny a Shipment Offer
	 * @result The user tries to deny their own shipment offer
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferDenyNegative1() {
		authenticate("user1");
		
		ShipmentOffer ro;

		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer3"));
				
		shipmentOfferService.deny(ro.getId());
		
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(!ro.getAcceptedBySender() && ro.getRejectedBySender());
	}
	
	/**
	 * @Test Deny a Shipment Offer
	 * @result The user tries to deny an offer without being logged in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferDenyNegative2() {
		authenticate("user2");
		
		ShipmentOffer ro;

		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer3"));
		
		unauthenticate();
		
		shipmentOfferService.deny(ro.getId());
		
		authenticate("user2");
		
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(!ro.getAcceptedBySender() && ro.getRejectedBySender());
	}
	
	/**
	 * @Test Deny a Shipment Offer
	 * @result The user tries to deny an offer that is rejected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferDenyNegative3() {
		authenticate("user2");
		
		ShipmentOffer ro;
		
		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer2"));				
		shipmentOfferService.deny(ro.getId());
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(!ro.getAcceptedBySender() && ro.getRejectedBySender());
	}
	
	/**
	 * @Test Deny a Shipment Offer
	 * @result The user tries to deny an offer that is accepted
	 */
	@Test(expected = IllegalArgumentException.class)
	public void shipmentOfferDenyNegative4() {
		authenticate("user3");
		
		ShipmentOffer ro;
		
		ro = shipmentOfferService.findOne(UtilTest.getIdFromBeanName("shipmentOffer5"));				
		shipmentOfferService.deny(ro.getId());
		ro = shipmentOfferService.findOne(ro.getId());
		
		Assert.isTrue(!ro.getAcceptedBySender() && ro.getRejectedBySender());
	}
	

}

