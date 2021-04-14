package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.haval.muTrainings.accessingdatajpa.Position;
import ru.haval.muTrainings.accessingdatajpa.PositionsRepository;

import java.util.List;

@Controller
@RequestMapping("/positions")
public class PositionsController {
    @Autowired
    PositionsRepository positionsRepository;

    @GetMapping
    public String showPositionForm(Model model) {
        // model.addAttribute("positions", positionsRepository.findByOrderByTextAsc());
        model.addAttribute("positions", positionsRepository.findAll());
        return "positions";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable("id") Long id, Model model) {
        try {
            positionsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
        }
    }

    @RequestMapping(path = "/get", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<Position> getPositions() {
        return positionsRepository.findByDelIsFalse();
    }

    // @RequestMapping("/get")
    // @ResponseBody
    // public String getPositions(){
    // ObjectMapper objectMapper = new ObjectMapper();
    // String json = "{}";
    // try {
    // json =
    // objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(positionsRepository.findAll());
    // } catch(Exception e) {
    // e.printStackTrace();
    // }
    // return json;
    // }

    @RequestMapping(path = "/add", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Position addPosition(@RequestBody Position position) {
        return positionsRepository.save(position);
    }
}
