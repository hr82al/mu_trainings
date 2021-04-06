package ru.haval.muTrainings.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MuErrorController implements ErrorController {
  @RequestMapping("/error")
  @ResponseBody
  public String handleError(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
    return String.format(
        "<html><title>Ошибка</title><body><a class=\"navbar-brand\" href=\"/\"><img src=\"/images/haval_logo.jpg\"></a><h2>Ошибка</h2><div>Статус: <b>%s</b></div>"
            + "<div>Сообщение: <b>%s</b></div><body></html>",
        statusCode, exception == null ? "N/A" : exception.getMessage());
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
