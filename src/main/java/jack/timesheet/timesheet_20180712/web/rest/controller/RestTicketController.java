package jack.timesheet.timesheet_20180712.web.rest.controller;

import jack.timesheet.timesheet_20180712.dao.TicketRepo;
import jack.timesheet.timesheet_20180712.dao.UserRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.service.TicketService;
import jack.timesheet.timesheet_20180712.util.PaginationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/ticket")
public class RestTicketController {

    private TicketService ticketService;

    @Autowired
    public RestTicketController(TicketRepo ticketRepo, TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /*@GetMapping
    public List<Ticket> getTimesheet(@RequestParam Integer userId) {
        return ticketService.getTickets(userId);
    }*/

    @GetMapping("/all2")
    public List<Ticket> getTimesheet() {
        return ticketService.getAllTickets();
    }

    @GetMapping
    public ResponseEntity<PaginationList> getTimesheet(@RequestParam(required = false) String fullName,
                                                       @RequestParam(required = false) String dateRange,
                                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                                       @RequestParam int limit) {

        PaginationList paginationList = ticketService.getTicketPaginationList(offset, limit);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Headers", "application/json");
        return new ResponseEntity<PaginationList>(paginationList, responseHeaders, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public Boolean deleteTicket(@RequestParam Integer ticketId) {
        ticketService.deleteTicketById(ticketId);
        return true;
    }

    @PostMapping("/createOrUpdate")
    public void createOrUpdateTicket(@RequestBody Ticket ticket) throws Exception {
        ticketService.createOrUpdateTicket(ticket);
    }

    @GetMapping("/export")
    public void exportTickets(@RequestParam(required = false) String fullName,
                              @RequestParam(required = false) String dateRange) throws IOException {
//        ticketService.exportTikets(fullname, year, month);
    }

}
