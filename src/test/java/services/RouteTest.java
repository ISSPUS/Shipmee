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

import domain.Route;
import domain.RouteOffer;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/junit.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RouteTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private RouteService routeService;

	// Supporting services ----------------------------------------------------


	// Test cases -------------------------------------------------------------

	
	/**
	 * @Test List all routes
	 * @result The routes are list
	 */
	@Test
	public void positiveListRoute1() {
		authenticate("user1");
	
		Collection<Route> routes;
		Route route, routeResult;
				
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routes = routeService.searchRoute("Almeria", "Sevilla", "12/03/2017", "15:00", "Both", "M");
		routeResult = routes.iterator().next();

		Assert.isTrue(routes.size() == 1);
		Assert.isTrue(route.getId() == routeResult.getId());
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The routes are list
	 */
	@Test
	public void positiveListRoute2() {
		authenticate("user1");
	
		Collection<Route> routes;
		Route route1, route2;
				
		route1 = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		route2 = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routes = routeService.searchRoute("Almeria", "Sevilla", "12/07/2017", "15:00", "Both", "M");

		for(Route r : routes) {
			if(r.getId() != route1.getId() && r.getId() != route2.getId())
				Assert.isTrue(false);
		}
		
		Assert.isTrue(routes.size() == 2);
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The routes are list
	 */
	@Test
	public void positiveListRoute3() {
		authenticate("user1");
	
		Collection<Route> routes;
				
		routes = routeService.searchRoute("Almeria", "Sevilla", "12/03/2017", "23:59", null, null);
		
		Assert.isTrue(routes.isEmpty());
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The user tries to search wihtout origin
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListRoute1() {
		authenticate("user1");
					
		routeService.searchRoute("", "Sevilla", "12/03/2017", "23:59", null, null);
				
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The user tries to search wihtout destination
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListRoute2() {
		authenticate("user1");
					
		routeService.searchRoute("Almeria", "", "12/03/2017", "23:59", null, null);
				
		unauthenticate();
	}
	
	/**
	 * @Test Contract a Route
	 * @result The router is associated to the contractor
	 */
	@Test
	public void possitiveContractRouteTest() {
		authenticate("user1");
		Route route;
		RouteOffer routeOffer;

		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));

		routeOffer = routeService.contractRoute(UtilTest.getIdFromBeanName("route2"), UtilTest.getIdFromBeanName("sizePrice5"));
		Assert.isTrue(routeOffer.getId() != 0);
		Assert.isTrue(routeOffer.getRoute().equals(route));

		unauthenticate();
	}

	/**
	 * @Test Contract a Route
	 * @result We create various offers from different users
	 */
	@Test
	public void possitiveContractRouteTest1() {
		authenticate("user1");
		Route route;
		RouteOffer routeOffer;
		RouteOffer routeOffer1;

		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));

		routeOffer = routeService.contractRoute(UtilTest.getIdFromBeanName("route2"), UtilTest.getIdFromBeanName("sizePrice5"));
		Assert.isTrue(routeOffer.getId() != 0);
		Assert.isTrue(routeOffer.getRoute().equals(route));

		unauthenticate();
		authenticate("user3");

		routeOffer1 = routeService.contractRoute(UtilTest.getIdFromBeanName("route2"), UtilTest.getIdFromBeanName("sizePrice6"));
		Assert.isTrue(routeOffer1.getId() != 0);
		Assert.isTrue(routeOffer1.getRoute().equals(route));

		unauthenticate();
	}

	/**
	 * @Test Contract a Route
	 * @result We try to contract our own route
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeContractRouteTest1() {
		authenticate("user2");
		Route route;
		RouteOffer routeOffer;

		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));

		routeOffer = routeService.contractRoute(UtilTest.getIdFromBeanName("route2"), UtilTest.getIdFromBeanName("sizePrice5"));
		Assert.isTrue(routeOffer.getId() != 0);
		Assert.isTrue(routeOffer.getRoute().equals(route));

		unauthenticate();
	}

	/**
	 * @Test Contract a Route
	 * @result We try to contract a Route that doesn't exists
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeContractRouteTest2() {
		authenticate("user1");
		RouteOffer routeOffer;

		routeOffer = routeService.contractRoute(-200, UtilTest.getIdFromBeanName("sizePrice5"));
		Assert.isTrue(routeOffer.getId() != 0);

		unauthenticate();
	}

	/**
	 * @Test Contract a Route
	 * @result We try contract a Route that is earlier than the current
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeContractRouteTest3() {
		authenticate("user1");
		Route route;
		RouteOffer routeOffer;

		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));

		routeOffer = routeService.contractRoute(UtilTest.getIdFromBeanName("route1"), UtilTest.getIdFromBeanName("sizePrice1"));
		Assert.isTrue(routeOffer.getId() != 0);
		Assert.isTrue(routeOffer.getRoute().equals(route));

		unauthenticate();
	}

}