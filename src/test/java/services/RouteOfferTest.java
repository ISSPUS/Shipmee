package services;

import java.util.Collection;

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

import domain.Route;
import domain.RouteOffer;
import domain.Shipment;
import domain.User;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RouteOfferTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private RouteOfferService routeOfferService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private RouteService routeService;

	@Autowired 
	private UserService userService;
	
	@Autowired
	private ShipmentService shipmentService;
	
	// Test cases -------------------------------------------------------------

	/**
	 * @Test Create a Route Offer
	 * @result The route has a new route offer
	 */
	@Test
	public void positiveCreateRouteOffer1() {
		authenticate("user3");
		
		RouteOffer result;
		Route route;
		Collection<RouteOffer> routeOffers;
		int sizePreCreate, sizePostCreate;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePreCreate = routeOffers.size();
		
		unauthenticate();
		authenticate("user2");
		
		result = routeOfferService.create(route.getId(), 0);
		result.setAmount(1);
		result.setDescription("Test");
		result = routeOfferService.save(result);
		
		unauthenticate();
		authenticate("user3");
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePostCreate = routeOffers.size();
		
		Assert.isTrue(sizePostCreate == sizePreCreate + 1);
		Assert.isTrue(routeOffers.contains(result));
		
		unauthenticate();
	}
	
	/**
	 * @Test Create a Route Offer
	 * @result The user tries to create a route offer with acceptedByCarrier to true
	 */
	@Test
	public void positiveCreateRouteOffer2() {
		authenticate("user3");
		
		RouteOffer result;
		Route route;
		Collection<RouteOffer> routeOffers;
		int sizePreCreate, sizePostCreate;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePreCreate = routeOffers.size();
		
		unauthenticate();
		authenticate("user2");
		
		result = routeOfferService.create(route.getId(), 0);
		result.setAmount(1);
		result.setDescription("Test");
		result.setAcceptedByCarrier(true);
		result = routeOfferService.save(result);
		
		unauthenticate();
		authenticate("user3");
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePostCreate = routeOffers.size();

		Assert.isTrue(result.getAcceptedByCarrier() == false);
		Assert.isTrue(sizePostCreate == sizePreCreate + 1);
		Assert.isTrue(routeOffers.contains(result));
		
		unauthenticate();
	}
	
	/**
	 * @Test Clone RouteOffer
	 * @result The routeOffer is cloned
	 */
	@Test
	public void positiveCloneRouteOffer() {
		authenticate("user3");
		
		RouteOffer routeOffer;
		RouteOffer routeOfferCloned;
		Route route;
		Shipment shipment;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		shipment = shipmentService.findOne(UtilTest.getIdFromBeanName("shipment1"));
		
		unauthenticate();
		authenticate("user2");
		
		routeOffer = routeOfferService.create(route.getId(), shipment.getId());
		routeOffer.setAmount(1);
		routeOffer.setDescription("Test");
		routeOffer.setAcceptedByCarrier(true);
		routeOffer = routeOfferService.save(routeOffer);
		
		routeOfferCloned = routeOfferService.createFromClone(routeOffer.getId());

		Assert.isTrue(routeOfferCloned.getAmount()==routeOffer.getAmount() && routeOfferCloned.getDescription().equals(routeOffer.getDescription()));
		
		unauthenticate();
	}
	
	/**
	 * @Test Create a Route Offer
	 * @result The user tries to create a route offer with other UserId
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateRouteOffer1() {
		authenticate("user1");
		
		RouteOffer result;
		Route route;
		Collection<RouteOffer> routeOffers;
		int sizePreCreate, sizePostCreate;
		User user;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePreCreate = routeOffers.size();
		user = userService.findOne(UtilTest.getIdFromBeanName("user3"));
		
		unauthenticate();
		authenticate("user2");
		
		result = routeOfferService.create(route.getId(), 0);
		result.setAmount(1);
		result.setDescription("Test");
		result.setUser(user);
		result = routeOfferService.save(result);
		
		unauthenticate();
		authenticate("user1");
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePostCreate = routeOffers.size();

		Assert.isTrue(sizePostCreate == sizePreCreate + 1);
		Assert.isTrue(routeOffers.contains(result));
		
		unauthenticate();
	}
	
	/**
	 * @Test Create a Route Offer
	 * @result The user tries to create an offer on their own route
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateRouteOffer2() {
		authenticate("user1");
		
		RouteOffer result;
		Route route;
		Collection<RouteOffer> routeOffers;
		int sizePreCreate, sizePostCreate;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePreCreate = routeOffers.size();		
		
		result = routeOfferService.create(route.getId(), 0);
		result.setAmount(1);
		result.setDescription("Test");
		result = routeOfferService.save(result);
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePostCreate = routeOffers.size();

		Assert.isTrue(sizePostCreate == sizePreCreate + 1);
		Assert.isTrue(routeOffers.contains(result));
		
		unauthenticate();
	}
	
	/**
	 * @Test Create a Route Offer
	 * @result The user tries to create an offer without being logged in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateRouteOffer3() {
		authenticate("user3");
		
		RouteOffer result;
		Route route;
		Collection<RouteOffer> routeOffers;
		int sizePreCreate, sizePostCreate;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePreCreate = routeOffers.size();
		
		unauthenticate();
		
		result = routeOfferService.create(route.getId(), 0);
		result.setAmount(1);
		result.setDescription("Test");
		result = routeOfferService.save(result);
		
		authenticate("user3");
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		sizePostCreate = routeOffers.size();

		Assert.isTrue(sizePostCreate == sizePreCreate + 1);
		Assert.isTrue(routeOffers.contains(result));
		
		unauthenticate();
	}
	
	/**
	 * @Test Accept a Route Offer
	 * @result The user accepts a route offer
	 */
	@Test
	public void positiveAcceptRouteOffer1() {
		authenticate("user2");
		
		Route route;
		RouteOffer ro;
		Collection<RouteOffer> routeOffers;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		ro = routeOffers.iterator().next();
		
		routeOfferService.accept(ro.getId());
		
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == true && ro.getRejectedByCarrier() == false);
		
		unauthenticate();
	}
	
	/**
	 * @Test Accept a Route Offer
	 * @result The user tries to accept their own route offer
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeAcceptRouteOffer1() {
		authenticate("user2");
		
		Route route;
		RouteOffer ro;
		Collection<RouteOffer> routeOffers;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		ro = routeOffers.iterator().next();
		
		unauthenticate();
		authenticate("user1");
		
		routeOfferService.accept(ro.getId());
		
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == true && ro.getRejectedByCarrier() == false);
		
		unauthenticate();
	}
	
	/**
	 * @Test Accept a Route Offer
	 * @result The user tries to accept an offer without being logged in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeAcceptRouteOffer2() {
		authenticate("user2");
		
		Route route;
		RouteOffer ro;
		Collection<RouteOffer> routeOffers;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		ro = routeOffers.iterator().next();
		
		unauthenticate();
		
		routeOfferService.accept(ro.getId());
		
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == true && ro.getRejectedByCarrier() == false);
		
	}
	
	/**
	 * @Test Accept a Route Offer
	 * @result The user tries to accept an offer that is rejected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeAcceptRouteOffer3() {
		authenticate("user2");
		
		RouteOffer ro;
		
		ro = routeOfferService.findOne(UtilTest.getIdFromBeanName("routeOffer7"));	
		routeOfferService.accept(ro.getId());
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == true && ro.getRejectedByCarrier() == false);
		
		unauthenticate();
	}
	
	/**
	 * @Test Accept a Route Offer
	 * @result The user tries to accept an offer that is accepted
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeAcceptRouteOffer4() {
		authenticate("user2");
		
		RouteOffer ro;
		
		ro = routeOfferService.findOne(UtilTest.getIdFromBeanName("routeOffer8"));	
		routeOfferService.accept(ro.getId());
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == true && ro.getRejectedByCarrier() == false);
		
		unauthenticate();
	}
	
	/**
	 * @Test Deny a Route Offer
	 * @result The user denies a route offer
	 */
	@Test
	public void positiveDenyRouteOffer1() {
		authenticate("user2");
		
		Route route;
		RouteOffer ro;
		Collection<RouteOffer> routeOffers;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		ro = routeOffers.iterator().next();
		
		routeOfferService.deny(ro.getId());
		
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == false && ro.getRejectedByCarrier() == true);
		
		unauthenticate();
	}
	
	/**
	 * @Test Deny a Route Offer
	 * @result The user tries to deny their own route offer
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeDenyRouteOffer1() {
		authenticate("user1");
		
		Route route;
		RouteOffer ro;
		Collection<RouteOffer> routeOffers;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		ro = routeOffers.iterator().next();
		
		unauthenticate();
		authenticate("user2");
		
		routeOfferService.deny(ro.getId());
		
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == false && ro.getRejectedByCarrier() == true);
		
		unauthenticate();
	}
	
	/**
	 * @Test Deny a Route Offer
	 * @result The user tries to deny an offer without being logged in
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeDenyRouteOffer2() {
		authenticate("user1");
		
		Route route;
		RouteOffer ro;
		Collection<RouteOffer> routeOffers;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routeOffers = routeOfferService.findAllByRouteId(route.getId());
		ro = routeOffers.iterator().next();
		
		unauthenticate();
		
		routeOfferService.deny(ro.getId());
		
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == false && ro.getRejectedByCarrier() == true);
		
	}
	
	/**
	 * @Test Deny a Route Offer
	 * @result The user tries to deny an offer that is rejected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeDenyRouteOffer3() {
		authenticate("user1");
		
		RouteOffer ro;
		
		ro = routeOfferService.findOne(UtilTest.getIdFromBeanName("routeOffer3"));				
		routeOfferService.deny(ro.getId());
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == false && ro.getRejectedByCarrier() == true);
		
		unauthenticate();
	}
	
	/**
	 * @Test Deny a Route Offer
	 * @result The user tries to deny an offer that is accepted
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeDenyRouteOffer4() {
		authenticate("user1");
		
		RouteOffer ro;
		
		ro = routeOfferService.findOne(UtilTest.getIdFromBeanName("routeOffer4"));				
		routeOfferService.deny(ro.getId());
		ro = routeOfferService.findOne(ro.getId());
		
		Assert.isTrue(ro.getAcceptedByCarrier() == false && ro.getRejectedByCarrier() == true);
		
		unauthenticate();
	}
	
	
	/**
	 * @Test List all Routes Offers
	 * @result The routes are list
	 */
	@Test
	public void positiveListRouteOffer1() {
		authenticate("user1");
	
		Route route;
		Page<RouteOffer> routeOffersPage;
		Collection<RouteOffer> routeOffers;
		
		Integer routeId;
		Integer userId;
		Pageable pageable = new PageRequest(1 - 1, 10);
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		
		routeId = route.getId();
		userId = 0;
		
		routeOffersPage = routeOfferService.findAllByOrRouteIdAndOrUserId(routeId, userId, pageable);
		routeOffers = routeOfferService.findAllByRouteId(routeId);
		
		Assert.isTrue(routeOffersPage.getNumberOfElements() == routeOffers.size());
		unauthenticate();
	}
	
	
	/**
	 * @Test List Routes Offers of a user
	 * @result The routes of user are list
	 */
	@Test
	public void positiveListRouteOffer2() {
		authenticate("user2");
	
		Page<RouteOffer> routeOffersByUser;
		Pageable pageable = new PageRequest(1 - 1, 5);

		User user2;
		Integer user2Id;
		int cont = 0;
		
		user2 = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		user2Id = user2.getId();
		routeOffersByUser = routeOfferService.findAllByOrRouteIdAndOrUserId(0, user2Id, pageable);
		
		for(RouteOffer r: routeOfferService.findAll()){
			if(r.getUser().equals(user2)){
				cont++;
			}
		}
		
		Assert.isTrue(cont == routeOffersByUser.getNumberOfElements());

		unauthenticate();
		
	}
	
	
	/**
	 * @Test List Routes Offers of a user
	 * @result The routes are not list because the user do not is logged.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListRouteOffer1() {
		authenticate("user1");
	
		Route route;
		Page<RouteOffer> routeOffers;
		Page<RouteOffer> routeOffersByUser;
		Integer routeId;
		Pageable pageable = new PageRequest(1 - 1, 5);

		User user2;
		Integer user2Id;

		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		user2 = userService.findOne(UtilTest.getIdFromBeanName("user2"));
		
		routeId = route.getId();
		user2Id = user2.getId();

		routeOffers = routeOfferService.findAllByOrRouteIdAndOrUserId(routeId, user2Id, pageable);
		routeOffersByUser = routeOfferService.findAllByOrRouteIdAndOrUserId(0, user2Id, pageable);
		
		Assert.isTrue(routeOffers.getNumberOfElements() == routeOffersByUser.getNumberOfElements());

		unauthenticate();
		
	}
	

}