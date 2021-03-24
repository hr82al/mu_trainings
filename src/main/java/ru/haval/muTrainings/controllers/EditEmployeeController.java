package ru.haval.muTrainings.controllers;

import java.time.LocalDate;
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
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsers;
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
  public EditEmployee addEmployee(@RequestBody EditEmployee employee) {
    System.out.println(employee);
    WorkRecordingUsers user = new WorkRecordingUsers();
    user.setFirstName(employee.getFirstName());
    user.setLastName(employee.getLastName());
    user.setCreateDate(LocalDate.now());
    user.setLogin(employee.getLogin());
    user.setPassword(employee.getPassword());
    user.setRole(employee.getRole());
    user.setUserDel(false);
    WorkRecordingUsers newUser = workRecordingUsersRepository.save(user);
    WorkRecordingHmmrMuStaff staff = new WorkRecordingHmmrMuStaff();
    employee.setId(newUser.getId());
    staff.setStaffId(employee.getStaffId());
    staff.setUserLettersId(employee.getUserLettersId());
    staff.setUserId(newUser.getId());
    staff.setRuFirstName(employee.getFirstName());
    staff.setRuLastName(employee.getLastName());
    staff.setPatronymic(employee.getPatronymic());
    staff.setEnFirstName(employee.getEnFirstName());
    staff.setEnLastName(employee.getEnLastName());
    staff.setBeginDate(employee.getBeginDate());
    staff.setSection(employee.getSection());
    staff.setShop(employee.getShop());
    staff.setTeam(employee.getTeam());
    staff.setShift(employee.getShift());
    staff.setEnPosition(employee.getEnPosition());
    staff.setRuPosition(employee.getEnPosition());
    staff.setGwmId(employee.getGwmId());
    staff.setBeginDate(employee.getBeginDate());
    staff.setEndDate(employee.getBeginDate());
    staff.setEmail(employee.getEmail());
    staff.setSkype(employee.getSkype());
    staff.setPhone1(employee.getPhone1());
    staff.setPhone2(employee.getPhone2());
    staff.setAddress(employee.getAddress());
    staff.setNumPlate(employee.getNumPlate());
    staff.setShoeSize(employee.getShoeSize());
    staff.setClothesSize(employee.getClothesSize());
    workRecordingHmmrMuStaffRepository.save(staff);
    return employee;
  }

  @RequestMapping(path = "/ruPositions", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Select2> getRuPositions() {
    List<Select2> positions = new ArrayList<>();
    Select2 tmp;
    for (WorkRecordingHmmrMuStaff i : workRecordingHmmrMuStaffRepository.getRuPositions()) {
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
    for (WorkRecordingHmmrMuStaff i : workRecordingHmmrMuStaffRepository.getEnPosition()) {
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
      return workRecordingHmmrMuStaffRepository.existsByStaffId(nameValue.getValue());
    case "userLettersId":
      System.out.println(workRecordingHmmrMuStaffRepository.existsByUserLettersId(nameValue.getValue()));
      return workRecordingHmmrMuStaffRepository.existsByUserLettersId(nameValue.getValue());
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
