package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.muTrainings.accessingdatajpa.PositionTraining;
import ru.haval.muTrainings.accessingdatajpa.PositionsTrainingsRepository;

@Controller
@RequestMapping("/positionsTrainings")
public class PositionsTrainingsController {
    @Autowired
    PositionsTrainingsRepository positionsTrainingsRepository;

    @GetMapping
    public String showPeriodsFrame(Model model){
        for(PositionTraining positionTraining : positionsTrainingsRepository.findAll()){
            System.out.println(positionTraining);
        }
        model.addAttribute("positionsTrainings", positionsTrainingsRepository.findAll());
        return "positionsTrainings";
    }

    @RequestMapping(path = "/del", consumes = "application/json", produces = "applicaion/json")
    @ResponseBody
    public PositionTraining deletePositionTraining(@RequestBody PositionTraining positionTraining){
        positionsTrainingsRepository.deleteById(positionTraining.getId());
        return positionTraining;
    }
}
