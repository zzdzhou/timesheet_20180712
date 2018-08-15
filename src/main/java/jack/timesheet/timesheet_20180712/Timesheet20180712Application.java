package jack.timesheet.timesheet_20180712;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(value = "jack.timesheet.timesheet_20180712.dao", repositoryImplementationPostfix = "Impl")
public class Timesheet20180712Application {

	public static void main(String[] args) {
		SpringApplication.run(Timesheet20180712Application.class, args);
	}
}
