package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ShipmentOffer;

@Repository
public interface ShipmentOfferRepository extends JpaRepository<ShipmentOffer, Integer> {

	@Query("select so from ShipmentOffer so ,PayPalObject po where so.shipment.id = ?1"
//			+ " AND (so.id NOT IN "
//				+ "(select so.id from ShipmentOffer so, PayPalObject po where po.feePayment.shipmentOffer.id = so.id AND so.shipment.id = ?1)"
//				+ " OR (po.feePayment.shipmentOffer.id = so.id AND po.payStatus <> 'CREATED'))"
			+ " GROUP BY so.id"
			)
	Collection<ShipmentOffer> findAllByShipmentId(int shipmentId);
	
	@Query("select so from ShipmentOffer so where so.shipment.id = ?1 AND so.acceptedBySender = false AND so.rejectedBySender = false")
	Collection<ShipmentOffer> findAllPendingByShipmentId(int shipmentId);
	
	@Query("select so from ShipmentOffer so, PayPalObject po"
			+ " where (?1 <= 0 OR so.shipment.id = ?1)"
			+ " AND (?2 <= 0 OR so.user.id = ?2)"
//			+ " AND (so.id NOT IN "
//				+ "(select ro.id from ShipmentOffer ro, PayPalObject po where po.feePayment.shipmentOffer.id = ro.id AND ro.shipment.id = ?1)"
//				+ " OR (po.feePayment.shipmentOffer.id = so.id AND po.payStatus <> 'CREATED'))"
			+ " GROUP BY so.id"
			+ " ORDER BY so.rejectedBySender ASC")
	Page<ShipmentOffer> findAllByShipmentIdAndUserId(int shipmentId, int userId, Pageable page);

}
