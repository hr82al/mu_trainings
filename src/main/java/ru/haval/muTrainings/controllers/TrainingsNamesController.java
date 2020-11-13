package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import ru.haval.muTrainings.accessingdatajpa.TrainingsNamesRepository;

@Controller
@RequestMapping("/trainingsNamesList")
public class TrainingsNamesController {
    @Autowired
    TrainingsNamesRepository trainingsNamesRepository;

    @GetMapping
    public String showTrainingNameFrame(Model model){
        model.addAttribute("traininsgNames", trainingsNamesRepository.findByDelIsFalseOrderByTraining());
        return "trainingsNames";
    }
}
