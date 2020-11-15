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

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/positions")
public class PositionsController {
    @Autowired
    PositionsRepository positionsRepository;

    @GetMapping
    public String showPositionForm(Model model) {
        model.addAttribute("positions", positionsRepository.findByOrderByTextAsc());
        return "positions";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public void deletePosition(@PathVariable("id") Long id, Model model) {
        System.out.println(id);
        try {
            positionsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {}
        //return "positions";
    }

    /*@PostMapping(path="/positions")
    //@ResponseStatus(HttpStatus.CREATED)
    public String postPosition(@RequestParam String pos, Model model) {

        System.out.println(pos);
        Position position = new Position();
        position.setText(pos);
        positionsRepository.save(position);
        return "positions";
    }*/

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Position addPosition(@RequestBody Position position){
        if ( position.getText() != ""){
            return positionsRepository.save(position);
        }
        return  null;
    }


/*    @PostMapping("/positions")
    public String greetingSubmit(@ModelAttribute Greeting greeting, Model model) {
        model.addAttribute("positions", greeting);
        return "positions";
    }*/

    @RequestMapping(path = "/get", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<Position> getPositions(){
        return positionsRepository.findByDelIsFalseOrderByTextAsc();
    }

    // @RequestMapping("/get")
    // @ResponseBody
    // public String getPositions(){
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String json = "{}";
    //     try {
    //         json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(positionsRepository.findAll());
    //     } catch(Exception e) {
    //         e.printStackTrace();
    //     }
    //     return json;
    // }
}
