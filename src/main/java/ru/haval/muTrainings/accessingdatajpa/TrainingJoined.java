package ru.haval.muTrainings.accessingdatajpa;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GenerationType;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trainings_dates")
public class TrainingJoined {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  Employee employee;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "training_id")
  TrainingName trainingName;

  @Column(name = "last_date")
  private LocalDate date;
}
