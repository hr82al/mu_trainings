/**
 * 
 */
package ru.haval.muTrainings.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.haval.muTrainings.accessingdatajpa.DepartmentsRepository;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;
import ru.haval.muTrainings.accessingdatajpa.PositionsRepository;
import ru.haval.muTrainings.accessingdatajpa.UsersPosition;
import ru.haval.muTrainings.accessingdatajpa.UsersPositionRepository;
import ru.haval.muTrainings.accessingdatajpa.usersPositionRepository;

import java.util.List;

/**
 * @author Aleksandr Khomov. hr82al@gmail.com
 */

@Controller
@RequestMapping("/muTrainings")
public class MuTrainingsController {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PositionsRepository positionsRepository;

	@Autowired
	UsersPositionRepository usersPositionRepository;

	@Autowired
	DepartmentsRepository departmentsRepository;

	/*
	 * @GetMapping("/mu_trainings/") public String muTrainings() { return
	 * "muTrainings"; }
	 */
	// @GetMapping("/mu_trainings")
	@GetMapping
	public String showMUTrainingsForm(Model model) {
		model.addAttribute("employees", employeeRepository.findAll());
		model.addAttribute("usersPositions", usersPositionRepository.findAll());
		model.addAttribute("positions", positionsRepository.findByDelIsFalse());
		model.addAttribute("departments", departmentsRepository.findByDelIsFalse());
		return "muTrainings";
	}

	/*
	 * @PostMapping("/mu_trainings") public String processMUTrainings(Position
	 * position, Model model) { model.addAttribute("employees",
	 * employeeRepository.findAll()); return "muTrainings"; }
	 */

	@PostMapping(path = "/change", consumes = "application/json", produces = "application/json")
	@ResponseBody
	UsersPosition changUsersPosition(@RequestBody UsersPosition usersPosition) {
		return usersPositionRepository.save(usersPosition);
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

}