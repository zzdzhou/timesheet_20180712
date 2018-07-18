package jack.timesheet.timesheet_20180712.dao;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepo extends CrudRepository<Ticket, Integer> {


}
