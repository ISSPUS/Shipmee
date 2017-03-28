package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
	
	@Query("select r from Route r where r.origin LIKE %?1% AND r.destination LIKE %?2% AND (?3 IS NULL OR (year(r.departureTime) = year(?3) AND month(r.departureTime) = month(?3) AND day(r.departureTime) = day(?3)))"
			+ "AND (?4 IS NULL OR r.departureTime >= ?4) AND (?5 IS NULL OR r.itemEnvelope = ?5 OR r.itemEnvelope LIKE 'both')"
			+ "AND (?6 IS NULL OR (select sp.size from SizePrice sp where sp.route.id = r.id AND sp.size = ?6) IS NOT NULL)")
	Collection<Route> searchRoute(String origin, String destination, Date date, Date time, String envelope, String itemSize);
	
	@Query("select r from Route r where r.creator.id = ?1")
	Page<Route> findAllByUserId(int userId, Pageable page);
}
