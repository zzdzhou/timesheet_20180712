package jack.timesheet.timesheet_20180712.web.controller;

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

    @GetMapping("/secure")
    public String ticket(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        model.addAttribute("tickets", userService.getAllTicketsOfAnUser(userId));
        return "timesheet";
    }

    @PostMapping("/add/secure")
    public String addATicket(Ticket ticket, Model model, /*todo*/User user) {
        // todo
//        ticket.setDate(new Date());

        // todo
        ticket.setResource(user.getFullName());
        ticketService.addATicket(ticket);
        return "timesheet";
    }
}
