/**
 * Таблица "Список сотрудников"
 */
package ru.haval.muTrainings.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.haval.muTrainings.accessingdatajpa.Employee;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;
import ru.haval.muTrainings.accessingdatajpa.PositionsRepository;
import java.util.List;

/**
 * @author Aleksandr Khomov. hr82al@gmail.com
 */

@Controller
@RequestMapping
public class MuTrainingsController {

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PositionsRepository positionsRepository;

	/*
	 * @GetMapping("/mu_trainings/") public String muTrainings() { return
	 * "muTrainings"; }
	 */
	// @GetMapping("/mu_trainings")
	@GetMapping("/muTrainings")
	public String showMUTrainingsForm(Model model) {
		model.addAttribute("pos", new Tmp());
		model.addAttribute("employees", employeeRepository.findAll());
		return "muTrainings";
	}

	/*
	 * @PostMapping("/mu_trainings") public String processMUTrainings(Position
	 * position, Model model) { model.addAttribute("employees",
	 * employeeRepository.findAll()); return "muTrainings"; }
	 */

	@PostMapping("/change_position")
	public String processPositions(Tmp tmp, Model model) {
		// model.addAttribute("position", positionsRepository.findAll());
		System.out.println("pos:");
		System.out.println(tmp.getPos());
		return "muTrainings";
	}

	/*
	 * @PostMapping(value = "/get_positions", produces = "application/json")
	 * 
	 * @ResponseBody public String getPositions() { return "{x :\"x\"}"; }
	 */
	/*
	 * @GetMapping("/greeting") public String greetingForm(Model model) {
	 * model.addAttribute("greeting", new Greeting()); return "greeting"; }
	 * 
	 * @PostMapping("/greeting") public String greetingSubmit(@ModelAttribute
	 * Greeting greeting, Model model) { model.addAttribute("greeting", greeting);
	 * return "result"; }
	 */

	// Get table for showing
	@RequestMapping(path = "/muTrainings/get", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<Employee> getEmployees() {
		return employeeRepository.findByOrderByFioAsc();
	}
}

@Data
@RequiredArgsConstructor
class Tmp {
	private String pos;
}
