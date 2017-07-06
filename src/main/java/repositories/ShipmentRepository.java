package repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Shipment;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
	
	@Query("select s from Shipment s where (?1 like CONCAT('%',s.origin,'%') OR s.origin like CONCAT('%',?1,'%')) "
			+ "AND (?2 like CONCAT('%',s.destination,'%') OR s.destination like CONCAT('%',?2,'%'))"
			+ "AND (?3 IS NULL OR (year(s.departureTime) = year(?3) AND month(s.departureTime) = month(?3) AND day(s.departureTime) = day(?3)))"
			+ "AND (?7 IS NULL OR (year(s.departureTime) > year(?7) OR "
				+ "(year(s.departureTime) = year(?7) AND month(s.departureTime) > month(?7) OR "
				+ "(year(s.departureTime) = year(?7) AND month(s.departureTime) = month(?7) AND day(s.departureTime) >= day(?7)))))"
			+ "AND (?4 IS NULL OR s.departureTime >= ?4) "
			+ "AND (?5 IS NULL OR s.itemEnvelope = ?5  OR s.itemEnvelope LIKE 'both') "
			+ "AND (?6 IS NULL OR s.itemSize = ?6) "
			+ "AND s.carried IS NULL "
			+ "ORDER BY s.maximumArriveTime ASC")
	Page<Shipment> searchShipment(String origin, String destination, Date date, Date time, String envelope, String itemSize, Date moment, Pageable page);
	
	@Query("select s from Shipment s where s.creator.id = ?1")
	Page<Shipment> findAllByUserId(int userId, Pageable page);
	
	@Query("select count(s) from Shipment s where s.creator.id = ?1")
	int countShipmentCreatedByUserId(int userId);
	
	@Query("select s from Shipment s where s.creator.id = ?1 AND (?2 IS NULL OR (year(s.departureTime) > year(?2) OR (year(s.departureTime) = year(?2) AND month(s.departureTime) > month(?2) OR (year(s.departureTime) = year(?2) AND month(s.departureTime) = month(?2) AND day(s.departureTime) >= day(?2))))) AND s.carried IS NULL ORDER BY s.itemName ASC")
	Page<Shipment> findAllAvailableByUserId(int userId, Date moment, Pageable page);
}
