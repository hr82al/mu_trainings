package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.muTrainings.accessingdatajpa.LastDates;
import ru.haval.muTrainings.accessingdatajpa.LastDatesRepository;

import java.util.List;

@Controller
@RequestMapping("/lastDates")
public class LastDatesController {
  @Autowired
  LastDatesRepository lastDatesRepository;

  @PostMapping(path = "/get_json", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<LastDates> getLastDates() {
    return lastDatesRepository.findAll();
  }
}
