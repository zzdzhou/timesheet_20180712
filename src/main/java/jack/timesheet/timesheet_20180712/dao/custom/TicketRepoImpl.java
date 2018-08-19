package jack.timesheet.timesheet_20180712.dao.custom;

import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.Ticket_;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TicketRepoImpl implements TicketRepoCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Ticket> getTickets(String resource, String dateRange) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Ticket> criteria = builder.createQuery(Ticket.class);
        Root<Ticket> root = criteria.from(Ticket.class);
        criteria.select(root);

        ArrayList<Predicate> predicates = new ArrayList<>();
        if (resource != null && !"ALL".equalsIgnoreCase(resource)) {
            predicates.add(builder.equal(root.get(Ticket_.resource), resource));
        }
        if (dateRange != null && !dateRange.trim().equals("")) {
            String[] dates = dateRange.replaceAll(" ", "").split("-");
            LocalDate startDate = LocalDate.parse(dates[0], DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            LocalDate endDate = LocalDate.parse(dates[1], DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            predicates.add(builder.between(root.get(Ticket_.date), startDate, endDate));
        }
        criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
        return em.createQuery(criteria).getResultList();
    }

}
