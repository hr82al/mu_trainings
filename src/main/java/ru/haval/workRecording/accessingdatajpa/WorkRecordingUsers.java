package ru.haval.workRecording.accessingdatajpa;

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
@Table(name = "users", schema = "hmmr_mu", catalog = "hmmr_mu")
public class WorkRecordingUsers {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "create_date")
  private LocalDate createDate;
  // a fake name
  // @Column(name = "last_login")
  // private Timestamp lastLogin;
  private String login;
  @Column(name = "passwd")
  private String password;
  // enum('Administrator','Group Lead','Team Lead','Engeneer','Technics')
  private String role;
  @Column(name = "user_del")
  private Boolean userDel;
  // not used
  // @Column(name = "Descript")
  // private String description;
}
