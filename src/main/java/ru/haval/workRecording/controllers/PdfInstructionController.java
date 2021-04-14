package ru.haval.workRecording.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.haval.MuTrainingsApplication;

@Controller
@RequestMapping("/work_recording/pdf_instruction")
public class PdfInstructionController {

  @RequestMapping(path = "/get", consumes = "text/plain", produces = "text/plain")
  @ResponseBody
  public String getPdfInstruction(@RequestBody String path) {
    if (path.endsWith(".pdf")) {
      Path source = Paths.get(path);
      Path dest = Paths.get(MuTrainingsApplication.getStaticTmpFolder() + "\\" + source.getFileName());
      try {
        Files.createDirectories(Paths.get(MuTrainingsApplication.getStaticTmpFolder()));
        Stream<Path> list = Files.list(Paths.get(MuTrainingsApplication.getStaticTmpFolder()));
        list.forEach((file) -> {
          try {
            if (ChronoUnit.DAYS.between(Files.getLastModifiedTime(Paths.get(file.toUri())).toInstant(),
                FileTime.fromMillis(System.currentTimeMillis()).toInstant()) > 1) {
              Files.delete(Paths.get(file.toUri()));
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
        list.close();
        Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        Files.setLastModifiedTime(dest, FileTime.fromMillis(System.currentTimeMillis()));
      } catch (Exception e) {
        System.out.println("Error file: " + source + " " + e.getMessage());
      }
      return "/tmp/" + source.getFileName();
    }
    return ".";
  }
}
