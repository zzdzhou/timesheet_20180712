package jack.timesheet.timesheet_20180712.dao.customrepo;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public interface TicketRepositoryCustom {

    List<Ticket> getTickets(String fullName, String dateRange);
}
