package ru.haval.muTrainings.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Immutable

public class PositionTrainingNames {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "position_id")
  private Long positionId;
  @Column(name = "training_id")
  private Long trainingId;
  private Boolean optional;
  private String position;
  private String training;
}
