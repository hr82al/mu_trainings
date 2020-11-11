package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class PositionsRestController {
    @PostMapping("/delete")
    public void deletePosition(@RequestBody String delPosistionId) {
        System.out.println(delPosistionId);
    }
}
