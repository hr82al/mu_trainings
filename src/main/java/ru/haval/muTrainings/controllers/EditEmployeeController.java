package ru.haval.muTrainings.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.NameValue;
import ru.haval.Select2;
import ru.haval.muTrainings.accessingdatajpa.EditEmployee;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaffRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsers;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsersRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingFilterRepository;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaff;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingFilter;

@Controller
@RequestMapping("employee")
public class EditEmployeeController {
  @Autowired
  WorkRecordingFilterRepository workRecordingRepository;
  @Autowired
  HmmrMuStaffRepository hmmrMuStaffRepository;
  @Autowired
  WorkRecordingUsersRepository workRecordingUsersRepository;

  // @GetMapping
  // public String showAddEmployee() {
  // return "employee";
  // }

  @GetMapping
  public String showAddEmployee(@RequestParam(required = false) String userId, Model model) {
    EditEmployee employee = new EditEmployee();
    if (userId != null) {
      Long id = Long.parseLong(userId);
      employee.setUserId(id);
      Optional<WorkRecordingUsers> userOptional = workRecordingUsersRepository.findById(id);
      if (!userOptional.isEmpty()) {
        WorkRecordingUsers user = userOptional.get();
        employee.setWorkRecordingUser(user);
      }
      Optional<HmmrMuStaff> staffOptional = hmmrMuStaffRepository.findByUserId(employee.getUserId());
      if (!staffOptional.isEmpty()) {
        HmmrMuStaff staff = staffOptional.get();
        employee.setHmmrMuStaff(staff);
      }
    }
    model.addAttribute("employee", employee);
    return "employee";
  }

  @RequestMapping(path = "/add", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public EditEmployee addEmployee(@RequestBody EditEmployee employee) {
    WorkRecordingUsers user = new WorkRecordingUsers();
    user = employee.getWorkRecordingUsers();
    if (employee.getUserId() != null) {
      user.setId(employee.getUserId());
    }
    WorkRecordingUsers newUser = workRecordingUsersRepository.save(user);
    employee.setUserId(newUser.getId());
    HmmrMuStaff staff = new HmmrMuStaff();
    staff = employee.getHmmrMuStaff();
    if (newUser.getId() != null) {
      Optional<HmmrMuStaff> tmpOptional = hmmrMuStaffRepository.findByUserId(newUser.getId());
      if (!tmpOptional.isEmpty()) {
        staff.setId(tmpOptional.get().getId());
      }
    }
    hmmrMuStaffRepository.save(staff);
    return employee;
  }

  @RequestMapping(path = "/ruPositions", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Select2> getRuPositions() {
    List<Select2> positions = new ArrayList<>();
    Select2 tmp;
    for (HmmrMuStaff i : hmmrMuStaffRepository.getRuPositions()) {
      tmp = new Select2();
      tmp.setId(i.getId());
      tmp.setText(i.getRuPosition());
      positions.add(tmp);
    }
    return positions;
  }

  @RequestMapping(path = "/enPositions", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Select2> getEnPositions() {
    List<Select2> enPositions = new ArrayList<>();
    Select2 tmp;
    for (HmmrMuStaff i : hmmrMuStaffRepository.getEnPosition()) {
      tmp = new Select2();
      tmp.setId(i.getId());
      tmp.setText(i.getEnPosition());
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
    case "password":
      return workRecordingUsersRepository.existsByPassword(nameValue.getValue());
    case "staffId":
      return hmmrMuStaffRepository.existsByStaffId(nameValue.getValue());
    case "userLettersId":
      return hmmrMuStaffRepository.existsByUserLettersId(nameValue.getValue());
    }
    return false;
  }

  @ModelAttribute("accesses")
  public List<String> getAccesses() {
    List<String> list_rule = new ArrayList<>();
    String USER_ROLE = "Administrator";
    if (USER_ROLE.equals("Administrator")) {
      list_rule.add("Administrator");
      list_rule.add("Group Lead");
      list_rule.add("Team Lead");
      list_rule.add("Engeneer");
      list_rule.add("Technics");
    }
    if (USER_ROLE.equals("Group Lead")) {
      list_rule.add("Group Lead");
      list_rule.add("Team Lead");
      list_rule.add("Engeneer");
      list_rule.add("Technics");
    }
    if (USER_ROLE.equals("Team Lead")) {
      list_rule.add("Team Lead");
      list_rule.add("Engeneer");
      list_rule.add("Technics");
    }
    if (USER_ROLE.equals("Engeneer")) {
      list_rule.add("Engeneer");
      list_rule.add("Technics");
    }
    return list_rule;
  }

  @RequestMapping(path = "/get", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<WorkRecordingFilter> getEmployee(@RequestBody EditEmployee editEmployee) {
    return workRecordingRepository.findAll();
  }
}
