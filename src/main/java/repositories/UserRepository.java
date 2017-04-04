package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select m from User m where m.userAccount.id = ?1")
	User findByUserAccountId(int id);

	@Query("select u from User u join u.routes r where r.id = ?1")
	Collection<User> findAllByRoutePurchased(int routeId);
	
	@Query("select c from User c where c.userAccount.username= ?1")
	User findByUsername(String username);

	@Query("select u from User u where (?1 < 0 OR u.isVerified = ?1)"
			+ " AND (?2 < 0 OR u.isActive = ?2)"
			+ " AND (?3 < 0 OR (u.isVerified = false AND u.dniPhoto <> ''))"
			+ " AND (?4 <> 1 OR 'MODERATOR' MEMBER OF u.userAccount.authorities)"
			+ " AND (?4 <> 0 OR 'MODERATOR' NOT MEMBER OF u.userAccount.authorities)")
	Page<User> findAllByVerifiedActiveVerificationPending(int isVerified, int isActive, int verificationPending, int isModerator, Pageable page);
}
