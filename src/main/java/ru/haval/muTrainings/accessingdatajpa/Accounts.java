package ru.haval.muTrainings.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity(name = "accounts")
@Immutable
// @Table
// @Subselect("SELECT * FROM trainings.accounts;")
public class Accounts {
  @Id
  @Column(name = "user_id")
  private Long id;
  private String FIO;
  private String username;
  private String role;
}
