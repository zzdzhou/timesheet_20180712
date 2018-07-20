package jack.timesheet.timesheet_20180712.service;

import jack.timesheet.timesheet_20180712.dao.UserRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.List;
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
            throw new Exception("Username, fullname, password can't be empty!");
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

    public Optional<List<Ticket>> getAllTicketsOfAnUser(Integer userId) {
        return userRepo.findById(userId).map(User::getTickets);
    }

    public Optional<String> getFullNameById(Integer userId) {
        return userRepo.findById(userId).map(User::getFullName);
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepo.findById(userId);
    }
}
