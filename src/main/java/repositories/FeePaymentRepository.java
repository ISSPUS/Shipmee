package repositories;


import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FeePayment;

@Repository
public interface FeePaymentRepository extends JpaRepository<FeePayment, Integer> {

	@Query("select f from FeePayment f where f.type like 'Pending' and f.purchaser.id = ?1 and f.routeOffer.acceptedByCarrier is true")
	Collection<FeePayment> findAllPendingRouteOffersByUser(int id);
	
	@Query("select f from FeePayment f where f.type like 'Pending' and f.purchaser.id = ?1 and f.shipmentOffer.acceptedBySender is true")
	Collection<FeePayment> findAllPendingShipmentOffersByUser(int id);
	
	@Query("select f from FeePayment f where f.type like 'Accepted' and f.purchaser.id = ?1 and f.routeOffer.acceptedByCarrier is true")
	Collection<FeePayment> findAllAcceptedRouteOffersByUser(int id);
	
	@Query("select f from FeePayment f where f.type like 'Accepted' and f.purchaser.id = ?1 and f.shipmentOffer.acceptedBySender is true")
	Collection<FeePayment> findAllAcceptedShipmentOffersByUser(int id);
	
	@Query("select f from FeePayment f where f.type like 'Rejected' and f.purchaser.id = ?1 and f.routeOffer.acceptedByCarrier is true")
	Collection<FeePayment> findAllRejectedRouteOffersByUser(int id);
	
	@Query("select f from FeePayment f where f.type like 'Rejected' and f.purchaser.id = ?1 and f.shipmentOffer.acceptedBySender is true")
	Collection<FeePayment> findAllRejectedShipmentOffersByUser(int id);

	@Query("select f from FeePayment f where f.type like 'Rejected'")
	Page<FeePayment> findAllRejected(Pageable page);
	
	@Query("select f from FeePayment f where f.type like 'Pending' and f.routeOffer.acceptedByCarrier is true")
	Collection<FeePayment> findAllPendingRouteOffers();
	
	@Query("select f from FeePayment f where f.type like 'Pending' and f.shipmentOffer.acceptedBySender is true")
	Collection<FeePayment> findAllPendingShipmentOffers();
	
	@Query("select f from FeePayment f where f.type like 'Accepted'")
	Page<FeePayment> findAllAccepted(Pageable page);
}
