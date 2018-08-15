package jack.timesheet.timesheet_20180712.dao;

import jack.timesheet.timesheet_20180712.dao.custom.TicketRepositoryCustom;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Integer>, TicketRepositoryCustom {

    Ticket save(Ticket ticket);

    void deleteById(Integer id);

    Optional<Ticket> findById(Integer id);

    List<Ticket> findAll();

    @Query(value = "select distinct t.resource from Ticket t order by t.resource asc", nativeQuery = false)
    List<String> findResouceDistinct();
}
