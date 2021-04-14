package ru.haval.muTrainings.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.muTrainings.accessingdatajpa.PositionTraining;
import ru.haval.muTrainings.accessingdatajpa.PositionTrainingIds;
import ru.haval.muTrainings.accessingdatajpa.PositionTrainingNames;
import ru.haval.muTrainings.accessingdatajpa.PositionTrainingNamesRepository;
import ru.haval.muTrainings.accessingdatajpa.PositionsTrainingIdsRepository;
import ru.haval.muTrainings.accessingdatajpa.PositionsTrainingsRepository;

import java.util.List;

@Controller
@RequestMapping("/positionsTrainings")
public class PositionsTrainingsController {
    @Autowired
    PositionsTrainingsRepository positionsTrainingsRepository;

    @Autowired
    PositionsTrainingIdsRepository positionTrainingIdsRepository;

    @Autowired
    PositionTrainingNamesRepository positionTrainingNamesRepository;

    @GetMapping
    public String showPeriodsFrame(Model model) {
        model.addAttribute("positionsTrainings", positionsTrainingsRepository.findAll());
        return "positionsTrainings";
    }

    @RequestMapping(path = "/del", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PositionTraining deletePositionTraining(@RequestBody PositionTraining positionTraining) {
        positionsTrainingsRepository.deleteById(positionTraining.getId());
        return positionTraining;
    }

    @RequestMapping(path = "/set", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PositionTrainingIds setPositionTraining(@RequestBody PositionTrainingIds positionTrainingIds) {
        return positionTrainingIdsRepository.save(positionTrainingIds);
    }

    @RequestMapping(path = "/change", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public PositionTrainingIds changePositionTraining(@RequestBody PositionTrainingIds rPositionTrainingIds) {
        Optional<PositionTrainingIds> positionTraining = positionTrainingIdsRepository
                .findById(rPositionTrainingIds.getId());
        if (positionTraining.isPresent()) {
            PositionTrainingIds pt = positionTraining.get();
            pt.setOptional(rPositionTrainingIds.getOptional());
            return positionTrainingIdsRepository.save(pt);
        }
        return null;

    }

    @PostMapping(path = "/get_json", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<PositionTrainingIds> getNotDeletedPositionsTrainings() {
        return positionTrainingIdsRepository.findAll();
    }

    @PostMapping(path = "/get", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<PositionTrainingNames> getPositionsTrainings() {
        return positionTrainingNamesRepository.findAll();
    }
}
