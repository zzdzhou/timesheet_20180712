package jack.timesheet.timesheet_20180712.web.controller;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import jack.timesheet.timesheet_20180712.dao.UserRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.service.TicketService;
import jack.timesheet.timesheet_20180712.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    private UserService userService;

    private TicketService ticketService;

    @Autowired
    public TicketController(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("/add/secure")
    public String addTicket(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        Optional<String> fullNameOpt = userService.getFullNameById(userId);
        if (fullNameOpt.isPresent()) {
            model.addAttribute("fullName", fullNameOpt.get());
        }
        return "ticket";
    }

    @GetMapping("/secure")
    public String ticket(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        Optional<List<Ticket>> ticketsOpt = userService.getAllTicketsOfAnUser(userId);
        if (ticketsOpt.isPresent()) {
            model.addAttribute("tickets", ticketsOpt.get());
        }
        return "timesheet";
    }

    @PostMapping("/add/secure")
    public String addATicket(Ticket ticket, Model model, HttpSession session) throws Exception {
        // todo
        ticket.setDate(new Date(new java.util.Date().getTime()));
        Integer userId = (Integer) session.getAttribute("userId");
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isPresent()) {
            ticket.setUser(userOpt.get());
            ticketService.addATicket(ticket);
            return "redirect:/ticket/secure";
        }

        // todo-won't do
        /*Integer userId = (Integer) session.getAttribute("userId");
        Optional<String> fullNameOpt = userService.getFullNameById(userId);
        if (fullNameOpt.isPresent()) {
            ticket.setResource(fullNameOpt.get());
        }*/
        throw new Exception("The user id " + userId + " is not exist!");

    }
}
