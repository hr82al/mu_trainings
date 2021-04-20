package ru.haval.workRecording.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.haval.workRecording.AddWorkRecordHeader;
import ru.haval.workRecording.accessingdatajpa.ActionPlanRepository;
import ru.haval.workRecording.accessingdatajpa.ActionPlanExtended;
import ru.haval.workRecording.accessingdatajpa.ActionPlanExtendedRepository;
import ru.haval.workRecording.accessingdatajpa.DataForNewWorkRecord;
import ru.haval.workRecording.accessingdatajpa.DataForNewWorkRecordRepository;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaff;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaffRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsers;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsersRepository;
import ru.haval.workRecording.accessingdatajpa.HmmrWorkRecording;
import ru.haval.workRecording.accessingdatajpa.HmmrWorkRecordingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

@Controller
@RequestMapping("/add_work_record")
public class addWorkRecordController {
  @Autowired
  HmmrMuStaffRepository hmmrMuStaff;
  @Autowired
  WorkRecordingUsersRepository workRecordingUsers;
  @Autowired
  ActionPlanExtendedRepository actionPlanExtendedRepository;
  @Autowired
  ActionPlanRepository actionPlanRepository;
  @Autowired
  DataForNewWorkRecordRepository dataForNewWorkRecord;
  @Autowired
  HmmrWorkRecordingRepository workRecordingRepository;

  private List<Long> ids;
  private List<ActionPlanExtended> selected;
  private String userLetters;
  List<ActionPlanExtended> list;

  /**
   * This method accumulates ids of action plan's records which was selected by
   * user when pressed the executor button icon
   * 
   * @param ids
   * @return
   */
  @RequestMapping(path = "/add", consumes = "application/json")
  public String addWorkRecord(@RequestBody List<String> ids) {
    this.ids = new ArrayList<>();
    for (String s : ids) {
      this.ids.add(Long.parseLong(s));
    }
    return "index";
  }

  @PostMapping(path = "/create", consumes = "application/json")
  @ResponseStatus(code = HttpStatus.OK)
  public void createWork(@RequestBody JsonNode rootNode) {
    final String DESCRIPTION = rootNode.get("description").asText();
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    for (JsonNode node : rootNode.get("records")) {
      ActionPlanExtended currentRecord = getActionPlanExtendedById(node.get("id").asLong(), selected);
      // check if current user is owner of the task
      if (currentRecord != null && currentRecord.getExecutor().equals(userLetters)) {
        final String START_DATE = node.get("startDateTime").asText();
        final LocalDateTime startDateTime = LocalDateTime.parse(START_DATE, dateTimeFormatter);
        final String END_DATE = node.get("endDateTime").asText();
        final LocalDateTime endDateTime = LocalDateTime.parse(END_DATE, dateTimeFormatter);
        addWorkRecord(node.get("id").asLong(), startDateTime, endDateTime, DESCRIPTION);
      }
    }
  }

  private static ActionPlanExtended getActionPlanExtendedById(Long id, List<ActionPlanExtended> list) {
    for (ActionPlanExtended i : list) {
      if (id.equals(i.getId())) {
        return i;
      }
    }
    return null;
  }

  @GetMapping
  public String showAddWorkRecord(@RequestHeader("accept-language") String language, Model model) {
    if (ids == null) {
      return "redirect:action_plan";
    }
    //
    AddWorkRecordHeader header = new AddWorkRecordHeader("ru");
    userLetters = getCurrentUserLettersId();
    model.addAttribute("header", header);
    if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
        .anyMatch(r -> r.getAuthority().equals("Administrator"))) {
      list = actionPlanExtendedRepository.findByDelRecIsFalse();
    } else { // If the users authorities different from the administrator
      list = actionPlanExtendedRepository.findByLetters(getCurrentUserLettersId());
    }
    selected = new ArrayList<>();
    for (Long id : ids) {
      // check if current user is owner of the task
      ActionPlanExtended record = getActionPlanExtendedById(id, list);
      if (record != null && record.getExecutor().equals(userLetters)) {
        selected.add(record);
      }
    }
    model.addAttribute("action_plan_rows", selected);
    return "work_recording/add_work_record";
  }

  private String getUserLettersByUserName(String userName) {
    WorkRecordingUsers user = workRecordingUsers.getIdByLogin(userName);
    Long id = user.getId();
    Optional<HmmrMuStaff> staff = hmmrMuStaff.findByUserId(id);
    if (!staff.isEmpty()) {
      return staff.get().getUserLettersId();
    }
    return null;
  }

  private String getCurrentUserLettersId() {
    String currentUser = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        .getUsername();
    return getUserLettersByUserName(currentUser);
  }

  private void addWorkRecord(Long actionPlanId, LocalDateTime start, LocalDateTime end, String taskReport) {
    LocalTime startTime = start.toLocalTime();
    LocalDate startDate = start.toLocalDate();
    LocalTime endTime = end.toLocalTime();
    LocalDate endDate = end.toLocalDate();
    final long DURATION = 60;
    // total work duration
    // double commonWorkTime = 0;
    LocalTime currentTime = LocalTime.now();
    currentTime = LocalTime.of(currentTime.getHour(), currentTime.getMinute());
    LocalDate currentDate = LocalDate.now();
    LocalTime currentTimeEnd = currentTime.plusMinutes(DURATION);
    String wrWorkTime = String.valueOf(ChronoUnit.MINUTES.between(start, end));
    DataForNewWorkRecord dataForNewRecord = dataForNewWorkRecord.findByActionPlanId(actionPlanId);
    HmmrWorkRecording workRecord = new HmmrWorkRecording();
    workRecord.setActionPlanId(actionPlanId);
    workRecord.setUserId(dataForNewRecord.getUserId());
    workRecord.setShop(dataForNewRecord.getShop());
    workRecord.setGroup(dataForNewRecord.getGroup());
    workRecord.setLine(dataForNewRecord.getLine());
    workRecord.setStation(dataForNewRecord.getStation());
    workRecord.setEquipment(dataForNewRecord.getEquipment());
    workRecord.setEquipmentFull(dataForNewRecord.getEquipmentFull());
    workRecord.setRecordType(dataForNewRecord.getRecordType());
    workRecord.setCommonWorkTime(0.0);
    workRecord.setTaskRespId(dataForNewRecord.getTaskRespId());
    workRecord.setWrExecutorConfirmed("Confirmed WR");
    workRecord.setTaskDescription(dataForNewRecord.getDescription());
    workRecord.setTaskReport(taskReport);
    workRecord.setWrBeginDate(startDate);
    workRecord.setActualDate_2(currentDate);
    workRecord.setActualDate_3(currentDate);
    workRecord.setActualDate_4(currentDate);
    workRecord.setActualDate2(currentDate);
    workRecord.setActualDate3(currentDate);
    workRecord.setActualDate4(currentDate);
    workRecord.setActualDate_5(currentDate);
    workRecord.setActualDate_6(currentDate);
    workRecord.setActualDate_7(currentDate);
    workRecord.setActualDate_8(currentDate);
    workRecord.setActualDate_9(currentDate);
    workRecord.setActualDate5(currentDate);
    workRecord.setActualDate6(currentDate);
    workRecord.setActualDate7(currentDate);
    workRecord.setActualDate8(currentDate);
    workRecord.setActualDate9(currentDate);
    workRecord.setResp2("0");
    workRecord.setResp3("0");
    workRecord.setResp4("0");
    workRecord.setCommonDownTime("0");
    workRecord.setResp5("0");
    workRecord.setResp6("0");
    workRecord.setResp7("0");
    workRecord.setResp8("0");
    workRecord.setResp9("0");
    workRecord.setWrExecutorConfirmed("Confirmed WR");
    workRecord.setWrBeginDate(startDate);
    workRecord.setWrEndDate(endDate);
    workRecord.setActualTime2("60");
    workRecord.setActualTime3("60");
    workRecord.setActualTime4("60");
    workRecord.setActualTime5("60");
    workRecord.setActualTime6("60");
    workRecord.setActualTime7("60");
    workRecord.setActualTime8("60");
    workRecord.setActualTime9("60");
    workRecord.setWrWorkTimeBegin(startTime.toString());
    workRecord.setWrWorkTimeEnd(endTime.toString());
    workRecord.setHours1_2(currentTime.toString());
    workRecord.setHours1_3(currentTime.toString());
    workRecord.setHours1_4(currentTime.toString());
    workRecord.setHours1_5(currentTime.toString());
    workRecord.setHours1_6(currentTime.toString());
    workRecord.setHours1_7(currentTime.toString());
    workRecord.setHours1_8(currentTime.toString());
    workRecord.setHours1_9(currentTime.toString());
    workRecord.setCommonBeginDate(LocalDate.parse("0000-01-01"));
    workRecord.setCommonEndDate(LocalDate.parse("0000-01-01"));
    workRecord.setCommonBeginTime("00:00");
    workRecord.setCommonEndTime("00:00");
    workRecord.setHours2_2(currentTimeEnd.toString());
    workRecord.setHours2_3(currentTimeEnd.toString());
    workRecord.setHours2_4(currentTimeEnd.toString());
    workRecord.setHours2_5(currentTimeEnd.toString());
    workRecord.setHours2_6(currentTimeEnd.toString());
    workRecord.setHours2_7(currentTimeEnd.toString());
    workRecord.setHours2_8(currentTimeEnd.toString());
    workRecord.setHours2_9(currentTimeEnd.toString());
    workRecord.setActivityType(dataForNewRecord.getActivityType());
    workRecord.setWrWorkTime(Long.parseLong(wrWorkTime));
    workRecordingRepository.save(workRecord);
    actionPlanRepository.setFlags(actionPlanId, "1", "0", "0");

  }
}
