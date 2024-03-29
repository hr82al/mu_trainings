package ru.haval.muTrainings.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.haval.muTrainings.accessingdatajpa.PositionsRepository;
import ru.haval.muTrainings.accessingdatajpa.Position;
import java.util.List;

@RestController
public class PositionController {

    @Autowired
    PositionsRepository positionsRepository;

    @RequestMapping("/get_positions")
    public String getPositions() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{}";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(positionsRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(path = "/positions/get_json", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public List<Position> getEmployees() {
        return positionsRepository.findAll();
    }
}
