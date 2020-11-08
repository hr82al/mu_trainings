package ru.haval.muTrainings.controllers;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.haval.muTrainings.accessingdatajpa.Position;
import ru.haval.muTrainings.accessingdatajpa.PositionsRepository;

@Slf4j
@Controller
@RequestMapping
public class PositionsController {
    @Autowired
    PositionsRepository positionsRepository;

    @GetMapping(path="/positions")
    public String showPositionForm(Model model) {
        model.addAttribute("positions", positionsRepository.findByOrderByTextAsc());
        return "positions";
    }

    @DeleteMapping("/position/delete/{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable("id") Long id, Model model) {
        System.out.println(id);
        try {
            positionsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
        //return "positions";
    }

    @PostMapping(path="/positions")
    //@ResponseStatus(HttpStatus.CREATED)
    public String postPosition(@RequestParam String pos, Model model) {

        System.out.println(pos);
        Position position = new Position();
        position.setText(pos);
        positionsRepository.save(position);
        return "positions";
    }

/*    @PostMapping("/positions")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("positions", greeting);
        return "positions";
    }*/
}
