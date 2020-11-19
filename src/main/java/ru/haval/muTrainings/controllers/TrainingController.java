package ru.haval.muTrainings.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainings")
public class TrainingController {

  @GetMapping
  public String showTrainingForm(Model model) {
    return "trainings";
  }
}
