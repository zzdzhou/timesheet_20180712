package jack.timesheet.timesheet_20180712.service;

import jack.timesheet.timesheet_20180712.dao.UserRepo;
import jack.timesheet.timesheet_20180712.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.Optional;

@Service
public class UserService {
    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User createAnUser(User user) throws Exception {
        if (isEmpty(user.getUsername()) || isEmpty(user.getPassword()) || isEmpty(user.getFullName())) {
            throw new Exception("Empty username or password is not allowed");
        }
        return userRepo.save(user);
    }

    public Optional<User> authenticateAnUser(String username, String password) {
        Iterable<User> userIter = userRepo.findAllByUsername(username);
        for (User user : userIter) {
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Iterable<User> getAllTicketsOfAnUser(String username) {
        return userRepo.findAllByUsername(username);
    }
}
