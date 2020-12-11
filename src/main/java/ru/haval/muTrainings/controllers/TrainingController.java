package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingJoined;
import ru.haval.muTrainings.accessingdatajpa.TrainingJoinedRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingsNamesRepository;

import java.util.List;

@Controller
@RequestMapping("/trainings")
public class TrainingController {
  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  TrainingsNamesRepository trainingsNamesRepository;

  @Autowired
  TrainingRepository trainingRepository;

  @Autowired
  TrainingJoinedRepository trainingJoinedRepository;

  @GetMapping
  public String showTrainingForm(Model model) {
    // List<List<String>> table = new ArrayList<>();
    System.out.println("training");
    for (TrainingJoined training : trainingJoinedRepository.findAll()) {
      System.out.println(training);
    }
    model.addAttribute("trainings", trainingsNamesRepository.findByDelIsFalse());
    model.addAttribute("employees", employeeRepository.findAll());
    return "trainings";
  }

  @RequestMapping(path = "/get", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<TrainingJoined> getTrainings() {
    return trainingJoinedRepository.findAll();
  }
}
