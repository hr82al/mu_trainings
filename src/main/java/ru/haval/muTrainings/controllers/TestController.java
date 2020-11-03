package ru.haval.muTrainings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.haval.muTrainings.accessingdatajpa.Position;
import ru.haval.muTrainings.accessingdatajpa.PositionsRepository;

@RestController
public class TestController {

    @Autowired
    PositionsRepository positionsRepository;

    @RequestMapping("/get_positions")
    public String getPositions(){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{}";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(positionsRepository.findAll());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}

