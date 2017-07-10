package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {

	@Query("select a from Alert a where (?1 like CONCAT('%',a.origin,'%') OR a.origin like CONCAT('%',?1,'%')) "
			+ "AND (?2 like CONCAT('%',a.destination,'%') OR a.destination like CONCAT('%',?2,'%'))"
			+ "AND year(a.date) = year(?3) AND month(a.date) = month(?3) AND day(a.date) = day(?3)"
			+ "AND a.type like ?4")
	Collection<Alert> checkAlerts(String origin, String destination, Date date, String type);
	
	@Query("select a from Alert a where a.user.id = ?1")
	Page<Alert> getAlertsOfUser(int userId,Pageable page);
}
