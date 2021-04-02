package ru.haval.workRecording.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
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
        // final File dir = new File(MuTrainingsApplication.getStaticTmpFolder());
        // final File[] files = dir.listFiles((d, name) -> name.matches("*\\.pdf"));
        // Arrays.asList(files).stream().forEach(File::delete);
        Files.createDirectories(Paths.get(MuTrainingsApplication.getStaticTmpFolder()));
        Stream<Path> list = Files.list(Paths.get(MuTrainingsApplication.getStaticTmpFolder()));
        list.forEach((file) -> {
          System.out.println(file.toFile().);
          if ((java.time.Instant.now().getEpochSecond() * 1000 - file.toFile().lastModified()) > 600000) {
            System.out.println(Path.get(file.toString()) );
            System.out.println((System.currentTimeMillis() - file.toFile().lastModified()));
            System.out.println(java.time.Instant.now().toEpochMilli());
            System.out.println(file.toString());
          }
        });
        Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(MuTrainingsApplication.getStaticTmpFolder());
      } catch (IOException e) {
        // TODO Auto-generated catch block
        // if path incorrect
        e.printStackTrace();
      }
      return "/tmp/" + source.getFileName();
    }
    return ".";

  }

}
