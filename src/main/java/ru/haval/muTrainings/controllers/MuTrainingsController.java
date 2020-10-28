/**
 * 
 */
package ru.haval.muTrainings.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.haval.muTrainings.accessingdatajpa.Employee;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author 	Aleksandr Khomov. hr82al@gmail.com
 *
 */

@Slf4j
@Controller
@RequestMapping("/mu_trainings/")
public class MuTrainingsController {

	@Autowired
	EmployeeRepository employeeRepository;

/*	@GetMapping("/mu_trainings/")
	public String muTrainings() {
		return "muTrainings";
	}*/
	@GetMapping
	public String showMUTrainingsForm(Model model) {
		Iterable<Employee> employeeStream = employeeRepository.findAll();
		model.addAttribute("trainings", employeeStream);
		return "muTrainings";
	}
}
