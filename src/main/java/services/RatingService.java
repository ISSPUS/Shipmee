package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Rating;
import domain.User;
import repositories.RatingRepository;

@Service
@Transactional
public class RatingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RatingRepository ratingRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService userService;
	
	@Autowired
	private Validator	validator;

	// Constructors -----------------------------------------------------------

	public RatingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Rating create(int concerningUserId) {
		Rating result;
		User creator;
		User concerning;

		result = new Rating();
		creator = userService.findByPrincipal();
		concerning = userService.findOne(concerningUserId);


		result.setAuthor(creator);
		result.setUser(concerning);
		result.setCreatedDate(new Date((new Date()).getTime() - 1000));

		return result;
	}
	
	public Rating findOne(int entityId) {
		Rating result;

		result = ratingRepository.findOne(entityId);

		return result;
	}
	
	public Rating save(Rating rating) {
		Assert.notNull(rating);
		Rating result;
		User user;
		
		Assert.isTrue(rating.getAuthor().getId() != rating.getUser().getId(), "ratingService.save.error.AuthorEqualUser");
		
		user = userService.findByPrincipal();
		
		Assert.isTrue(rating.getAuthor().equals(user), "ratingService.save.error.notAuthor");

		result = ratingRepository.save(rating);
		
		return result;
	}
	
	public Rating reconstruct(Rating rating, final BindingResult binding){
		Rating result;
		
		result = this.create(rating.getUser().getId());

		result.setComment(rating.getComment());
		result.setValue(rating.getValue());

		this.validator.validate(result, binding);

		return result;
		
	}
	
	public int countRatingCreatedByUserId(User user){
		Assert.notNull(user);
		
		int result;
		
		result = ratingRepository.countRatingCreatedByUserId(user.getId());
		
		return result;
	}
	
	public int countRatingReceivedByUserId(User user){
		Assert.notNull(user);
		
		int result;
		
		result = ratingRepository.countRatingReceivedByUserId(user.getId());
		
		return result;
	}

	// Other business methods -------------------------------------------------
	
	/**
	 * 
	 * @param authorId - could be <= 0 to ignore
	 * @param userReceivedId - could be <= 0 to ignore
	 * @param page
	 * @return
	 */
	public Page<Rating> findAllByAuthorOrUser(int authorId, int userReceivedId, Pageable page) {
		Page<Rating> result;
		
		Assert.isTrue(authorId + userReceivedId > 0, 
				"RatingService.findAllByAuthorOrUser.error.notIdSpecified");

		result = ratingRepository.findAllByAuthorOrUser(authorId, userReceivedId, page);
		
		return result;
	}
	
}
