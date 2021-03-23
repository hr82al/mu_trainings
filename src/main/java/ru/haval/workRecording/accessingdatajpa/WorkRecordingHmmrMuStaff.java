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
@Table(name = "hmmr_mu_staff", schema = "hmmr_mu", catalog = "hmmr_mu")
public class WorkRecordingHmmrMuStaff {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_num")
  private Long id;
  @Column(name = "Position_RUS")
  private String positionRus;
  @Column(name = "Position")
  private String position;
}
