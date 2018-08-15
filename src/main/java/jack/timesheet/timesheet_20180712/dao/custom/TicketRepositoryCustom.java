package jack.timesheet.timesheet_20180712.dao.custom;

import jack.timesheet.timesheet_20180712.entities.Ticket;

import java.util.List;

public interface TicketRepositoryCustom {

    List<Ticket> getTickets(String fullName, String dateRange);

}
