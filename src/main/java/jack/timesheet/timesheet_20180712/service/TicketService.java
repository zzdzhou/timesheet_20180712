package jack.timesheet.timesheet_20180712.service;

import jack.timesheet.timesheet_20180712.dao.TicketRepo;
import jack.timesheet.timesheet_20180712.entities.Ticket;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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


    public void exportXlsxFile(List<Ticket> tickets, String filename) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        try(OutputStream os = new FileOutputStream(filename)) {
            XSSFSheet sheet1 = wb.createSheet("sheet1");
            // todo
            Row row = null;
            Cell cell = null;
            for (int r = 0; r < tickets.size(); r++) {
                row = sheet1.createRow(r);
                if (r == 0) {
                    for (int c = 0; c < row.)
                    cell = row.createCell()
                }
            }
            wb.write(os);
        }

    }
}
