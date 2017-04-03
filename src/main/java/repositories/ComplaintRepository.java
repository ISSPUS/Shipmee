package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select c from Complaint c where c.type is null and c.creator.id != ?1 and c.involved.id != ?1")
	Page<Complaint> findAllNotResolvedAndNotInvolved(Pageable page, int id);
	
	@Query("select c from Complaint c where c.type like 'Serious' or c.type like 'Grave'")
	Page<Complaint> findAllSerious(Pageable page);
	
	@Query("select c from Complaint c where c.type like 'Mild' or c.type like 'Leve'")
	Page<Complaint> findAllMild(Pageable page);
	
	@Query("select c from Complaint c where c.type like 'Omitted' or c.type like 'Omitido'")
	Page<Complaint> findAllOmitted(Pageable page);
}
