package jack.timesheet.timesheet_20180712.service;

import jack.timesheet.timesheet_20180712.dao.TicketRepo;
import jack.timesheet.timesheet_20180712.dao.UserRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import jack.timesheet.timesheet_20180712.entities.User;
import jack.timesheet.timesheet_20180712.util.PaginationList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketService {

    public static final String FILENAME_PATTERN = "/root/jack/webapp/temp/timesheet/TS Richemont_%1$s_%2$s.xlsx";

    private TicketRepo ticketRepo;
    private UserRepo userRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo, UserRepo userRepo) {
        this.ticketRepo = ticketRepo;
        this.userRepo = userRepo;
    }

    public List<Ticket> getTickets(String fullName, String dateRange) {
        return ticketRepo.getTickets(fullName, dateRange);
    }

    public PaginationList convertTicketsToPaginationList(List<Ticket> tickets, int offset, int pageSize) throws Exception {
        if (tickets != null) {
            List<Ticket> pageList = tickets.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
            return new PaginationList<>(tickets.size(), pageList);
        }
        throw new Exception("tickets is null");
    }

    public List<Ticket> getAllTickets() {
        Iterable<Ticket> allItr = ticketRepo.findAll();
        List<Ticket> tickets = new ArrayList<>();
        for (Ticket ticket : allItr) {
            tickets.add(ticket);
        }
        return tickets;
    }

    public void addATicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    public void createOrUpdateTicket(Ticket ticket) throws Exception {
        Ticket newTicket = new Ticket();

        Integer id = ticket.getId();
        if (id != null) {
            Optional<Ticket> ticketOpt = ticketRepo.findById(id);
            if (ticketOpt.isPresent()) {
                newTicket = ticketOpt.get();
            }
        }

        newTicket.setDate(ticket.getDate());
        newTicket.setActivity(ticket.getActivity());
        newTicket.setType(ticket.getType());
        newTicket.setResource(ticket.getResource());
        newTicket.setDays(ticket.getDays());
        newTicket.setDescription(ticket.getDescription());
        Optional<User> userOpt = userRepo.findByFullName(ticket.getResource().trim());
        if (userOpt.isPresent()) {
            newTicket.setUser(userOpt.get());
        } else {
            throw new Exception(String.format("User fullname %s doesn't exists", ticket.getResource()));
        }

        ticketRepo.save(newTicket);
    }

    public void deleteTicketById(Integer ticketId) {
        ticketRepo.deleteById(ticketId);
    }

    public List<String> getAllResources() {
        return ticketRepo.findResouceDistinct();
    }

    /*
     * tool methods
     * -----------------------------
     */


    /**
     * to use
     * @param tickets
     * @param filename
     * @param excludeFields
     * @throws IOException
     */
    public void exportExcel(List<Ticket> tickets, String filename, String[] excludeFields) throws IOException {

        List<Field> fields = new ArrayList<>(Arrays.asList(Ticket.class.getDeclaredFields()));

        // remove excluded fields
        List<Field> excludeList = new ArrayList<>();
        if (excludeFields != null && excludeFields.length > 0) {
            for (Field field : fields) {
                for (String exclude : excludeFields) {
                    if (exclude.equals(field.getName())) {
                        excludeList.add(field);
                        break;
                    }
                }
            }
        }
        fields.removeAll(excludeList);

        if (fields.size() > 0 && tickets != null && tickets.size() > 0) {
            XSSFWorkbook wb = new XSSFWorkbook();
            try (OutputStream os = new FileOutputStream(filename)) {
                XSSFSheet sheet1 = wb.createSheet("sheet1");
                // todo
                Row row;
                Cell cell;
                Field field;
                Ticket ticket = null;
                // transfer each ticket in tickets to one row in excel
                for (int r = 0; r <= tickets.size(); r++) {
                    row = sheet1.createRow(r);
                    if (r > 0) {
                        ticket = tickets.get(r-1);
                    }
                    // tansfer each field in a ticket to a cell in excel
                    for (int c = 0; c < fields.size(); c++) {
                        cell = row.createCell(c);
                        field = fields.get(c);
                        if (r == 0) {
                            cell.setCellValue(field.getName());
                        } else {
                            Class<?> type = field.getType();
                            field.setAccessible(true);
                            if (type == String.class) {
                                cell.setCellValue((String) field.get(ticket));
                            } else if (type == Integer.class) {
                                cell.setCellValue(field.getInt(ticket));
                            } else if (type == float.class) {
                                cell.setCellValue(field.getFloat(ticket));
                            } else if (type == java.sql.Date.class) {
                                Calendar cale = Calendar.getInstance();
                                cale.setTime((java.sql.Date) field.get(ticket));
                                cell.setCellValue(cale);
                            } else if (type == LocalDate.class) {
                                LocalDate localDate = (LocalDate) field.get(ticket);
                                Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                                cell.setCellValue(Date.from(instant));
                            }
                        }
                    }
                }
                wb.write(os);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
