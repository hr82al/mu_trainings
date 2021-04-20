package ru.haval.workRecording.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ru.haval.workRecording.ActionPlanHeader;
import ru.haval.workRecording.accessingdatajpa.ActionPlanExtendedRepository;
import ru.haval.workRecording.accessingdatajpa.Colors;
import ru.haval.workRecording.accessingdatajpa.ColorsRepository;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaff;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaffRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsers;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsersRepository;

/**
 * Shows current processed work tasks in the action plan table
 */
@Controller
@RequestMapping("/action_plan")
public class ActionPlanController {
  @Autowired
  private ActionPlanExtendedRepository actionPlanExtendedRepository;
  @Autowired
  private ColorsRepository colorsRepository;
  @Autowired
  private HmmrMuStaffRepository hmmrMuStaff;
  @Autowired
  private WorkRecordingUsersRepository workRecordingUsers;

  // The variable contains user letter ID for the current user. For example,
  // "SAV", which means Смелых Андрей
  private String currentUserLetterId;
  // TODO remove
  private ActionPlanHeader header = new ActionPlanHeader("ru");
  String currentRole;

  private Map<String, String[]> views = new HashMap<>();
  private Map<String, String> viewsCaptions = new HashMap<>();
  private Map<String, Pair<String, String>> shops = new HashMap<>();
  private Map<String, String> shopsLettersMap = new HashMap<>();
  {
    views.put("Administrator", new String[] { "executed_last_year", "executed_previous_year", "without_executor" });
    views.put("Group Lead", new String[] { "all", "executed_last_year", "executed_previous_year", "without_executor" });
    views.put("Team Lead", new String[] { "all", "executed_last_year", "executed_previous_year", "without_executor" });
    views.put("Engeneer", new String[] { "executed_last_year", "executed_previous_year", "without_executor" });
    views.put("Engeneer", new String[] { "executed_last_year", "executed_previous_year", "without_executor" });
    viewsCaptions.put("executed_last_year", "Выполненные за последний год");
    viewsCaptions.put("executed_previous_year", "Выполненные за предыдущий год");
    viewsCaptions.put("all", "Все цеха");
    viewsCaptions.put("without_executor", "Задачи без исполнителя");
    shops.put("A", Pair.of("assembly", "Цех сборки"));
    shops.put("L", Pair.of("logistics", "Цех логистики"));
    shops.put("P", Pair.of("paint", "Цех покраски"));
    shops.put("S", Pair.of("stamp", "Цех штамповки"));
    shops.put("W", Pair.of("welding", "Цех сварки"));
    shops.put("J", Pair.of("jig", "Кондукторы"));
    shopsLettersMap.put("assembly", "A");
    shopsLettersMap.put("logistics", "L");
    shopsLettersMap.put("paint", "P");
    shopsLettersMap.put("welding", "W");
    shopsLettersMap.put("jig", "J");
  }
  private String[] userShops;

  /**
   * Shows main window the action plan table. This is initial method at the action
   * plan page.
   * 
   * @param model
   * @return
   */
  @GetMapping(path = "/personal")
  public String showActionPlanPersonalTask(@RequestHeader("accept-language") String language, Model model) {
    // TODO set language by header

    showPersonalTasks(model);
    return "work_recording/action_plan";
  }

  private void initPage(Model model) {
    initCurrentUserLetterId();
    model.addAttribute("header", header);
    model.addAttribute("viewsCaptions", viewsCaptions);
    List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .map(r -> r.getAuthority()).collect(Collectors.toList());
    currentRole = roles.get(0);
    model.addAttribute("views", views.get(currentRole));
    if (currentRole.equals("Administrator")) {
      var list = shops.values().toArray();
      model.addAttribute("shops", list);
    } else {
      List<Pair<String, String>> shopList = new ArrayList<>();
      for (String shop : userShops) {
        shopList.add(shops.get(shop));
      }
      shopList.add(shops.get("J"));
      model.addAttribute("shops", shopList);
    }

  }

  /**
   * Shows current personal not executed tasks
   * 
   * @param model
   */
  private void showPersonalTasks(Model model) {
    initPage(model);
    model.addAttribute("current", "Личные");
    // for administrator shows all not executed current tasks
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .anyMatch(r -> r.getAuthority().equals("Administrator"))) {
      model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByDelRecIsFalse());
    } else {
      // If the users authorities different from the administrator
      model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByLetters(currentUserLetterId));
    }
  }

  @GetMapping(path = "/all")
  public String showActionPlanAllTask(@RequestHeader("accept-language") String language, Model model) {
    // TODO set language by header
    showAllTasks(model);
    return "work_recording/action_plan";
  }

  private void showAllTasks(Model model) {
    initPage(model);
    model.addAttribute("current", "Все");
    model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByDelRecIsFalse());
  }

  @GetMapping(path = "/executed_last_year")
  public String showActionPlanExecutedLastYear(@RequestHeader("accept-language") String language, Model model) {
    // TODO set language by header
    showExecutedLastYear(model);
    return "work_recording/action_plan";
  }

  private void showExecutedLastYear(Model model) {
    initPage(model);
    model.addAttribute("current", "Выполненные за последний год");
    LocalDate date = LocalDate.now();
    model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByYear(date.getYear()));
  }

  @GetMapping(path = "/without_executor")
  public String showActionPlanTasksWithoutExecutor(@RequestHeader("accept-language") String language, Model model) {
    // TODO set language by header
    showTasksWithoutExecutor(model);
    return "work_recording/action_plan";
  }

  private void showTasksWithoutExecutor(Model model) {
    initPage(model);
    model.addAttribute("current", "Задачи без исполнителя");
    if (currentRole.equals("Administrator")) {
      model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findWithoutExecutor());
    } else {
      model.addAttribute("action_plan_rows",
          actionPlanExtendedRepository.findWithoutExecutorByUserLettersId(currentUserLetterId));
    }
  }

  @GetMapping(path = "/executed_previous_year")
  public String showActionPlanExecutedPreviousYear(@RequestHeader("accept-language") String language, Model model) {
    // TODO set language
    showExecutedPreviousYear(model);
    return "work_recording/action_plan";
  }

  private void showExecutedPreviousYear(Model model) {
    initPage(model);
    model.addAttribute("current", "Выполненные за предыдущий год");
    final int year = LocalDate.now().getYear() - 1;
    model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByYear(year));
  }

  @GetMapping(path = "/shop/{shop}")
  public String showActionPlanShop(@RequestHeader("accept-language") String language,
      @PathVariable(value = "shop") String shop, Model model) {
    // TODO set language
    if (Arrays.stream(userShops).anyMatch(shopsLettersMap.get(shop)::equals) || currentRole.equals("Administrator")
        || shop.equals("jig")) {
      showShop(model, shopsLettersMap.get(shop));
    } else {
      return "redirect:/action_plan/personal";
    }
    return "work_recording/action_plan";
  }

  private void showShop(Model model, String shop) {
    initPage(model);
    model.addAttribute("current", "Цех: " + shop);
    model.addAttribute("action_plan_rows", actionPlanExtendedRepository.findByShopAndDelRecIsFalse(shop));
  }

  @RequestMapping(path = "/colors", consumes = "application/json", produces = "application/json")
  @ResponseBody
  public List<Colors> getColors() {
    return colorsRepository.findAll();
  }

  private void initCurrentUserLetterId() {
    final String CURRENT_USER = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUsername();
    WorkRecordingUsers user = workRecordingUsers.getIdByLogin(CURRENT_USER);
    Long id = user.getId();
    Optional<HmmrMuStaff> staff = hmmrMuStaff.findByUserId(id);
    if (!staff.isEmpty()) {
      userShops = staff.get().getShop().split(",");
      currentUserLetterId = staff.get().getUserLettersId();
    }
  }
}
