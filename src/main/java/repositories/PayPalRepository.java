package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PayPalObject;

@Repository
public interface PayPalRepository extends JpaRepository<PayPalObject, Integer> {
	
	@Query("select count(r) from PayPalObject r where r.trackingId = ?1")
	int countPayPalObjectWithTrackingId(String trackingId);
	
	@Query("select r from PayPalObject r where r.trackingId = ?1")
	PayPalObject findByTrackingId(String trackingId);
	
	@Query("select r from PayPalObject r where r.feePayment.id = ?1")
	PayPalObject findByFeePayment(int feePaymentId);
}
