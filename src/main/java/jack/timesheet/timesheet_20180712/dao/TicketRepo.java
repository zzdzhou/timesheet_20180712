package jack.timesheet.timesheet_20180712.dao;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TicketRepo extends Repository<Ticket, Integer> {

    Ticket save(Ticket ticket);

    void deleteById(Integer id);

    Optional<Ticket> findById(Integer id);

    List<Ticket> findAll();

    List<Ticket> findByUserAndDateBetween(User user, Date start, Date end);

    @Query(value = "select distinct t.resource from Ticket t order by t.resource asc", nativeQuery = false)
    List<String> findResouceDistinct();
}
