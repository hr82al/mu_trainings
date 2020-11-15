package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.ui.Model;

import ru.haval.muTrainings.accessingdatajpa.TrainingName;
import ru.haval.muTrainings.accessingdatajpa.TrainingsNamesRepository;

@Controller
@RequestMapping("/trainingsNamesList")
public class TrainingsNamesController {
    @Autowired
    TrainingsNamesRepository trainingsNamesRepository;

    @GetMapping
    public String showTrainingNameFrame(Model model){
        model.addAttribute("trainingsgNames", trainingsNamesRepository.findByDelIsFalseOrderByTraining());
        return "trainingsNames";
    }

    @RequestMapping(path = "/add", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public TrainingName addTrainingName(@RequestBody TrainingName trainingName){
        System.out.println(trainingName); 
        int i =  trainingsNamesRepository.addTrainingNameQuery(trainingName.getTraining(), trainingName.getTrainingPeriod());
        // Map<String, Object> map = new HashMap<>();
        // map.put("result", "ok");
        return trainingName;
    }

    @RequestMapping(path = "/del", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public TrainingName deleteTrainingName(@RequestBody TrainingName trainingName){
        trainingName.setDel(true);
        System.out.println(trainingName);
        TrainingName result =  trainingsNamesRepository.save(trainingName);
        return result;
    }
    @RequestMapping(path = "/change", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public TrainingName changeTrainingName(@RequestBody TrainingName trainingName){
        System.out.println(trainingName);
        TrainingName result =  trainingsNamesRepository.save(trainingName);
        return result;
    }
}
