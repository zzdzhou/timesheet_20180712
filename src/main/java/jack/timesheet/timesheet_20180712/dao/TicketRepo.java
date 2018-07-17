package jack.timesheet.timesheet_20180712.dao;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.springframework.data.repository.CrudRepository;
import sun.util.resources.cldr.kea.TimeZoneNames_kea;

public interface TicketRepo extends CrudRepository<Ticket, Integer> {


}
