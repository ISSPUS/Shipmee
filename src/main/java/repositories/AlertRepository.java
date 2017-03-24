package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Integer> {

}
