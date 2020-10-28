package ru.haval.muTrainings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.haval.muTrainings.accessingdatajpa.Employee;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;

@Slf4j
@SpringBootApplication
public class MuTrainingsApplication {



	public static void main(String[] args) {
		SpringApplication.run(MuTrainingsApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(EmployeeRepository repository) {
		return (args) -> {
			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("****************");
			for (Employee employee : repository.findAll()) {
				log.info(employee.toString());
			}
			log.info("");
		};
	}

}
