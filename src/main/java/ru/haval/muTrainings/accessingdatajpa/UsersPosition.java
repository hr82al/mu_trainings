package ru.haval.muTrainings.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "trainings_employees")
public class UsersPosition {
  @Id
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "position_id")
  private Integer positionId;

  @Column(name = "department_id")
  private Integer departmentId;
}