package ru.haval.muTrainings.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import ru.haval.muTrainings.accessingdatajpa.AccountRepository;
import ru.haval.muTrainings.accessingdatajpa.Accounts;

@Controller
@RequestMapping
public class AccountManagerController {
  @Autowired
  AccountRepository accountRepository;

  @GetMapping("/admin/accountManager")
  public String showAccountManager(Model model) {
    return "accountManager";
  }

  @RequestMapping(path = "/admin/accountManager/get", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Accounts> requestMethodName() {
    System.out.println("repository");
    return accountRepository.findAll();
  }
}
