package jack.timesheet.timesheet_20180712.dao.custom;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TicketRepositoryImpl implements TicketRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Ticket> getTickets(String fullName, String dateRange) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteria = builder.createQuery(Ticket.class);

        Root<Ticket> root = criteria.from(Ticket.class);
        criteria.select(root);

        Predicate[] predicates = new Predicate[2];
        if (fullName != null && fullName != "ALL") {
            predicates[0] = builder.equal(root.get(Ticket_.resource), fullName);
        }
        if (dateRange != null) {
            String[] split = dateRange.split(" - ");
            if (split.length == 2) {
                LocalDate startDate = LocalDate.parse(split[0], DateTimeFormatter.ofPattern("uuuu.MM.DD"));
                LocalDate endDate = LocalDate.parse(split[1], DateTimeFormatter.ofPattern("uuuu.MM.DD")).plusDays(1L);
                predicates[1] = builder.between(root.get(Ticket_.date), startDate, endDate);
            }
        }
        criteria.where(builder.and(predicates));
        List<Ticket> tickets = entityManager.createQuery(criteria).getResultList();
        return tickets;
    }

}
