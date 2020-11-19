package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.haval.muTrainings.accessingdatajpa.Employee;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingsNamesRepository;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/trainings")
public class TrainingController {
  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  TrainingsNamesRepository trainingsNamesRepository;

  @Autowired
  TrainingRepository trainingRepository;

  @GetMapping
  public String showTrainingForm(Model model) {
    List<List<String>> table = new ArrayList<>();
    for (Employee employee : employeeRepository.findAll()) {
      System.out.println(employee);
    }
    model.addAttribute("trainings", trainingsNamesRepository.findByDelIsFalseOrderByText());
    model.addAttribute("employees", employeeRepository.findAll());
    return "trainings";
  }
}
