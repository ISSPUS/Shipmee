package repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
	
	@Query("select r from Route r where (?1 like CONCAT('%',r.origin,'%') OR r.origin like CONCAT('%',?1,'%')) "
			+ "AND (?2 like CONCAT('%',r.destination,'%') OR r.destination like CONCAT('%',?2,'%'))"
			+ "AND (?3 IS NULL OR (year(r.departureTime) = year(?3) AND month(r.departureTime) = month(?3) AND day(r.departureTime) = day(?3)))"
			+ "AND (?7 IS NULL OR (year(r.departureTime) > year(?7) OR "
				+ "(year(r.departureTime) = year(?7) AND month(r.departureTime) > month(?7) OR "
				+ "(year(r.departureTime) = year(?7) AND month(r.departureTime) = month(?7) AND day(r.departureTime) >= day(?7)))))"
			+ "AND (?4 IS NULL OR r.departureTime >= ?4) "
			+ "AND (?5 IS NULL OR r.itemEnvelope = ?5 OR r.itemEnvelope LIKE 'both')"
			+ "AND (?6 IS NULL OR (select sp.size from SizePrice sp where sp.route.id = r.id AND sp.size = ?6) IS NOT NULL) "
			+ "ORDER BY r.arriveTime ASC")
	Page<Route> searchRoute(String origin, String destination, Date date, Date time, String envelope, String itemSize, Date moment, Pageable page);
	
	@Query("select r from Route r where r.creator.id = ?1")
	Page<Route> findAllByUserId(int userId, Pageable page);
	
	@Query("select count(r) from Route r where r.creator.id = ?1")
	int countRouteCreatedByUserId(int userId);
}
