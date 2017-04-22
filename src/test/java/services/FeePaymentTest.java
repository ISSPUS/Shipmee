package services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.FeePayment;
import domain.Shipment;
import domain.ShipmentOffer;
import domain.form.FeePaymentForm;
import services.form.FeePaymentFormService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class FeePaymentTest extends AbstractTest {

	// Service to test --------------------------------------------------------

	@Autowired
	private FeePaymentService feePaymentService;
	
	@Autowired
	private FeePaymentFormService feePaymentFormService;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private ShipmentService shipmentService;
	
	@Autowired
	private ShipmentOfferService shipmentOfferService;
	
	// Test cases -------------------------------------------------------------

	/**
	 * Create a payment about a Shipment Offer and Accept it.
	 */
	@Test
	public void positiveCreatePaymentShipmentOffer1(){
		
		Pageable page = new PageRequest(0, 10);
		
		Shipment shipment;
		
		Integer numberOfPaymentsBefore;
		Integer numberOfPaymentsAfter;
		Integer numberOfPaymentPendingBefore;
		Integer numberOfPaymentPendingAfter;
		Integer numberOfPaymentAcceptedBefore;
		Integer numberOfPaymentAcceptedAfter;
		
		numberOfPaymentsBefore = feePaymentService.findAll().size();
		
		authenticate("user1");
		
		numberOfPaymentPendingBefore = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		numberOfPaymentAcceptedBefore = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		shipment = shipmentService.create();
		shipment.setOrigin("Sevilla");
		shipment.setDestination("Lugo");
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		shipment.setDepartureTime(departureTime);
		Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		shipment.setMaximumArriveTime(maximumArrivalTime);
		shipment.setItemSize("L");
		shipment.setItemName("Prueba");
		shipment.setPrice(10.0);
		shipment.setItemPicture("https://cde.3.elcomercio.pe/ima/0/1/0/2/1/1021917.jpg");
		shipment.setItemEnvelope("Open");
		shipment = shipmentService.save(shipment);
		
		unauthenticate();
		
		ShipmentOffer shipmentOffer;
		
		authenticate("user2");
		
		shipment = shipmentService.findOne(shipment.getId());
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(5.0);
		shipmentOffer.setDescription("Mensaje de prueba");
		shipmentOffer = shipmentOfferService.save(shipmentOffer);
		
		unauthenticate();
		
		FeePaymentForm feePaymentForm;
		FeePayment payment;
		CreditCard creditCard;
		
		authenticate("user1");
		
		shipmentOffer = shipmentOfferService.findOne(shipmentOffer.getId());
		feePaymentForm = feePaymentFormService.create(3, shipmentOffer.getId(), 0, 0.0, "Prueba de pago");
		creditCard = new CreditCard();
		creditCard.setHolderName("Nombre de Prueba");
		creditCard.setBrandName("VISA");
		creditCard.setNumber("4929772835813522");
		creditCard.setExpirationMonth(6);
		creditCard.setExpirationYear(2020);
		creditCard.setCvvCode(123);
		feePaymentForm.setCreditCard(creditCard);
		
		shipmentOffer = shipmentOfferService.accept(feePaymentForm.getOfferId());
		payment = feePaymentFormService.reconstruct(feePaymentForm);
		payment = feePaymentService.save(payment);
		
		numberOfPaymentsAfter = feePaymentService.findAll().size();
		numberOfPaymentPendingAfter = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		
		unauthenticate();
				
		Assert.isTrue(numberOfPaymentsAfter - numberOfPaymentsBefore == 1, "Number of Payment must increase");
		Assert.isTrue(numberOfPaymentPendingAfter - numberOfPaymentPendingBefore == 1, "Number of Pending Payments must increase");

		authenticate("user1");
		
		payment = feePaymentService.findOne(payment.getId());
		payment.setType("Accepted");
		payment = feePaymentService.save(payment);
		
		numberOfPaymentAcceptedAfter = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		unauthenticate();
		
		Assert.isTrue(numberOfPaymentAcceptedAfter - numberOfPaymentAcceptedBefore == 1, "Number of Accepted Payments must increase");
		
	}
	
	/**
	 * Create a payment about a Shipment Offer and Reject it.
	 */
	@Test
	public void positiveCreatePaymentShipmentOffer2(){
		
		Pageable page = new PageRequest(0, 10);
		
		Shipment shipment;
		
		Integer numberOfPaymentsBefore;
		Integer numberOfPaymentsAfter;
		Integer numberOfPaymentPendingBefore;
		Integer numberOfPaymentPendingAfter;
		Integer numberOfPaymentRejectedBefore;
		Integer numberOfPaymentRejectedAfter;
		
		numberOfPaymentsBefore = feePaymentService.findAll().size();
		
		authenticate("user1");
		
		numberOfPaymentPendingBefore = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		numberOfPaymentRejectedBefore = (int) feePaymentService.findAllRejected(page).getTotalElements();
		
		shipment = shipmentService.create();
		shipment.setOrigin("Sevilla");
		shipment.setDestination("Lugo");
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		shipment.setDepartureTime(departureTime);
		Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		shipment.setMaximumArriveTime(maximumArrivalTime);
		shipment.setItemSize("L");
		shipment.setItemName("Prueba");
		shipment.setPrice(10.0);
		shipment.setItemPicture("https://cde.3.elcomercio.pe/ima/0/1/0/2/1/1021917.jpg");
		shipment.setItemEnvelope("Open");
		shipment = shipmentService.save(shipment);
		
		unauthenticate();
		
		ShipmentOffer shipmentOffer;
		
		authenticate("user2");
		
		shipment = shipmentService.findOne(shipment.getId());
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(5.0);
		shipmentOffer.setDescription("Mensaje de prueba");
		shipmentOffer = shipmentOfferService.save(shipmentOffer);
		
		unauthenticate();
		
		FeePaymentForm feePaymentForm;
		FeePayment payment;
		CreditCard creditCard;
		
		authenticate("user1");
		
		shipmentOffer = shipmentOfferService.findOne(shipmentOffer.getId());
		feePaymentForm = feePaymentFormService.create(3, shipmentOffer.getId(), 0, 0.0, "Prueba de pago");
		creditCard = new CreditCard();
		creditCard.setHolderName("Nombre de Prueba");
		creditCard.setBrandName("VISA");
		creditCard.setNumber("4929772835813522");
		creditCard.setExpirationMonth(6);
		creditCard.setExpirationYear(2020);
		creditCard.setCvvCode(123);
		feePaymentForm.setCreditCard(creditCard);
		
		shipmentOffer = shipmentOfferService.accept(feePaymentForm.getOfferId());
		payment = feePaymentFormService.reconstruct(feePaymentForm);
		payment = feePaymentService.save(payment);
		
		numberOfPaymentsAfter = feePaymentService.findAll().size();
		numberOfPaymentPendingAfter = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		
		unauthenticate();
				
		Assert.isTrue(numberOfPaymentsAfter - numberOfPaymentsBefore == 1, "Number of Payment must increase");
		Assert.isTrue(numberOfPaymentPendingAfter - numberOfPaymentPendingBefore == 1, "Number of Pending Payments must increase");

		authenticate("user1");
		
		payment = feePaymentService.findOne(payment.getId());
		payment.setType("Rejected");
		payment = feePaymentService.save(payment);
		
		numberOfPaymentRejectedAfter = (int) feePaymentService.findAllRejected(page).getTotalElements();
		
		unauthenticate();
		
		Assert.isTrue(numberOfPaymentRejectedAfter - numberOfPaymentRejectedBefore == 1, "Number of Accepted Payments must increase");
		
	}
	
	/**
	 * Create a payment about a Shipment Offer and introduce a bad type.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreatePaymentShipmentOffer1(){
		
		Pageable page = new PageRequest(0, 10);
		
		Shipment shipment;
		
		Integer numberOfPaymentsBefore;
		Integer numberOfPaymentsAfter;
		Integer numberOfPaymentPendingBefore;
		Integer numberOfPaymentPendingAfter;
		Integer numberOfPaymentAcceptedBefore;
		Integer numberOfPaymentAcceptedAfter;
		
		numberOfPaymentsBefore = feePaymentService.findAll().size();
		
		authenticate("user1");
		
		numberOfPaymentPendingBefore = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		numberOfPaymentAcceptedBefore = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		shipment = shipmentService.create();
		shipment.setOrigin("Sevilla");
		shipment.setDestination("Lugo");
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		shipment.setDepartureTime(departureTime);
		Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		shipment.setMaximumArriveTime(maximumArrivalTime);
		shipment.setItemSize("L");
		shipment.setItemName("Prueba");
		shipment.setPrice(10.0);
		shipment.setItemPicture("https://cde.3.elcomercio.pe/ima/0/1/0/2/1/1021917.jpg");
		shipment.setItemEnvelope("Open");
		shipment = shipmentService.save(shipment);
		
		unauthenticate();
		
		ShipmentOffer shipmentOffer;
		
		authenticate("user2");
		
		shipment = shipmentService.findOne(shipment.getId());
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(5.0);
		shipmentOffer.setDescription("Mensaje de prueba");
		shipmentOffer = shipmentOfferService.save(shipmentOffer);
		
		unauthenticate();
		
		FeePaymentForm feePaymentForm;
		FeePayment payment;
		CreditCard creditCard;
		
		authenticate("user1");
		
		shipmentOffer = shipmentOfferService.findOne(shipmentOffer.getId());
		feePaymentForm = feePaymentFormService.create(3, shipmentOffer.getId(), 0, 0.0, "Prueba de pago");
		creditCard = new CreditCard();
		creditCard.setHolderName("Nombre de Prueba");
		creditCard.setBrandName("VISA");
		creditCard.setNumber("4929772835813522");
		creditCard.setExpirationMonth(6);
		creditCard.setExpirationYear(2020);
		creditCard.setCvvCode(123);
		feePaymentForm.setCreditCard(creditCard);
		
		shipmentOffer = shipmentOfferService.accept(feePaymentForm.getOfferId());
		payment = feePaymentFormService.reconstruct(feePaymentForm);
		payment = feePaymentService.save(payment);
		
		numberOfPaymentsAfter = feePaymentService.findAll().size();
		numberOfPaymentPendingAfter = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		
		unauthenticate();
				
		Assert.isTrue(numberOfPaymentsAfter - numberOfPaymentsBefore == 1, "Number of Payment must increase");
		Assert.isTrue(numberOfPaymentPendingAfter - numberOfPaymentPendingBefore == 1, "Number of Pending Payments must increase");

		authenticate("user1");
		
		payment = feePaymentService.findOne(payment.getId());
		payment.setType("blablabla");
		payment = feePaymentService.save(payment);
		
		numberOfPaymentAcceptedAfter = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		unauthenticate();
		
		Assert.isTrue(numberOfPaymentAcceptedAfter - numberOfPaymentAcceptedBefore == 1, "Number of Accepted Payments must increase");
		
	}
	
	/**
	 * Create a payment about a Shipment Offer passing a wrong Shipment Offer ID.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void negativeCreatePaymentShipmentOffer2(){
		
		Pageable page = new PageRequest(0, 10);
		
		Shipment shipment;
		
		Integer numberOfPaymentsBefore;
		Integer numberOfPaymentsAfter;
		Integer numberOfPaymentPendingBefore;
		Integer numberOfPaymentPendingAfter;
		Integer numberOfPaymentAcceptedBefore;
		Integer numberOfPaymentAcceptedAfter;
		
		numberOfPaymentsBefore = feePaymentService.findAll().size();
		
		authenticate("user1");
		
		numberOfPaymentPendingBefore = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		numberOfPaymentAcceptedBefore = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		shipment = shipmentService.create();
		shipment.setOrigin("Sevilla");
		shipment.setDestination("Lugo");
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		shipment.setDepartureTime(departureTime);
		Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		shipment.setMaximumArriveTime(maximumArrivalTime);
		shipment.setItemSize("L");
		shipment.setItemName("Prueba");
		shipment.setPrice(10.0);
		shipment.setItemPicture("https://cde.3.elcomercio.pe/ima/0/1/0/2/1/1021917.jpg");
		shipment.setItemEnvelope("Open");
		shipment = shipmentService.save(shipment);
		
		unauthenticate();
		
		ShipmentOffer shipmentOffer;
		
		authenticate("user2");
		
		shipment = shipmentService.findOne(shipment.getId());
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(5.0);
		shipmentOffer.setDescription("Mensaje de prueba");
		shipmentOffer = shipmentOfferService.save(shipmentOffer);
		
		unauthenticate();
		
		FeePaymentForm feePaymentForm;
		FeePayment payment;
		CreditCard creditCard;
		
		authenticate("user1");
		
		shipmentOffer = shipmentOfferService.findOne(shipmentOffer.getId());
		feePaymentForm = feePaymentFormService.create(3, 0, 0, 0.0, "Prueba de pago");
		creditCard = new CreditCard();
		creditCard.setHolderName("Nombre de Prueba");
		creditCard.setBrandName("VISA");
		creditCard.setNumber("4929772835813522");
		creditCard.setExpirationMonth(6);
		creditCard.setExpirationYear(2020);
		creditCard.setCvvCode(123);
		feePaymentForm.setCreditCard(creditCard);
		
		shipmentOffer = shipmentOfferService.accept(feePaymentForm.getOfferId());
		payment = feePaymentFormService.reconstruct(feePaymentForm);
		payment = feePaymentService.save(payment);
		
		numberOfPaymentsAfter = feePaymentService.findAll().size();
		numberOfPaymentPendingAfter = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		
		unauthenticate();
				
		Assert.isTrue(numberOfPaymentsAfter - numberOfPaymentsBefore == 1, "Number of Payment must increase");
		Assert.isTrue(numberOfPaymentPendingAfter - numberOfPaymentPendingBefore == 1, "Number of Pending Payments must increase");

		authenticate("user1");
		
		payment = feePaymentService.findOne(payment.getId());
		payment.setType("Accepted");
		payment = feePaymentService.save(payment);
		
		numberOfPaymentAcceptedAfter = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		unauthenticate();
		
		Assert.isTrue(numberOfPaymentAcceptedAfter - numberOfPaymentAcceptedBefore == 1, "Number of Accepted Payments must increase");
		
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void negativeCreatePaymentShipmentOffer3(){
		
		Pageable page = new PageRequest(0, 10);
		
		Shipment shipment;
		
		Integer numberOfPaymentsBefore;
		Integer numberOfPaymentsAfter;
		Integer numberOfPaymentPendingBefore;
		Integer numberOfPaymentPendingAfter;
		Integer numberOfPaymentAcceptedBefore;
		Integer numberOfPaymentAcceptedAfter;
		
		numberOfPaymentsBefore = feePaymentService.findAll().size();
		
		authenticate("user1");
		
		numberOfPaymentPendingBefore = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		numberOfPaymentAcceptedBefore = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		shipment = shipmentService.create();
		shipment.setOrigin("Sevilla");
		shipment.setDestination("Lugo");
		Date departureTime = new GregorianCalendar(2017, Calendar.JULY, 01).getTime();
		shipment.setDepartureTime(departureTime);
		Date maximumArrivalTime = new GregorianCalendar(2017, Calendar.JULY, 02).getTime();
		shipment.setMaximumArriveTime(maximumArrivalTime);
		shipment.setItemSize("L");
		shipment.setItemName("Prueba");
		shipment.setPrice(10.0);
		shipment.setItemPicture("https://cde.3.elcomercio.pe/ima/0/1/0/2/1/1021917.jpg");
		shipment.setItemEnvelope("Open");
		shipment = shipmentService.save(shipment);
		
		unauthenticate();
		
		ShipmentOffer shipmentOffer;
		
		authenticate("user2");
		
		shipment = shipmentService.findOne(shipment.getId());
		shipmentOffer = shipmentOfferService.create(shipment.getId());
		shipmentOffer.setAmount(5.0);
		shipmentOffer.setDescription("Mensaje de prueba");
		shipmentOffer = shipmentOfferService.save(shipmentOffer);
		
		unauthenticate();
		
		FeePaymentForm feePaymentForm;
		FeePayment payment;
		CreditCard creditCard;
		
		authenticate("user1");
		
		shipmentOffer = shipmentOfferService.findOne(shipmentOffer.getId());
		feePaymentForm = feePaymentFormService.create(3, shipmentOffer.getId(), 0, 0.0, "Prueba de pago");
		creditCard = new CreditCard();
//		creditCard.setHolderName("Nombre de Prueba");
//		creditCard.setBrandName("VISA");
//		creditCard.setNumber("4929772835813522");
//		creditCard.setExpirationMonth(6);
//		creditCard.setExpirationYear(2020);
//		creditCard.setCvvCode(123);
		feePaymentForm.setCreditCard(creditCard);
		
		shipmentOffer = shipmentOfferService.accept(feePaymentForm.getOfferId());
		payment = feePaymentFormService.reconstruct(feePaymentForm);
		payment = feePaymentService.save(payment);
		
		numberOfPaymentsAfter = feePaymentService.findAll().size();
		numberOfPaymentPendingAfter = (int) feePaymentService.findAllPendingByUser(page).getTotalElements();
		
		unauthenticate();
				
		Assert.isTrue(numberOfPaymentsAfter - numberOfPaymentsBefore == 1, "Number of Payment must increase");
		Assert.isTrue(numberOfPaymentPendingAfter - numberOfPaymentPendingBefore == 1, "Number of Pending Payments must increase");

		authenticate("user1");
		
		payment = feePaymentService.findOne(payment.getId());
		payment.setType("Accepted");
		payment = feePaymentService.save(payment);
		
		numberOfPaymentAcceptedAfter = (int) feePaymentService.findAllAccepted(page).getTotalElements();
		
		unauthenticate();
		
		Assert.isTrue(numberOfPaymentAcceptedAfter - numberOfPaymentAcceptedBefore == 1, "Number of Accepted Payments must increase");
		
	}
}