package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {

	@Query("select a from Alert a where a.origin like %?1% AND a.destination like %?2% AND "
			+ "year(a.date) = year(?3) AND month(a.date) = month(?3) AND day(a.date) = day(?3)"
			+ "AND a.type like ?4")
	Collection<Alert> checkAlerts(String origin, String destination, Date date, String type);
	
	@Query("select a from Alert a where a.user.id = ?1")
	Collection<Alert> getAlertsOfUser(int userId);
}
