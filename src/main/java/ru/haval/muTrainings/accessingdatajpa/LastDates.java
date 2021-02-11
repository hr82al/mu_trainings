package ru.haval.muTrainings.accessingdatajpa;

import java.time.LocalDate;

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
@Table(name = "trainings_last_dates")
public class LastDates {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "user_id")
  private Integer userId;
  @Column(name = "training_id")
  private Integer trainingId;
  @Column(name = "last_date")
  private LocalDate lastDate;
}
