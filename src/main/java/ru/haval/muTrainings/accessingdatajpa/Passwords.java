package ru.haval.muTrainings.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Immutable
@Table(name = "passwords")
public class Passwords {
  @Id
  Long id;
  private String user;
  @Column(name = "passwd")
  private String password;
}
