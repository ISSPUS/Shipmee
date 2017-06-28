package services;


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

import domain.Vehicle;
import utilities.AbstractTest;
import utilities.UtilTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class VehicleTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private VehicleService vehicleService;

	// Supporting services ----------------------------------------------------

	// Test cases -------------------------------------------------------------

	/**
	 * @test List your own Vehicles
	 * @result The vehicles listed are correct
	 */
	@Test
	public void positiveListVehicle() {
		authenticate("user2");

		Page<Vehicle> vehicles;
		Pageable page = new PageRequest(0, Integer.MAX_VALUE);

		vehicles = vehicleService.findAllNotDeletedByUser(page);

		Assert.isTrue(vehicles.getContent().size() == 1);

		unauthenticate();
	}

	/**
	 * @Test Create a Vehicle
	 * @result The vehicle is created and persisted into database
	 */
	@Test
	public void positiveCreateVehicle() {
		authenticate("user2");

		Vehicle vehicle;

		vehicle = vehicleService.create();
		vehicle.setBrand("Audi");
		vehicle.setModel("A4");
		vehicle.setColor("Red");
		vehicle.setPicture("http://example.com/");
		vehicle = vehicleService.save(vehicle);

		Assert.isTrue(vehicle.getId() != 0 && vehicleService.findOne(vehicle.getId()).getBrand().equals("Audi")
				&& vehicleService.findOne(vehicle.getId()).getModel().equals("A4"));

		unauthenticate();
	}

	/**
	 * @test Modify a Vehicle
	 * @result The vehicle is modified and persisted into database
	 */
	@Test
	public void positiveEditVehicle() {
		authenticate("user2");

		Vehicle vehicle;

		vehicle = vehicleService.findOne(UtilTest.getIdFromBeanName("vehicle2"));

		Assert.isTrue(vehicle.getColor().equals("Gray"));

		vehicle.setColor("White");
		vehicle = vehicleService.save(vehicle);

		Assert.isTrue(vehicle.getColor().equals("White"));

		unauthenticate();
	}

	/**
	 * @test Delete a Vehicle
	 * @result The vehicle is deleted into database
	 */
	@Test
	public void positiveDeleteVehicle() {
		authenticate("user2");

		Vehicle vehicle;

		vehicle = vehicleService.findOne(UtilTest.getIdFromBeanName("vehicle2"));

		vehicleService.delete(vehicle);

		unauthenticate();
	}

	/**
	 * @test Create a Vehicle
	 * @result We try to create a Vehicle without logged User so
	 *         <code>IllegalArgumentException</code> is expected
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreateVehicle() {
		Vehicle vehicle;

		vehicle = vehicleService.create();
		vehicle.setBrand("Audi");
		vehicle.setModel("A4");
		vehicle.setColor("Red");
		vehicle.setPicture("http://example.com/");
		vehicle = vehicleService.save(vehicle);

		Assert.isTrue(vehicle.getId() != 0 && vehicleService.findOne(vehicle.getId()).getBrand().equals("Audi")
				&& vehicleService.findOne(vehicle.getId()).getModel().equals("A4"));
	}
}