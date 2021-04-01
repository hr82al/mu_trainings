package ru.haval.workRecording.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.val;
import ru.haval.MuTrainingsApplication;

@Controller
@RequestMapping("/work_recording/pdf_instruction")
public class PdfInstructionController {

  @RequestMapping(path = "/get", consumes = "text/plain", produces = "text/plain")
  @ResponseBody
  public String getPdfInstruction(@RequestBody String path) {
    Path source = Paths.get(path);
    Path dest = Paths.get(MuTrainingsApplication.getStaticTmpFolder());
    try {
      Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
      System.out.println(MuTrainingsApplication.getStaticTmpFolder());
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(path);
    return "some";
  }

}
