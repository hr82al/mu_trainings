package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.haval.muTrainings.accessingdatajpa.Department;
import ru.haval.muTrainings.accessingdatajpa.DepartmentsRepository;
import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentsController {
	@Autowired
	DepartmentsRepository departmentsRepository;

	@GetMapping
	public String showDepartmentForm(Model model) {
		model.addAttribute("departments", departmentsRepository.findByDelIsFalseOrderByTextAsc());
		return "departments";
	}

	@PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void addDepartment(@RequestBody Department department) {
		if (department.getText() != "") {
			departmentsRepository.addDepartmentQuery(department.getText());
		}
	}

	@PostMapping(path = "/get", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Department getDepartment(@RequestBody Department department) {
		return departmentsRepository.findByText(department.getText()).get(0);
	}

	@PostMapping(path = "/set", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Department setDepartment(@RequestBody Department department) {
		return departmentsRepository.save(department);
	}

	@PostMapping(path = "/get_json", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<Department> getDepartments() {
		return departmentsRepository.findAll();
	}

	@PostMapping(path = "/del", consumes = "application/json", produces = "application/json")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delDepartment(@RequestBody Department department) {
		departmentsRepository.delById(department.getId());
	}

	@PostMapping(path = "/findByDepartment", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Department findByDepartment(@RequestBody Department department) {
		if (department.getText() != "") {
			return departmentsRepository.findByText(department.getText()).get(0);
		}
		return null;
	}

	@PostMapping(path = "/changeDel", consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void changeDel(@RequestBody Department department) {
		departmentsRepository.updateByText(department.getText(), false);
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public @ResponseBody Department updateDepartment(@RequestBody Department department) {
		return departmentsRepository.save(department);
	}
}
