package jack.timesheet.timesheet_20180712.web.controller;

import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signUpPage")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(User user) throws Exception {
        userService.createAnUser(user);
        // todo
        return "timesheet";
    }

    @PostMapping("/authentication")
    public String authenticate(User user, Model model) {
        Optional<User> userOpt = userService.authenticateAnUser(user.getUsername(), user.getPassword());
        if (userOpt.isPresent()) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("tickets", userOpt.get().getTickets());
            return "timesheet";
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
}
