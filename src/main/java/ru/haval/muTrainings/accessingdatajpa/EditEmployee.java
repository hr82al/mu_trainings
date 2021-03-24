package ru.haval.muTrainings.accessingdatajpa;

import java.time.LocalDate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EditEmployee {
  private Long id;
  private String firstName;
  private String lastName;
  private String patronymic;
  private String enFirstName;
  private String enLastName;
  private LocalDate birthDate;
  private String email;
  private String skype;
  private String phone1;
  private String phone2;
  private String address;
  private String numPlate;
  private String staffId;
  private String userLettersId;
  private String section; // select
  private String shop; // select
  private String team; // select
  private String position; // select
  private String enPosition; // select
  private Long shift;
  private String gwmId;
  private Long shoeSize;
  private Long clothesSize;
  private LocalDate beginDate;
  private LocalDate endDate;
  private String login;
  private String password;
  private String role; // select
}
