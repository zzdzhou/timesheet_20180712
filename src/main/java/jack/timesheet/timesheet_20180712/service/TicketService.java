package jack.timesheet.timesheet_20180712.service;

import jack.timesheet.timesheet_20180712.dao.TicketRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.Class;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
public class TicketService {

    private TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public void addATicket(Ticket ticket) {
        ticketRepo.save(ticket);
    }


    public void exportXlsxFile(List<Ticket> tickets, String filename, String[] excludeFields) throws IOException {

        List<Field> fields = Arrays.asList(Ticket.class.getFields());
        // remove excluded fields
        if (excludeFields != null && excludeFields.length > 0) {
            for (Field field : fields) {
                for (String exclude : excludeFields) {
                    if (exclude.equals(field.getName())) {
                        fields.remove(field);
                    }
                    continue;
                }
            }
        }

        if (fields.size() > 0) {
            XSSFWorkbook wb = new XSSFWorkbook();
            try (OutputStream os = new FileOutputStream(filename)) {
                XSSFSheet sheet1 = wb.createSheet("sheet1");
                // todo
                Row row = null;
                Cell cell = null;
                // transfer each ticket in tickets to one row in excel
                for (int r = 0; r < tickets.size(); r++) {
                    row = sheet1.createRow(r);
                    // tansfer each field in a ticket to a cell in excel
                    for (int c = 0; c < fields.size(); c++) {
                        cell = row.createCell(c);
                        if (r == 0) {
                            cell.setCellValue(fields.get(c).getName());
                        } else {
                            cell.setCellValue(fields.get(c).);
                        }
                    }
                }
                wb.write(os);
            }
        }

    }
}
