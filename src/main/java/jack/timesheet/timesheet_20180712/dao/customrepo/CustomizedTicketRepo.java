package jack.timesheet.timesheet_20180712.dao.customrepo;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;

import java.sql.Date;
import java.util.List;

public interface CustomizedTicketRepo {

    List<Ticket> findByUserAndDateBetweenIfFound(User user, Date start, Date end);
}
