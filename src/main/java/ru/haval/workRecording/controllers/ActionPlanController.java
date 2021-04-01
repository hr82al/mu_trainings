package ru.haval.workRecording.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.haval.MuTrainingsApplication;
import ru.haval.workRecording.ActionPlanHeader;
import ru.haval.workRecording.accessingdatajpa.ActionPlanExtendedRepository;

@Controller
@RequestMapping("/action_plan")
/**
 * Shows processed work tasks in the table action plan
 */
public class ActionPlanController {
  @Autowired
  ActionPlanExtendedRepository actionPlanExtendedRepository;

  /**
   * Shows main window the action plan table
   * 
   * @param model
   * @return
   */
  @GetMapping
  public String showActionPlan(@RequestHeader("accept-language") String language, Model model) {
    System.out.println(language);
    //
    ActionPlanHeader header = new ActionPlanHeader("ru");
    model.addAttribute("header", header);
    model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByDelRecIsFalse());
    model.addAttribute("file", MuTrainingsApplication.getWarLocation());
    return "work_recording/action_plan";
  }
}
