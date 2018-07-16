package jack.timesheet.timesheet_20180712.web.controller;

import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public void signUp(User user) throws Exception {
        userService.createAnUser(user);
    }

    @PostMapping("/authentication")
    public String login(User user, Model model) {
        Optional<User> userOpt = userService.authenticateAnUser(user.getUsername(), user.getPassword());
        if (userOpt.isPresent()) {
            model.addAttribute("username", user.getUsername());
        }
        return "timesheet";
    }
}
