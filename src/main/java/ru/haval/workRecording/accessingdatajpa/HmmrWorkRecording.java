package ru.haval.workRecording.accessingdatajpa;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hmmr_work_recording", schema = "hmmr_mu", catalog = "hmmr_mu")
public class HmmrWorkRecording {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "ap_num")
  private Long actionPlanId = 1L;
  @Column(name = "user_number")
  private Long userId = 1L;
  @Column(name = "record_type", length = 25)
  private String recordType = null;
  @Column(name = "fl_wsh", length = 5)
  private String shop = null;
  @Column(name = "fl_group")
  private String group = null;
  @Column(name = "fl_line")
  private String line = null;
  @Column(name = "fl_station", length = 45)
  private String station = null;
  @Column(name = "fl_equipment")
  private String equipment = "0";
  @Column(name = "equipment_full")
  private String equipmentFull = null;
  @Column(name = "wr_executor_confirmed", length = 55)
  private String wrExecutorConfirmed = null;
  @Column(name = "wr_resp_confirmed", length = 45)
  private String wrRespConfirmed = "0"; // Признак подтверждения задачи ответственным за задачу
  @Column(name = "wr_host_confirmed", length = 5)
  private String wrHostConfirmed = "0"; // Признак подтверждения задачи ее хозяином
  @Column(name = "task_resp_id", length = 75)
  private String taskRespId = null;
  @Column(name = "task_description", length = 5255)
  private String taskDescription = null;
  @Column(name = "task_report", length = 5255)
  private String taskReport = null;
  @Column(name = "wr_begin_date")
  private LocalDate wrBeginDate = null;
  @Column(name = "wr_end_date")
  private LocalDate wrEndDate = null;
  @Column(name = "wr_work_time_begin", length = 55)
  private String wrWorkTimeBegin = "00:00";
  @Column(name = "wr_work_time_end", length = 55)
  private String wrWorkTimeEnd = "00:00";
  @Column(name = "wr_work_time")
  private Long wrWorkTime = 0L; // Время работы сотрудника
  @Column(name = "cm_date_begin")
  private LocalDate commonBeginDate;// Общая дата работ - начало
  @Column(name = "cm_date_end")
  private LocalDate commonEndDate;// Общая дата работ - окончание
  @Column(name = "cm_time_begin", length = 55)
  private String commonBeginTime = "00:00"; // Общее время работ - начало
  @Column(name = "cm_time_end", length = 55)
  private String commonEndTime = "00:00"; // Общее время работ - окончание
  @Column(name = "cm_work_time")
  private Double commonWorkTime = 0.0; // Общее время ремонта, часы
  @Column(name = "cm_downtime")
  private String commonDownTime = null; // Время простоя оборудования
  @Column(name = "_resp2", length = 5)
  private String resp2 = null;
  @Column(name = "_resp3", length = 5)
  private String resp3 = null;
  @Column(name = "_resp4", length = 5)
  private String resp4 = null;
  @Column(name = "_actual_date_2")
  private LocalDate actualDate_2 = null;
  @Column(name = "_actual_date_3")
  private LocalDate actualDate_3 = null;
  @Column(name = "_actual_date_4")
  private LocalDate actualDate_4 = null;
  @Column(name = "_actual_date2")
  private LocalDate actualDate2 = null;
  @Column(name = "_actual_date3")
  private LocalDate actualDate3 = null;
  @Column(name = "_actual_date4")
  private LocalDate actualDate4 = null;
  @Column(name = "_actual_time2")
  private String actualTime2 = "0";
  @Column(name = "_actual_time3")
  private String actualTime3 = "0";
  @Column(name = "_actual_time4")
  private String actualTime4 = "0";
  @Column(name = "_hours1_2", length = 55)
  private String hours1_2 = "00:00";
  @Column(name = "_hours1_3", length = 55)
  private String hours1_3 = "00:00";
  @Column(name = "_hours1_4", length = 55)
  private String hours1_4 = "00:00";
  @Column(name = "_min1", length = 2)
  private String min1 = "0";
  @Column(name = "_hours2_2")
  private String hours2_2 = "00:00";
  @Column(name = "_hours2_3")
  private String hours2_3 = "00:00";
  @Column(name = "_hours2_4")
  private String hours2_4 = "00:00";
  @Column(name = "_min2", length = 2)
  private String min2 = "0";
  @Column(name = "activity_type", length = 25)
  private String activityType = "1";
  @Column(name = "_resp5", length = 5)
  private String resp5 = "0";
  @Column(name = "_resp6", length = 5)
  private String resp6 = "0";
  @Column(name = "_resp7", length = 5)
  private String resp7 = "0";
  @Column(name = "_resp8", length = 5)
  private String resp8 = "0";
  @Column(name = "_resp9", length = 5)
  private String resp9 = "0";
  @Column(name = "_actual_date_5")
  private LocalDate actualDate_5;
  @Column(name = "_actual_date_6")
  private LocalDate actualDate_6;
  @Column(name = "_actual_date_7")
  private LocalDate actualDate_7;
  @Column(name = "_actual_date_8")
  private LocalDate actualDate_8;
  @Column(name = "_actual_date_9")
  private LocalDate actualDate_9;
  @Column(name = "_actual_date5")
  private LocalDate actualDate5;
  @Column(name = "_actual_date6")
  private LocalDate actualDate6;
  @Column(name = "_actual_date7")
  private LocalDate actualDate7;
  @Column(name = "_actual_date8")
  private LocalDate actualDate8;
  @Column(name = "_actual_date9")
  private LocalDate actualDate9;
  @Column(name = "_actual_time5")
  private String actualTime5 = "0";
  @Column(name = "_actual_time6")
  private String actualTime6 = "0";
  @Column(name = "_actual_time7")
  private String actualTime7 = "0";
  @Column(name = "_actual_time8")
  private String actualTime8 = "0";
  @Column(name = "_actual_time9")
  private String actualTime9 = "0";
  @Column(name = "_hours1_5", length = 55)
  private String hours1_5 = "00:00";
  @Column(name = "_hours1_6", length = 55)
  private String hours1_6 = "00:00";
  @Column(name = "_hours1_7", length = 55)
  private String hours1_7 = "00:00";
  @Column(name = "_hours1_8", length = 55)
  private String hours1_8 = "00:00";
  @Column(name = "_hours1_9", length = 55)
  private String hours1_9 = "00:00";
  @Column(name = "_hours2_5", length = 55)
  private String hours2_5 = "00:00";
  @Column(name = "_hours2_6", length = 55)
  private String hours2_6 = "00:00";
  @Column(name = "_hours2_7", length = 55)
  private String hours2_7 = "00:00";
  @Column(name = "_hours2_8", length = 55)
  private String hours2_8 = "00:00";
  @Column(name = "_hours2_9", length = 55)
  private String hours2_9 = "00:00";
}