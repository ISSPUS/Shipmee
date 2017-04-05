package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository  extends JpaRepository<Message, Integer>{


	@Query("select m from Message m where m.recipient.id = ?1 ORDER BY m.moment desc")
	Page<Message> messagesReceived(int id,Pageable page);
	
	@Query("select m from Message m where m.sender.id = ?1 ORDER BY m.moment desc")
	Page<Message> messagesSent(int id,Pageable page);
	
	@Query("select count(m) from Message m where m.sender.id = ?1")
	int countMessagesSentByActorId(int id);
	
	@Query("select count(m) from Message m where m.recipient.id = ?1")
	int countMessagesReceivedtByActorId(int id);
}
