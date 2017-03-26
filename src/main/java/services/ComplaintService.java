package services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Complaint;
import domain.Moderator;
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
	
	@Autowired
	private ModeratorService moderatorService;
	
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
		Moderator moderator;
		Complaint complaintPreSave;
		
		user = userService.findByPrincipal();
				
		if(complaint.getId() == 0) {
			Assert.isTrue(user.getId() != complaint.getInvolved().getId());
			
			complaint.setCreator(user);
			complaint.setModerator(null);
			complaint.setType(null);
			
			complaint = complaintRepository.save(complaint);
			
		} else {
			Assert.isTrue(actorService.checkAuthority("MODERATOR"),
					"Only an moderator can resolve a complaint");
			
			complaintPreSave = this.findOne(complaint.getId());
			
			moderator = moderatorService.findByPrincipal();
			
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
	
	public Collection<Complaint> findAll() {
		Collection<Complaint> result;

		result = complaintRepository.findAll();

		return result;
	}
	

	// Other business methods -------------------------------------------------
	
}
