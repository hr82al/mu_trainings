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

import java.util.List;

@Controller
@RequestMapping("/trainingsNamesList")
public class TrainingsNamesController {
    @Autowired
    TrainingsNamesRepository trainingsNamesRepository;

    @GetMapping
    public String showTrainingNameFrame(Model model){
        //model.addAttribute("trainingsNames", trainingsNamesRepository.findByDelIsFalseOrderByText());
        model.addAttribute("trainingsNames", trainingsNamesRepository.findAll());
        return "trainingsNames";
    }

    @RequestMapping(path = "/add", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public TrainingName addTrainingName(@RequestBody TrainingName trainingName){
        //System.out.println(trainingName); 
        //int i =  trainingsNamesRepository.addTrainingNameQuery(trainingName.getText(), trainingName.getTrainingPeriod());
        // Map<String, Object> map = new HashMap<>();
        // map.put("result", "ok");
        return trainingsNamesRepository.save(trainingName);
    }

    @RequestMapping(path = "/del", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public TrainingName deleteTrainingName(@RequestBody TrainingName trainingName){
        trainingName.setDel(true);
        TrainingName result =  trainingsNamesRepository.save(trainingName);
        return result;
    }
    @RequestMapping(path = "/change", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public TrainingName changeTrainingName(@RequestBody TrainingName trainingName){
        return trainingsNamesRepository.save(trainingName);
    }

    @RequestMapping(path = "/get", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<TrainingName> getTrainingNames() {
        return trainingsNamesRepository.findByDelIsFalseOrderByText();
    }
}
