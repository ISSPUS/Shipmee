package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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
import domain.Vehicle;
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
	
	@Autowired
	private VehicleService vehicleService;

	// Supporting services ----------------------------------------------------


	// Test cases -------------------------------------------------------------

	/**
	 * @test 
	 */
	@Test
	public void positiveCreateRoute1(){
		
		authenticate("user2");
		
		Integer numberOfRoutesBefore = routeService.findAll().size();
		
		Route route;
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		Date arrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		Vehicle vehicle = vehicleService.findAllNotDeletedByUser().iterator().next();
		
		route = routeService.create();
		route.setOrigin("Sevilla");
		route.setDestination("Madrid");
		route.setDepartureTime(departureTime);
		route.setArriveTime(arrivalTime);
		route.setItemEnvelope("Open");
		route.setVehicle(vehicle);
		
		routeService.save(route);
		
		Integer numberOfRoutesAfter = routeService.findAll().size();
		
		Assert.isTrue(numberOfRoutesAfter - numberOfRoutesBefore == 1);
		
		unauthenticate();
	}
	
	/**
	 * @test Create a route while been unauthenticated.
	 * @result The route is not created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateShipment1(){
				
		Integer numberOfRoutesBefore = routeService.findAll().size();
		
		Route route;
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		Date arrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		Vehicle vehicle = vehicleService.findAllNotDeletedByUser().iterator().next();
		
		route = routeService.create();
		route.setOrigin("Sevilla");
		route.setDestination("Madrid");
		route.setDepartureTime(departureTime);
		route.setArriveTime(arrivalTime);
		route.setItemEnvelope("Open");
		route.setVehicle(vehicle);
		
		routeService.save(route);
		
		Integer numberOfRoutesAfter = routeService.findAll().size();
		
		Assert.isTrue(numberOfRoutesAfter - numberOfRoutesBefore == 1);
		
	}
	
	/**
	 * @test Create a route with a MaximumArrivalTime before the DepartureTime.
	 * @result The route is not created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateShipment2(){
			
		authenticate("user1");
		
		Integer numberOfRoutesBefore = routeService.findAll().size();
		
		Route route;
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		Date arrivalTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		Vehicle vehicle = vehicleService.findAllNotDeletedByUser().iterator().next();
		
		route = routeService.create();
		route.setOrigin("Sevilla");
		route.setDestination("Madrid");
		route.setDepartureTime(departureTime);
		route.setArriveTime(arrivalTime);
		route.setItemEnvelope("Open");
		route.setVehicle(vehicle);
		
		routeService.save(route);
		
		Integer numberOfRoutesAfter = routeService.findAll().size();
		
		Assert.isTrue(numberOfRoutesAfter - numberOfRoutesBefore == 1);
		
		unauthenticate();
	}
	
	/**
	 * @test Create a route with a vehicle that it's not yours.
	 * @return The route is not created.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateRoute3(){
		
		authenticate("user2");
		
		Integer numberOfRoutesBefore = routeService.findAll().size();
		
		Route route;
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		Date arrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		Vehicle vehicle = vehicleService.findOne(UtilTest.getIdFromBeanName("vehicle1"));
		
		route = routeService.create();
		route.setOrigin("Sevilla");
		route.setDestination("Madrid");
		route.setDepartureTime(departureTime);
		route.setArriveTime(arrivalTime);
		route.setItemEnvelope("Open");
		route.setVehicle(vehicle);
		
		routeService.save(route);
		
		Integer numberOfRoutesAfter = routeService.findAll().size();
		
		Assert.isTrue(numberOfRoutesAfter - numberOfRoutesBefore == 1);
		
		unauthenticate();
	}
	
	/**
	 * @test Edit an own route
	 * @return The route is edited.
	 */
	@Test
	public void positiveEditShipment1(){
		
		authenticate("user2");
		
		Route routeBefore =  routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		
		routeBefore.setOrigin("Valencia");
		
		routeService.save(routeBefore);
		
		Route routeAfter = routeService.findOne(routeBefore.getId());
		
		Assert.isTrue(routeAfter.getOrigin().equals("Valencia"));
		
		unauthenticate();
	}
	
	/**
	 * @test Edit an own route
	 * @return The route is edited.
	 */
	@Test
	public void positiveEditShipment2(){
		
		authenticate("user2");
		
		Route routeBefore =  routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		Date arrivalTimeAfter = new GregorianCalendar(2018, Calendar.JULY, 02).getTime();
		
		routeBefore.setArriveTime(arrivalTimeAfter);
		
		routeService.save(routeBefore);
		
		Route routeAfter = routeService.findOne(routeBefore.getId());
		
		Assert.isTrue(routeAfter.getArriveTime().equals(arrivalTimeAfter));
		
		unauthenticate();
	}
	
	/**
	 * @test Edit an route while been unauthenticated
	 * @return The route is edited.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeEditShipment1(){
				
		Route routeBefore =  routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		
		routeBefore.setOrigin("Valencia");
		
		routeService.save(routeBefore);
		
		Route routeAfter = routeService.findOne(routeBefore.getId());
		
		Assert.isTrue(routeAfter.getOrigin().equals("Valencia"));
		
	}
	
	/**
	 * @test Edit an route that belongs to another user
	 * @return The route is edited.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeEditShipment2(){
		
		authenticate("user3");
		
		Route routeBefore =  routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		
		routeBefore.setOrigin("Valencia");
		
		routeService.save(routeBefore);
		
		Route routeAfter = routeService.findOne(routeBefore.getId());
		
		Assert.isTrue(routeAfter.getOrigin().equals("Valencia"));
		
		unauthenticate();
	}
	
	/**
	 * @test A user tries to delete his own routes.
	 * @return The route is deleted
	 */
	@Test
	public void positiveDeleteRoute1(){
		
		authenticate("user2");
		
		Route routeBefore = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		Integer numberOfRouteBefore = routeService.findAll().size();
		
		routeService.delete(routeBefore);
		
		Integer numberOfRouteAfter = routeService.findAll().size();

		Assert.isTrue(numberOfRouteBefore - numberOfRouteAfter == 1);
		
		unauthenticate();
	}
	
	/**
	 * @test A user tries to delete his own routes while been unauthenticated.
	 * @return The route is not deleted
	 */
	@Test(expected = IllegalArgumentException.class)
	public void deleteDeleteRoute1(){
				
		Route routeBefore = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		Integer numberOfRouteBefore = routeService.findAll().size();
		
		routeService.delete(routeBefore);
		
		Integer numberOfRouteAfter = routeService.findAll().size();

		Assert.isTrue(numberOfRouteBefore - numberOfRouteAfter == 1);
		
	}
	
	/**
	 * @test A user tries to delete a route that he/she does not belong.
	 * @return The route is not deleted
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeDeleteRoute2(){
		
		authenticate("user3");
		
		Route routeBefore = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		Integer numberOfRouteBefore = routeService.findAll().size();
		
		routeService.delete(routeBefore);
		
		Integer numberOfRouteAfter = routeService.findAll().size();

		Assert.isTrue(numberOfRouteBefore - numberOfRouteAfter == 1);
		
		unauthenticate();
	}
	
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
	
	/**
	 * @Test View details of a route
	 * @result The user views the details of a route
	 */
	@Test
	public void positiveViewDetailsShipment() {
		authenticate("user2");
		Route route;
		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		route.getOrigin().equals("Almeria");
		route.getDestination().equals("Sevilla");
		route.getItemEnvelope().equals("Both");
		
		Assert.notNull(route);
	}
	
	/**
	 * @Test View details of a shipment
	 * @result We try view the details of a Route that not exists
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeViewDetailsShipment() {
		authenticate("user2");
		Route route;
		
		route = routeService.findOne(-200);
		
		Assert.notNull(route);
	}

}