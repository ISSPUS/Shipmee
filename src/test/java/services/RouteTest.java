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

import domain.Alert;
import domain.Message;
import domain.Route;
import domain.RouteOffer;
import domain.User;
import domain.Vehicle;
import repositories.AlertRepository;
import repositories.MessageRepository;
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

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private AlertRepository alertRepository;

	// Test cases -------------------------------------------------------------

	/**
	 * @test 
	 */
	@Test
	public void positiveCreateRoute1(){
		
		authenticate("user2");
		
		Integer numberOfRoutesBefore = routeService.findAll().size();
		
		Collection<Message> beforeMessages;
		Collection<Message> newsMessages;
		boolean alertFound;
		Alert alert;
		Route route;
		Date departureTime = new GregorianCalendar(2018, Calendar.JULY, 01).getTime();
		Date arrivalTime = new GregorianCalendar(2018, Calendar.JULY, 02).getTime();
		Pageable page = new PageRequest(0, Integer.MAX_VALUE);
		Vehicle vehicle = vehicleService.findAllNotDeletedByUser(page).getContent().iterator().next();
		beforeMessages = messageRepository.findAll();
		
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
		Assert.isTrue(beforeMessages.size() != messageRepository.findAll().size());
		
		newsMessages = messageRepository.findAll();
		alert = alertRepository.findOne(UtilTest.getIdFromBeanName("alert3"));
		
		newsMessages.removeAll(beforeMessages);
		alertFound = false;
		
		for(Message m:newsMessages){
			if(m.getRecipient().getId() == alert.getUser().getId()){
				Assert.isTrue(m.getBody().contains(alert.getOrigin()));
				Assert.isTrue(m.getBody().contains(alert.getDestination()));
				alertFound = true;
			}
		}
		
		Assert.isTrue(alertFound);
		
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
		Date departureTime = new GregorianCalendar(2018, Calendar.JULY, 01).getTime();
		Date arrivalTime = new GregorianCalendar(2018, Calendar.JULY, 02).getTime();
		Pageable page = new PageRequest(0, Integer.MAX_VALUE);
		Vehicle vehicle = vehicleService.findAllNotDeletedByUser(page).getContent().iterator().next();
		
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
		Date departureTime = new GregorianCalendar(2018, Calendar.JULY, 02).getTime();
		Date arrivalTime = new GregorianCalendar(2018, Calendar.JULY, 01).getTime();
		Pageable page = new PageRequest(0, Integer.MAX_VALUE);
		Vehicle vehicle = vehicleService.findAllNotDeletedByUser(page).getContent().iterator().next();
		
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
		Date departureTime = new GregorianCalendar(2018, Calendar.JULY, 01).getTime();
		Date arrivalTime = new GregorianCalendar(2018, Calendar.JULY, 02).getTime();
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
		
		authenticate("user1");
		
		Route routeBefore =  routeService.findOne(UtilTest.getIdFromBeanName("route8"));
		
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
		
		authenticate("user1");
		
		Route routeBefore =  routeService.findOne(UtilTest.getIdFromBeanName("route8"));
		Date arrivalTimeAfter = new GregorianCalendar(2019, Calendar.JULY, 02).getTime();
		
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
		
		authenticate("user1");
		
		Route routeBefore = routeService.findOne(UtilTest.getIdFromBeanName("route8"));
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
	
		Page<Route> routes;
		Route route, routeResult;
		Pageable pageable;

		pageable = new PageRequest(0, 5);		
		route = routeService.findOne(UtilTest.getIdFromBeanName("route1"));
		routes = routeService.searchRoute("Almeria", "Sevilla", "12/03/2017", "15:00", "Both", "M",pageable);
		if(routes.iterator().hasNext())
			routeResult = routes.iterator().next();
		else
			routeResult = new Route();

		Assert.isTrue(routes.getContent().size() == 0);
		Assert.isTrue(route.getId() != routeResult.getId());
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The routes are list
	 */
	@Test
	public void positiveListRoute2() {
		authenticate("user1");
	
		Page<Route> routes;
		Route route1, route2;
		Pageable pageable;

		pageable = new PageRequest(0, 5);		
		route1 = routeService.findOne(UtilTest.getIdFromBeanName("route2"));
		route2 = routeService.findOne(UtilTest.getIdFromBeanName("route3"));
		routes = routeService.searchRoute("Almeria", "Sevilla", "12/07/2018", "15:00", "Both", "M",pageable);

		for(Route r : routes) {
			if(r.getId() != route1.getId() && r.getId() != route2.getId())
				Assert.isTrue(false);
		}
		
		Assert.isTrue(routes.getContent().size() == 2);
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The routes are list
	 */
	@Test
	public void positiveListRoute3() {
		authenticate("user1");
	
		Page<Route> routes;
		Pageable pageable;

		pageable = new PageRequest(0, 5);				
		routes = routeService.searchRoute("Almeria", "Sevilla", "12/03/2017", "23:59", null, null,pageable);
		
		Assert.isTrue(routes.getContent().isEmpty());
		
		
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The user tries to search wihtout origin
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListRoute1() {
		authenticate("user1");
		Pageable pageable;

		pageable = new PageRequest(0, 5);
		routeService.searchRoute("", "Sevilla", "12/03/2017", "23:59", null, null,pageable);
				
		unauthenticate();
	}
	
	/**
	 * @Test List all routes
	 * @result The user tries to search wihtout destination
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeListRoute2() {
		authenticate("user1");
		Pageable pageable;

		pageable = new PageRequest(0, 5);			
		routeService.searchRoute("Almeria", "", "12/03/2017", "23:59", null, null,pageable);
				
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
	
	/**
	 * @Test Find all routes by user
	 */
	@Test
	public void positiveFindAllRoutesByUser() {
		Collection<Route> routes;
		Page<Route> pages;
		Pageable pageable;
		User user;
		int count;
		
		authenticate("user1");
		
		user = userService.findByPrincipal();
		pageable = new PageRequest(1 - 1, 5);

		count = 0;
		pages = routeService.findAllByCurrentUser(pageable);
		routes = pages.getContent();
		
		for(Route route : routes) {
			Assert.isTrue(route.getCreator().getId() == user.getId());
		}
		
		for(Route route : routeService.findAll()) {
			if(route.getCreator().getId() == user.getId()) {
				count++;
			}
		}
		
		Assert.isTrue(count == routes.size());
	}

}