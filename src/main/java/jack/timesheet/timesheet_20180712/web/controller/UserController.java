package jack.timesheet.timesheet_20180712.web.controller;

import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.service.UserService;
import jdk.nashorn.internal.ir.IfNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
    public String login(@RequestParam(required = false) String msg, @RequestParam(required = false) String error, Model model) {
        if (msg != null) {
            model.addAttribute("msg", msg);
        }
        if (error != null) {
            model.addAttribute("error", error);
        }
        return "login";
    }

    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(User user, Model model) throws Exception {
        userService.createAnUser(user);
        // todo
        model.addAttribute("msg", "You have registered an account successfully! Please login");
        return "redirect:/user/login";
    }

    @PostMapping("/authentication")
    public String authenticate(HttpSession session, User user, Model model) {
        Optional<User> userOpt = userService.authenticateAnUser(user.getUsername(), user.getPassword());
        if (userOpt.isPresent()) {
            // 第一次登录验证通过， 设置session userId
            session.setAttribute("userId", userOpt.get().getId());
            session.setAttribute("fullName", userOpt.get().getFullName());
            return "redirect:/ticket/timesheet/secure";
        }
        model.addAttribute("error", "Invalid username or password");
        return "redirect:/user/login";
    }
}
