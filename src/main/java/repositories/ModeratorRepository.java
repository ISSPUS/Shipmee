package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Moderator;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

}
