package jack.timesheet.timesheet_20180712.web.controller;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public String ticket(User user) {

        return "timesheet";
    }

    @PostMapping("/add")
    public String addATicket(Ticket ticket, Model model, /*todo*/User user) {
        // todo
//        ticket.setDate(new Date());

        // todo
        ticket.setResource(user.getFullName());
        ticketService.addATicket(ticket);
        return "timesheet";
    }
}
