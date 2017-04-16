package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
	
	@Query("select r from Rating r where (?1 <= 0 OR r.author.id = ?1)"
			+ " AND (?2 <= 0 OR r.user.id = ?2)"
			+ " ORDER BY r.createdDate DESC")
	Page<Rating> findAllByAuthorOrUser(int authorId, int userReceivedId, Pageable page);
	
	@Query("select count(r) from Rating r where r.author.id = ?1")
	int countRatingCreatedByUserId(int authorId);
	
	@Query("select count(r) from Rating r where r.user.id = ?1")
	int countRatingReceivedByUserId(int userId);
}
