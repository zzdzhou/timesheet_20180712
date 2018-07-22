package jack.timesheet.timesheet_20180712.web.rest.controller;

import jack.timesheet.timesheet_20180712.dao.TicketRepo;
import jack.timesheet.timesheet_20180712.dao.UserRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/ticket")
public class RestTicketController {

    private UserRepo userRepo;
    private TicketRepo ticketRepo;

    @Autowired
    public RestTicketController(UserRepo userRepo, TicketRepo ticketRepo) {
        this.userRepo = userRepo;
        this.ticketRepo = ticketRepo;
    }

    @GetMapping
    public List<Ticket> getTimesheet(@RequestParam Integer userId) {
        Optional<List<Ticket>> ticketsOpt = userRepo.findById(userId).map(User::getTickets);
        if (ticketsOpt.isPresent()) {
            return ticketsOpt.get();
        }
        return null;
    }

    @GetMapping("/all")
    public List<Ticket> getTimesheet() {
        Iterable<Ticket> allItr = ticketRepo.findAll();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : allItr) {
            tickets.add(ticket);
        }
        return tickets;
    }

}
