package ru.haval.workRecording.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hmmr_pm", schema = "hmmr_mu", catalog = "hmmr_mu")
public class HmmrPm {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  @Column(name = "user_id")
  Long userId = 1L;
  @Column(name = "instruction_num", length = 25)
  String instructionNum = null;
  @Column(name = "eq_id", length = 25)
  String eqId = "1";
  @Column(name = "pm_group", length = 45)
  String pmGroup = "0";
  @Column(name = "pm_resp", length = 10)
  String pmResp = "0";
  @Column(name = "pm_executor", length = 45)
  String pmExecutor = "need select";
  @Column(name = "onoff_Line", length = 45)
  String onOffLine;
  @Column(name = "del_rec")
  Boolean delRec = false;
}
