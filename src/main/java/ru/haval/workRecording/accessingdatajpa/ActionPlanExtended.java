package ru.haval.workRecording.accessingdatajpa;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Immutable
@Table(name = "action_plan_base", catalog = "hmmr_mu", schema = "hmmr_mu")
public class ActionPlanExtended {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "pm_num")
  private Long pmNum = 0L;
  @Column(name = "Type", length = 5)
  private String type = "PM";
  @Column(name = "Description")
  private String description;
  @Column(name = "due_date")
  private String dueDate;
  @Column(name = "Equipment")
  private String equipment;
  @Column(name = "Instruction")
  private String instruction;
  @Column(name = "tsk_maker")
  private String owner;
  @Column(name = "otv_for_task")
  private String responsible = "need select";
  @Column(name = "Otv")
  private String executor = "need select";
  @Column(name = "user_id")
  private String userId;
  private String shop = "P";
  @Column(name = "del_rec")
  private Boolean delRec = false;
  @Column(name = "flag_otv")
  private String flagOtv = "0";
  @Column(name = "flag_oft")
  private String flagOft = "0";
  @Column(name = "flag_tm")
  private String flagTm = "0";
  @Column(name = "Icon")
  private String icon = "1";
  @Column(name = "Icon_AT")
  private String iconAt = "1";
  // @Column(name = "Status")
  // Boolean status = false;
  @Column(name = "priority_icon_path")
  private String priorityIconPath;
  @Column(name = "activity_type_icon_path")
  private String activityTypeIconPath;
  private String priority;
  @Column(name = "activity_type")
  private String activityType;
  private String duration;

  public String getPriorityIconPath() {
    return fixPath(priorityIconPath);
  }

  public String getActivityTypeIconPath() {
    return fixPath(activityTypeIconPath);
  }

  private static String fixPath(String path) {
    File file = new File(path);
    String name = file.getName();
    return "/images/" + name;
  }
}
