package jack.timesheet.timesheet_20180712.dao;

import jack.timesheet.timesheet_20180712.entities.User;
import org.springframework.data.repository.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepo extends Repository<User, Integer> {

    User save(User user);

    Optional<User> findById(Integer id);

    Iterable<User> findAllByUsername(String username);

    Optional<User> findByFullName(String fullname);
}
