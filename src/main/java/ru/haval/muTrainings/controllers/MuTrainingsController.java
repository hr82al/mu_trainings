/**
 * Таблица "Список сотрудников"
 */
package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.haval.muTrainings.accessingdatajpa.Employee;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;
import ru.haval.muTrainings.accessingdatajpa.Employees;
import ru.haval.muTrainings.accessingdatajpa.EmployeesRepository;
import ru.haval.muTrainings.accessingdatajpa.PositionsRepository;
import java.util.List;

/**
 * @author Aleksandr Khromov. hr82al@gmail.com
 */

@Controller
@RequestMapping
public class MuTrainingsController {

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PositionsRepository positionsRepository;
	@Autowired
	EmployeesRepository employeesRepository;

	// @GetMapping("/mu_trainings")
	@GetMapping("/muTrainings")
	public String showMUTrainingsForm(Model model) {
		// model.addAttribute("pos", new Tmp());
		model.addAttribute("employees", employeeRepository.findAll());
		return "muTrainings";
	}

	@PostMapping("/change_position")
	public String processPositions(Model model) {
		return "muTrainings";
	}

	@RequestMapping(path = "/muTrainings/add", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Employees addEmployees(@RequestBody Employees employees) {
		return employeesRepository.save(employees);
	}

	@RequestMapping(path = "/muTrainings/resetUser", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Employee resetUser(@RequestBody Employee employee) {
		employeesRepository.deleteById(employee.getUserId());
		return employee;
	}

	// Get table for showing
	@RequestMapping(path = "/muTrainings/get", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<Employee> getEmployees() {
		return employeeRepository.findByOrderByDepartmentIdAscFioAsc();
	}

	@RequestMapping(path = "/muTrainings/get_by_department", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<Employee> findByDepartmentId(@RequestBody Employee employee) {
		return employeeRepository.findByDepartmentId(employee.getDepartmentId());
	}
}
