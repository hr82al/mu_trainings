package ru.haval.workRecording.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.haval.workRecording.dbContractions;
import ru.haval.workRecording.accessingdatajpa.ReportsRepository;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping(path = "/reports")
public class Report {
  @Autowired
  ReportsRepository report;

  @GetMapping(path = "/counters/{year}")
  public String showCounters(Model model, @PathVariable(value = "year") int year) {
    countTotalWorks(model, year);
    return "/work_recording/reports/counters";
  }

  @GetMapping(path = "/counters")
  public String showCounters(Model model) {
    int year = LocalDate.now().getYear();
    countTotalWorks(model, year);
    return "/work_recording/reports/counters";
  }

  private void countTotalWorks(Model model, int year) {
    List<List<String>> totalForYear = report.getTotalPlanedWorks(Integer.toString(year));
    List<List<String>> totalCompletedPlannedForThisYear = report.getCompletedPlannedWorksTotal(Integer.toString(year));
    List<List<String>> totalCompletedInYear = report.getCompletedWorksTotal(Integer.toString(year));
    List<List<List<String>>> totalCompetedPlannedForMonths = new ArrayList<>();
    List<List<List<String>>> totalCompetedForMonths = new ArrayList<>();
    List<List<List<String>>> totalForMonths = new ArrayList<>();
    for (int month = 1; month <= 12; month++) {
      String monthString = Integer.toString(year) + "-" + String.format("%02d", month);
      totalForMonths.add(report.getTotalPlanedWorks(monthString));
      totalCompetedPlannedForMonths.add(report.getCompletedPlannedWorksTotal(monthString));
      totalCompetedForMonths.add(report.getCompletedWorksTotal(monthString));
    }
    List<List<String>> completed = assembleTotalTable(totalCompletedPlannedForThisYear, totalCompetedPlannedForMonths);
    List<List<String>> total = assembleTotalTable(totalForYear, totalForMonths);
    model.addAttribute("total", total);
    model.addAttribute("completed", completed);
    List<List<String>> percents = new ArrayList<>();
    percents.add(total.get(0));
    for (int row = 1; row < total.size(); row++) {
      for (List<String> planRow : completed) {
        if (completed.get(row).get(0).equals(planRow.get(0))) {
          List<String> percentRow = new ArrayList<>();
          percentRow.add(planRow.get(0));
          for (int i = 1; i < planRow.size(); i++) {
            long totalLong = Long.parseLong(total.get(row).get(i));
            long completedLong = Long.parseLong(planRow.get(i));
            if (totalLong == 0L) {
              percentRow.add("100");
            } else {
              long percent = completedLong * 100 / totalLong;
              percentRow.add(Long.toString(percent));
            }
          }
          percents.add(percentRow);
        }
      }
    }
    model.addAttribute("percents", percents);
    model.addAttribute("all_work", assembleTotalTable(totalCompletedInYear, totalCompetedForMonths));
  }

  private List<List<String>> assembleTotalTable(List<List<String>> totalYear, List<List<List<String>>> totalMonth) {
    List<List<String>> table = new ArrayList<>();
    List<String> head = new ArrayList<>();
    head.add("");
    head.add("За год");
    for (int month = 1; month <= 12; month++) {
      head.add(Month.of(month).getDisplayName(TextStyle.FULL, new Locale("ru")));
    }
    table.add(head);
    List<String> allShops = new ArrayList<>();
    allShops.add("Все цеха");
    long allShopsInYear = 0;
    for (List<String> record : totalYear) {
      allShopsInYear += Long.parseLong(record.get(1));
    }
    allShops.add(Long.toString(allShopsInYear));
    for (List<List<String>> monthRecord : totalMonth) {
      long allShopsInMonth = 0;
      for (List<String> record : monthRecord) {
        allShopsInMonth += Long.parseLong(record.get(1));
      }
      allShops.add(Long.toString(allShopsInMonth));
    }
    table.add(allShops);
    for (String shopLetter : dbContractions.shops.keySet()) {
      List<String> oneOfTheShops = new ArrayList<>();
      oneOfTheShops.add(dbContractions.shops.get(shopLetter));
      long totalShop = 0;
      for (List<String> shop : totalYear) {
        if (shop.get(0).equals(shopLetter)) {
          totalShop += Long.parseLong(shop.get(1));
        }
      }
      oneOfTheShops.add(Long.toString(totalShop));
      for (List<List<String>> month : totalMonth) {
        long totalMonthShop = 0;
        for (List<String> shop : month) {
          if (shop.get(0).equals(shopLetter)) {
            totalMonthShop += Long.parseLong(shop.get(1));
          }
        }
        oneOfTheShops.add(Long.toString(totalMonthShop));
      }
      table.add(oneOfTheShops);
    }
    return table;
  }
}
