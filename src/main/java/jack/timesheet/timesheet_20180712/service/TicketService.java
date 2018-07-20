package jack.timesheet.timesheet_20180712.service;

import jack.timesheet.timesheet_20180712.dao.TicketRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public void addATicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }
}
