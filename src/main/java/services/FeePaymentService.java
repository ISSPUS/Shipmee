package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.FeePayment;
import domain.User;
import repositories.FeePaymentRepository;

@Service
@Transactional
public class FeePaymentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FeePaymentRepository feePaymentRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public FeePaymentService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FeePayment create() {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can create a feepayment");
		
		FeePayment result;
		User user;
		
		result = new FeePayment();
		user = userService.findByPrincipal();
		
		result.setPurchaser(user);
		result.setPaymentMoment(new Date());
		result.setType("Pending");
		
		return result;
	}
	
	public FeePayment save(FeePayment feePayment) {
		Assert.notNull(feePayment);
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can save a feepayment");
		
		User user;
		FeePayment feePaymentPreSave;
		
		user = userService.findByPrincipal();
		
		if(feePayment.getId() == 0) {
			feePayment.setPurchaser(user);
			feePayment.setPaymentMoment(new Date());
			feePayment.setType("Pending");
			
			feePayment = feePaymentRepository.save(feePayment);
		} else {
			feePaymentPreSave = this.findOne(feePayment.getId());
			feePaymentPreSave.setType(feePayment.getType());
			
			feePayment = feePaymentRepository.save(feePaymentPreSave);
		}	
			
		return feePayment;
	}
	
	
	public FeePayment findOne(int feePaymentId) {
		FeePayment result;
		
		result = feePaymentRepository.findOne(feePaymentId);
		
		return result;
	}
	
	public Collection<FeePayment> findAll() {
		Collection<FeePayment> result;

		result = feePaymentRepository.findAll();

		return result;
	}

	public Page<FeePayment> findAllPendingByUser(Pageable pageable) {
		Page<FeePayment> result;
		User user;
		
		user = userService.findByPrincipal();

		result = feePaymentRepository.findAllPendingByUser(user.getId(), pageable);
		Assert.notNull(result);
		
		return result;
	}
	

	// Other business methods -------------------------------------------------
	
	public Page<FeePayment> findAllRejected(Pageable page) {
		Page<FeePayment> result;

		result = feePaymentRepository.findAllRejected(page);
		Assert.notNull(result);
		return result;
	}
	
	public Page<FeePayment> findAllPending(Pageable page) {
		Page<FeePayment> result;

		result = feePaymentRepository.findAllPending(page);
		Assert.notNull(result);
		return result;
	}
	
	public Page<FeePayment> findAllAccepted(Pageable page) {
		Page<FeePayment> result;

		result = feePaymentRepository.findAllAccepted(page);
		Assert.notNull(result);
		return result;
	}
}
