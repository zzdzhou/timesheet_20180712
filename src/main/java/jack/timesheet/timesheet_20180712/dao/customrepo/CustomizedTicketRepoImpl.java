package jack.timesheet.timesheet_20180712.dao.customrepo;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;

public class CustomizedTicketRepoImpl implements CustomizedTicketRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Ticket> findByUserAndDateBetweenIfFound(User user, Date start, Date end) {

        if (user != null) {

        }
        return null;
    }

}
