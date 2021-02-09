package ru.haval.muTrainings.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "trainings_employees")
public class Employees {
  @Id
  @Column(name = "user_id")
  private Long userId;
  @Column(name = "position_id")
  private String positionId;
  @Column(name = "department_id")
  private String departmentId;
}
