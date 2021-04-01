package ru.haval.workRecording.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "hmmr_action_plan", schema = "hmmr_mu", catalog = "hmmr_mu")
public class ActionPlan {
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
  private String tskMaker;
  @Column(name = "otv_for_task")
  private String otvForTask = "need select";
  @Column(name = "Otv")
  private String otv = "need select";
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
  @Column(name = "Status")
  Boolean status = false;
}
