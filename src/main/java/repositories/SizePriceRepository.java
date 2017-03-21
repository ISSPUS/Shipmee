package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SizePrice;

@Repository
public interface SizePriceRepository extends JpaRepository<SizePrice, Integer> {

	@Query("select s from SizePrice s where s.route.id = ?1")
	Collection<SizePrice> findAllByRouteId(int routeId);

}
