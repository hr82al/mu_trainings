package ru.haval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // (scanBasePackages = { "ru.haval.muTrainings", "ru.haval.workRecording" })
public class MuTrainingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuTrainingsApplication.class, args);
	}

	/*
	 * @Bean public CommandLineRunner demo(EmployeeRepository repository) { return
	 * (args) -> { // fetch all customers
	 * log.info("Customers found with findAll():"); log.info("****************");
	 * for (Employee employee : repository.findAll()) {
	 * log.info(employee.toString()); } log.info(""); }; }
	 */

}
