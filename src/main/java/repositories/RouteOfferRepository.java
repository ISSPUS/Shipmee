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

	@Query("select ro from RouteOffer ro ,PayPalObject po where ro.route.id = ?1"
//			+ " AND (ro.id NOT IN "
//				+ "(select ro.id from RouteOffer ro, PayPalObject po where po.feePayment.routeOffer.id = ro.id AND ro.route.id = ?1)"
//				+ " OR (po.feePayment.routeOffer.id = ro.id AND po.payStatus <> 'CREATED'))"
			+ " GROUP BY ro.id"
			)
	Collection<RouteOffer> findAllByRouteId(int routeId);
	
	@Query("select ro from RouteOffer ro where ro.route.id = ?1 AND ro.acceptedByCarrier = false AND ro.rejectedByCarrier = false")
	Collection<RouteOffer> findAllPendingByRouteId(int routeId);
	
	@Query("select ro from RouteOffer ro, PayPalObject po"
			+ " where (?1 <= 0 OR ro.route.id = ?1)"
			+ " AND (?2 <= 0 OR ro.user.id = ?2)"
//			+ " AND (ro.id NOT IN "
//				+ "(select ro.id from RouteOffer ro, PayPalObject po where po.feePayment.routeOffer.id = ro.id AND ro.route.id = ?1)"
//				+ " OR (po.feePayment.routeOffer.id = ro.id AND po.payStatus <> 'CREATED'))"
			+ " GROUP BY ro.id"
			+ " ORDER BY ro.rejectedByCarrier ASC")
	Page<RouteOffer> findAllByRouteIdAndUserId(int routeId, int userId, Pageable page);
	
	@Query("select ro from RouteOffer ro where ro.shipment.id = ?1 AND ro.acceptedByCarrier = false AND ro.rejectedByCarrier = false")
	Collection<RouteOffer> findAllPendingByShipmentId(int shipmentId);

}
