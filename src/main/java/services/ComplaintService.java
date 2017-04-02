package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Complaint;
import domain.User;
import repositories.ComplaintRepository;

@Service
@Transactional
public class ComplaintService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ComplaintRepository complaintRepository;

	// Supporting services ----------------------------------------------------
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;
	
	// Constructors -----------------------------------------------------------

	public ComplaintService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Complaint create(int userId) {
		Assert.isTrue(actorService.checkAuthority("USER"),
				"Only an user can create a complaint");
		
		Complaint result;
		User user, involved;
		
		result = new Complaint();
		user = userService.findByPrincipal();
		involved = userService.findOne(userId);
		
		Assert.isTrue(user.getId() != userId);
		
		result.setCreator(user);
		result.setInvolved(involved);
		result.setModerator(null);
		result.setType(null);
		
		return result;
	}
	
	public Complaint save(Complaint complaint) {
		Assert.notNull(complaint);
		
		User user;
		User moderator;
		Complaint complaintPreSave;
		
		
				
		if(complaint.getId() == 0) {
			user = userService.findByPrincipal();
			
			Assert.isTrue(user.getId() != complaint.getInvolved().getId());
			
			complaint.setCreator(user);
			complaint.setModerator(null);
			complaint.setType(null);
			
			complaint = complaintRepository.save(complaint);
			
		} else {
			Assert.isTrue(actorService.checkAuthority("MODERATOR"),
					"Only an moderator can resolve a complaint");
			Assert.isTrue(checkType(complaint.getType()));
			
			complaintPreSave = this.findOne(complaint.getId());
			
			moderator = userService.findByPrincipal();
			
			complaintPreSave.setModerator(moderator);
			complaintPreSave.setType(complaint.getType());
						
			complaint = complaintRepository.save(complaintPreSave);
		}

		return complaint;
	}
	
	
	public Complaint findOne(int complaintId) {
		Complaint result;
		
		result = complaintRepository.findOne(complaintId);
		
		return result;
	}
	
	public Page<Complaint> findAllNotResolved(Pageable page) {
		Page<Complaint> result;

		result = complaintRepository.findAllNotResolved(page);
		Assert.notNull(result);
		return result;
	}
	
	public Page<Complaint> findAllSerious(Pageable page) {
		Page<Complaint> result;

		result = complaintRepository.findAllSerious(page);
		Assert.notNull(result);
		return result;
	}
	
	public Page<Complaint> findAllMild(Pageable page) {
		Page<Complaint> result;

		result = complaintRepository.findAllMild(page);
		Assert.notNull(result);
		return result;
	}
	
	public Page<Complaint> findAllOmitted(Pageable page) {
		Page<Complaint> result;

		result = complaintRepository.findAllOmitted(page);
		Assert.notNull(result);
		return result;
	}
	

	// Other business methods -------------------------------------------------
	
	private boolean checkType(String type) {
		boolean res;
		
		res = false;

		if(type.equals("Omitted") || type.equals("Mild") ||
				type.equals("Serious") || type.equals("Omitido") ||
				type.equals("Leve") || type.equals("Grave")) {
			res = true;
		}

		return res;
	}
}
