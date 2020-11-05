package ru.haval.muTrainings.controllers;

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
        model.addAttribute("positions", positionsRepository.findByOrderByIdAsc());
        return "positions";
    }

    @DeleteMapping("/position/delete/{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable("id") Long id) {
        System.out.println(id);
        try {
            positionsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
    }

    @PostMapping(path="/position/post", consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void postPosition(@RequestBody String position) {
        System.out.println(position);
    }
}
