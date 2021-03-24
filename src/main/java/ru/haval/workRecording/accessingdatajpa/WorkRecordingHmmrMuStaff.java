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
@Table(name = "hmmr_mu_staff", schema = "hmmr_mu", catalog = "hmmr_mu")
public class WorkRecordingHmmrMuStaff {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_num")
  private Long id;
  @Column(name = "STAFF_ID")
  private String staffId;
  @Column(name = "ID")
  private String userLettersId;
  @Column(name = "user_id")
  private Long userId;
  @Column(name = "L_Name_RUS")
  private String ruLastName;
  @Column(name = "F_Name_RUS")
  private String ruFirstName;
  @Column(name = "Otchestvo")
  private String Patronymic;
  @Column(name = "First_Name")
  private String enFirstName;
  @Column(name = "Last_Name")
  private String enLastName;
  @Column(name = "DoB")
  private String birthDate;
  @Column(name = "Sec")
  private String section;
  @Column(name = "Group_S")
  private String shop;
  @Column(name = "Team")
  private String team;
  @Column(name = "workshift")
  private Long shift;
  // not used
  // @Column(name = "Shift")
  // private String Shift;
  @Column(name = "Position")
  private String enPosition;
  @Column(name = "Position_RUS")
  private String ruPosition;
  // What is this? may be a rest of the gvm id
  // @Column(name = "HR")
  // private Long hr;
  @Column(name = "GWM_ID")
  private String gwmId;
  @Column(name = "Date_of_Start")
  private LocalDate beginDate;
  @Column(name = "E_Mail")
  private String email;
  @Column(name = "Skype")
  private String skype;
  @Column(name = "Cell_1")
  private String phone1;
  @Column(name = "Cell_2")
  private String phone2;
  @Column(name = "Address")
  private String address;
  @Column(name = "Avto")
  private String numPlate;
  @Column(name = "Shoes_size")
  private Long shoeSize;
  @Column(name = "Clothes_size")
  private Long clothesSize;
  // @Column(name = "Working")
  // private Long working;
  @Column(name = "Quit_Date")
  private LocalDate endDate;
  @Column(name = "user_del")
  private Boolean userDel;
}
