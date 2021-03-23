package ru.haval.muTrainings.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.NameValue;
import ru.haval.Select2;
import ru.haval.muTrainings.accessingdatajpa.EditEmployee;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingHmmrMuStaffRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsersRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingFilterRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingHmmrMuStaff;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingFilter;

@Controller
@RequestMapping("employee")
public class EditEmployeeController {
  @Autowired
  WorkRecordingFilterRepository workRecordingRepository;
  @Autowired
  WorkRecordingHmmrMuStaffRepository workRecordingHmmrMuStaffRepository;
  @Autowired
  WorkRecordingUsersRepository workRecordingUsersRepository;

  @GetMapping
  public String showAddEmployee() {
    return "employee";
  }

  @RequestMapping(path = "/add", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public EditEmployee addEmployee(@RequestBody EditEmployee editEmployee) {
    System.out.println(editEmployee);
    return editEmployee;
  }

  @RequestMapping(path = "/positions", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Select2> getPositions() {
    List<Select2> positions = new ArrayList<>();
    Select2 tmp;
    for (WorkRecordingHmmrMuStaff i : workRecordingHmmrMuStaffRepository.getPositions()) {
      tmp = new Select2();
      tmp.setId(i.getId());
      tmp.setText(i.getPositionRus());
      positions.add(tmp);
    }
    return positions;
  }

  @RequestMapping(path = "/enPositions", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Select2> getEnPositions() {
    List<Select2> enPositions = new ArrayList<>();
    Select2 tmp;
    for (WorkRecordingHmmrMuStaff i : workRecordingHmmrMuStaffRepository.getEnPosition()) {
      tmp = new Select2();
      tmp.setId(i.getId());
      tmp.setText(i.getPosition());
      enPositions.add(tmp);
    }
    return enPositions;
  }

  @RequestMapping(path = "/checkForUnique", consumes = "application/json", produces = "application/json")
  @ResponseBody
  Boolean checkForUnique(@RequestBody NameValue nameValue) {
    switch (nameValue.getName()) {
    case "login":
      return workRecordingUsersRepository.existsByLogin(nameValue.getValue());

    // break;
    }
    System.out.println(nameValue.getName());
    return true;
  }

  @ModelAttribute("accesses")
  public List<String> getAccesses() {
    List<String> list_rule = new ArrayList<>();
    String USER_ROLE = "Administrator";
    if (USER_ROLE.equals("Administrator")) {
      list_rule.add("Administrator");
      list_rule.add("Group Leader");
      list_rule.add("Team Leader");
      list_rule.add("Engineer");
      list_rule.add("Technician");
    }
    if (USER_ROLE.equals("Group Lead")) {
      list_rule.add("Group Leader");
      list_rule.add("Team Leader");
      list_rule.add("Engineer");
      list_rule.add("Technician");
    }
    if (USER_ROLE.equals("Team Lead")) {
      list_rule.add("Team Leader");
      list_rule.add("Engineer");
      list_rule.add("Technician");
    }
    if (USER_ROLE.equals("Engineer")) {
      list_rule.add("Engineer");
      list_rule.add("Technician");
    }
    return list_rule;
  }

  @RequestMapping(path = "/get", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<WorkRecordingFilter> getEmployee(@RequestBody EditEmployee editEmployee) {
    return workRecordingRepository.findAll();
  }
}
