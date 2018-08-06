package jack.timesheet.timesheet_20180712.dao;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface TicketRepo extends CrudRepository<Ticket, Integer> {

    List<Ticket> findByUserAndDateBetween(User user, Date start, Date end);
}
