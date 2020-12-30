package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.muTrainings.accessingdatajpa.Employee;
import ru.haval.muTrainings.accessingdatajpa.EmployeeRepository;
import ru.haval.muTrainings.accessingdatajpa.LastDatesRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingJoined;
import ru.haval.muTrainings.accessingdatajpa.TrainingJoinedRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingName;
import ru.haval.muTrainings.accessingdatajpa.TrainingRepository;
import ru.haval.muTrainings.accessingdatajpa.TrainingsNamesRepository;
import ru.haval.muTrainings.beans.Date;
import ru.haval.muTrainings.accessingdatajpa.LastDate;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/trainings")
public class TrainingController {
  private List<LastDate> dates;

  @Autowired
  EmployeeRepository employeeRepository;

  @Autowired
  TrainingsNamesRepository trainingsNamesRepository;

  @Autowired
  TrainingRepository trainingRepository;

  @Autowired
  TrainingJoinedRepository trainingJoinedRepository;

  @Autowired
  LastDatesRepository lastDatesRepository;

  @GetMapping
  public String showTrainingForm(Model model) {
    // List<List<String>> table = new ArrayList<>();
    System.out.println("training");
    List<Employee> employees = employeeRepository.findAll();
    List<TrainingName> trainings = trainingsNamesRepository.findByDelIsFalse();
    model.addAttribute("trainings", trainings);
    model.addAttribute("employees", employees);
    dates = lastDatesRepository.findAll();

    ArrayList<ArrayList<Date>> aDates = new ArrayList<>();

    // init table with default values
    for (int row = 0; row < employees.size(); row++) {
      ArrayList<Date> tmp = new ArrayList<>(trainings.size());
      for (int column = 0; column < trainings.size(); column++) {
        tmp.add(new Date("", Date.NONE));
      }
      aDates.add(tmp);
    }

    int[] trainingIDs = new int[trainings.size()];
    for (int i = 0; i < trainings.size(); i++) {
      trainingIDs[i] = trainings.get(i).getId();
    }
    int[] employIDs = new int[employees.size()];
    for (int i = 0; i < employees.size(); i++) {
      employIDs[i] = employees.get(i).getUserId();
    }
    for (LastDate lastDate : dates) {
      aDates.get(lastDate.getUserId()).set(lastDate.getTrainingId(),
          new Date(lastDate.getLastDate().toString(), Date.NONE));
    }
    model.addAttribute("dates", aDates);
    // ArrayList<ArrayList<String>> aDates = new ArrayList<>(employees.size());
    // for (int i = 0; i < employees.size(); i++) {
    // aDates.add(new ArrayList<String>(trainings.size()));
    // }

    // for (LastDates lastDate : dates) {
    // try {
    // aDates.get(lastDate.getUserId()).set(lastDate.getUserId(),
    // lastDate.getLastDate().toString());
    // } catch (IndexOutOfBoundsException e) {
    // System.err.println(e.getMessage());
    // }
    // }
    // model.addAttribute("dates", aDates);
    // for (Employee employee: employeeRepository.findAll()) {
    // ArrayList<LocalDate> rowDates = new ArrayList<>();
    // for (TrainingName trainingName: trainingsNamesRepository.findAll()) {
    // aDates.set(employee.getUserId(), rowDates.set(trainingName.getId(), ))
    // }
    // }
    // model.addAttribute("dates", LastDatesRepository.findAll());
    return "trainings";
  }

  private LocalDate getTrainingDate(Employee employee, TrainingName trainingName) {

    return null;
  }

  @RequestMapping(path = "/get", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<TrainingJoined> getTrainings() {
    return trainingJoinedRepository.findAll();
  }

  @RequestMapping(path = "/getDates", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<LastDate> getDates() {
    return lastDatesRepository.findAll();
  }
}
