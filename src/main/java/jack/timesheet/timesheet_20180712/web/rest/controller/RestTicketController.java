package jack.timesheet.timesheet_20180712.web.rest.controller;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.service.TicketService;
import jack.timesheet.timesheet_20180712.util.PaginationList;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/rest/ticket")
public class RestTicketController {

    private TicketService ticketService;

    @Autowired
    public RestTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/all2")
    public List<Ticket> getTimesheet() {
        return ticketService.getAllTickets();
    }

    @GetMapping
    public ResponseEntity<PaginationList> getTimesheet(@RequestParam(required = false) String fullName,
                                                       @RequestParam(required = false) String dateRange,
                                                       @RequestParam(required = false, defaultValue = "0") int offset,
                                                       @RequestParam int pageSize) throws Exception {

        List<Ticket> tickets = ticketService.getTickets(fullName, dateRange);
        PaginationList paginationList = ticketService.convertTicketsToPaginationList(tickets, offset, pageSize);
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
    public ResponseEntity<byte[]> exportTickets(@RequestParam(required = false) String fullName,
                                      @RequestParam(required = false) String dateRange) throws Exception {
        List<Ticket> tickets = ticketService.getTickets(fullName, dateRange);
        if (tickets != null && tickets.size() > 0) {
            String filename = String.format(TicketService.FILENAME_PATTERN, fullName, dateRange);
            String excludedFields[] = {"id", "user"};
            ticketService.exportExcel(tickets, filename, excludedFields);
            File file = new File(filename);
            if (file.exists()) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachement;filename=" + new File(filename).getName());
                httpHeaders.set(HttpHeaders.CONTENT_ENCODING, "utf-8");
                return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), httpHeaders, HttpStatus.OK);
            }
        }
        return null;
    }

}
