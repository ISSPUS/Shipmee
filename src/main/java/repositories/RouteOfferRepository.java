package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RouteOffer;

@Repository
public interface RouteOfferRepository extends JpaRepository<RouteOffer, Integer> {

	@Query("select ro from RouteOffer ro where ro.route.id = ?1")
	Collection<RouteOffer> findAllByRouteId(int routeId);
	
	@Query("select ro from RouteOffer ro where ro.route.id = ?1 AND ro.acceptedByCarrier = false AND ro.rejectedByCarrier = false")
	Collection<RouteOffer> findAllPendingByRouteId(int routeId);
	
	@Query("select ro from RouteOffer ro where (?1 <= 0 OR ro.route.id = ?1)"
			+ " AND (?2 <= 0 OR ro.user.id = ?2)"
			+ " ORDER BY ro.rejectedByCarrier ASC")
	Page<RouteOffer> findAllByRouteIdAndUserId(int routeId, int userId, Pageable page);

}
