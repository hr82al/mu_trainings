/**
 * 
 */
package ru.haval.muTrainings.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.haval.muTrainings.accessingdatajpa.Employee;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;
import ru.haval.muTrainings.accessingdatajpa.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 	Aleksandr Khomov. hr82al@gmail.com
 *
 */

@Slf4j
@Controller
@RequestMapping("/mu_trainings")
public class MuTrainingsController {

	@Autowired
	EmployeeRepository employeeRepository;

/*	@GetMapping("/mu_trainings/")
	public String muTrainings() {
		return "muTrainings";
	}*/
	@GetMapping
	public String showMUTrainingsForm(Model model) {
		model.addAttribute("position", new Position());
		List<Employee> employees = new ArrayList<>();
		System.out.println("000");
		//employeeRepository.findByUser_del(0).forEach(employees::add);
		model.addAttribute("employees", employeeRepository.findAll());
		return "muTrainings";
	}

	@PostMapping
	public String processMUTrainings(Position position) {
		System.out.println("fds");
		log.info("pos" + position.getPos());
		return "muTrainings";
	}
/*
	@GetMapping("/greeting")
	public String greetingForm(Model model) {
		model.addAttribute("greeting", new Greeting());
		return "greeting";
	}

	@PostMapping("/greeting")
	public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
		model.addAttribute("greeting", greeting);
		return "result";
	}
*/

}
