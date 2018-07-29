package jack.timesheet.timesheet_20180712.web.rest.controller;

import jack.timesheet.timesheet_20180712.dao.TicketRepo;
import jack.timesheet.timesheet_20180712.dao.UserRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.util.PaginationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<PaginationList> getTimesheet(@RequestParam(required = false, defaultValue = "0") int offset,
                                                       @RequestParam int limit) {
        Iterable<Ticket> allItr = ticketRepo.findAll();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : allItr) {
            tickets.add(ticket);
        }
        List<Ticket> pageList = tickets.stream().skip(offset).limit(limit).collect(Collectors.toList());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Headers", "application/json");
        return new ResponseEntity<PaginationList>(new PaginationList(tickets.size(), pageList), responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/all2")
    public List<Ticket> getTimesheet() {
        Iterable<Ticket> allItr = ticketRepo.findAll();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : allItr) {
            tickets.add(ticket);
        }
        return tickets;
    }

    @DeleteMapping("/delete")
    public Boolean deleteTicket(@RequestParam Integer ticketId) {
        ticketRepo.deleteById(ticketId);
        return true;
    }

    @PostMapping("/createOrUpdate")
    public void createOrUpdateTicket(@RequestBody Ticket ticket) throws Exception {
        Ticket newTicket = new Ticket();

        Integer id = ticket.getId();
        if (id != null) {
            Optional<Ticket> ticketOpt = ticketRepo.findById(id);
            if (ticketOpt.isPresent()) {
                newTicket = ticketOpt.get();
            }
        }

        newTicket.setDate(ticket.getDate());
        newTicket.setActivity(ticket.getActivity());
        newTicket.setType(ticket.getType());
        newTicket.setResource(ticket.getResource());
        newTicket.setDays(ticket.getDays());
        newTicket.setDescription(ticket.getDescription());
        Optional<User> userOpt = userRepo.findByFullName(ticket.getResource().trim());
        if (userOpt.isPresent()) {
            newTicket.setUser(userOpt.get());
        } else {
            throw new Exception(String.format("User fullname %s doesn't exists", ticket.getResource()));
        }

        ticketRepo.save(newTicket);
    }

}
