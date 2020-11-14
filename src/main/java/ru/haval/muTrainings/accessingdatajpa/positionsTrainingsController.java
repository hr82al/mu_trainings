package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/positionsTrainings")
public class positionsTrainingsController {

    @GetMapping
    public String showPeriodsFrame(Model model){
        return "positionsTrainings";
    }
}
