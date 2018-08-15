package jack.timesheet.timesheet_20180712.Service;

import jack.timesheet.timesheet_20180712.Timesheet20180712Application;
import jack.timesheet.timesheet_20180712.service.TicketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Timesheet20180712Application.class) // 加载ApplicationContext @SpringBootTest或@SpringApplicationConfiguration(1.4.0过期)
public class TicketServiceTest {

    @Autowired  // 自动注入
    private TicketService ticketService;

    public static final String FILENAME_PATTERN = "C:\\Users\\D1M\\Desktop\\zzd_tools\\TS Richemont_%1$s_%2$s.xlsx";

    @Test
    public void exportExcel() throws IOException {
        // initial parameters
//        ticketService.exportTikets("Rookie HAN");
    }

}
