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
	
	@Query("select u from User u where u.isVerified = true AND where u.fotoDNI != null")
	Page<User> findAllVerified(Pageable page);
	
	@Query("select count(u) from User u where u.isVerified = true AND where u.fotoDNI != null")
	int countAllVerified();
	
	@Query("select u from User u where u.isVerified = false AND where u.fotoDNI != null")
	Page<User> findAllPending(Pageable page);
	
	@Query("select count(u) from User u where u.isVerified = false AND where u.fotoDNI != null")
	int countAllPending();
}
