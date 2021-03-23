package ru.haval.muTrainings.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.muTrainings.accessingdatajpa.EditEmployee;
import ru.haval.muTrainings.accessingdatajpa.HmmrStaff;
import ru.haval.muTrainings.accessingdatajpa.HmmrStaffRepository;

@Controller
@RequestMapping("employee")
public class EditEmployeeController {
  // @Qualifier("workrecording")
  // @Autowired
  // HmmrStaffRepository hmmr;

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
    // for (HmmrStaff staff : hmmr.findAll()) {
    // System.out.println(staff);
    // }
    return list_rule;
  }
}
