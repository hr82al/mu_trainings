package ru.haval.workRecording.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import ru.haval.MuTrainingsApplication;
import ru.haval.workRecording.ActionPlanHeader;
import ru.haval.workRecording.accessingdatajpa.ActionPlanExtendedRepository;
import ru.haval.workRecording.accessingdatajpa.Colors;
import ru.haval.workRecording.accessingdatajpa.ColorsRepository;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaff;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaffRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsers;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsersRepository;

@Controller
@RequestMapping("/action_plan")
/**
 * Shows processed work tasks in the table action plan
 */
public class ActionPlanController {
  @Autowired
  ActionPlanExtendedRepository actionPlanExtendedRepository;
  @Autowired
  ColorsRepository colorsRepository;
  @Autowired
  HmmrMuStaffRepository hmmrMuStaff;
  @Autowired
  WorkRecordingUsersRepository workRecordingUsers;

  /**
   * Shows main window the action plan table
   * 
   * @param model
   * @return
   */
  @GetMapping
  public String showActionPlan(@RequestHeader("accept-language") String language, Model model) {
    String currentUser = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUsername();
    // String currentUserLettersID = hmmrMuStaff.findByUserId(id)
    // System.out.println(language);
    String userLettersId = getUserLettersByUserName(currentUser);
    System.out.println(userLettersId);
    System.out.println(userLettersId);
    System.out.println(userLettersId);
    //
    ActionPlanHeader header = new ActionPlanHeader("ru");
    model.addAttribute("header", header);
    model.addAttribute("file", MuTrainingsApplication.getWarLocation());
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .anyMatch(r -> r.getAuthority().equals("Administrator"))) {
      // model.addAttribute("action_plan_rows",
      // actionPlanExtendedRepository.findByLetters("SAV"));
      model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByDelRecIsFalse());
    } else { // If the users authorities different from the administrator
      model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByLetters(userLettersId));
    }

    return "work_recording/action_plan";
  }

  @RequestMapping(path = "/colors", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Colors> getColors() {
    return colorsRepository.findAll();
  }

  public String getUserLettersByUserName(String userName) {
    WorkRecordingUsers user = workRecordingUsers.getIdByLogin(userName);
    Long id = user.getId();
    HmmrMuStaff staff = hmmrMuStaff.getUserLettersIdByUserId(id);
    return staff.getUserLettersId();
  }
}
