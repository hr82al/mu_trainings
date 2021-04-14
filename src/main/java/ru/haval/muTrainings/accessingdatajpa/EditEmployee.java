package ru.haval.muTrainings.accessingdatajpa;

import java.time.LocalDate;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.haval.workRecording.accessingdatajpa.HmmrMuStaff;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsers;

@Data
@RequiredArgsConstructor
public class EditEmployee {
  private Long userId;
  private String ruFirstName;
  private String ruLastName;
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
  private String ruPosition; // select
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

  public WorkRecordingUsers getWorkRecordingUsers() {
    WorkRecordingUsers user = new WorkRecordingUsers();
    user.setFirstName(getRuFirstName());
    user.setLastName(getRuLastName());
    user.setCreateDate(LocalDate.now());
    user.setLogin(getLogin());
    user.setPassword(getPassword());
    user.setRole(getRole());
    user.setUserDel(false);
    return user;
  }

  public void setWorkRecordingUser(WorkRecordingUsers user) {
    setUserId(user.getId());
    setRuFirstName(user.getFirstName());
    setRuLastName(user.getLastName());
    setLogin(user.getLogin());
    setPassword(user.getPassword());
    setRole(user.getRole());
  }

  public HmmrMuStaff getHmmrMuStaff() {
    HmmrMuStaff staff = new HmmrMuStaff();
    staff.setStaffId(getStaffId());
    staff.setUserLettersId(getUserLettersId());
    staff.setUserId(getUserId());
    staff.setRuFirstName(getRuFirstName());
    staff.setRuLastName(getRuLastName());
    staff.setPatronymic(getPatronymic());
    staff.setEnFirstName(getEnFirstName());
    staff.setEnLastName(getEnLastName());
    staff.setBirthDate(getBirthDate());
    staff.setSection(getSection());
    staff.setShop(getShop());
    staff.setTeam(getTeam());
    staff.setShift(getShift());
    staff.setEnPosition(getEnPosition());
    staff.setRuPosition(getRuPosition());
    staff.setGwmId(getGwmId());
    staff.setBeginDate(getBeginDate());
    staff.setEndDate(getEndDate());
    staff.setEmail(getEmail());
    staff.setSkype(getSkype());
    staff.setPhone1(getPhone1());
    staff.setPhone2(getPhone2());
    staff.setAddress(getAddress());
    staff.setNumPlate(getNumPlate());
    staff.setShoeSize(getShoeSize());
    staff.setClothesSize(getClothesSize());
    return staff;
  }

  public void setHmmrMuStaff(HmmrMuStaff staff) {
    setStaffId(staff.getStaffId());
    setUserLettersId(staff.getUserLettersId());
    setUserId(staff.getId());
    setRuFirstName(staff.getRuFirstName());
    setRuLastName(staff.getRuLastName());
    setPatronymic(staff.getPatronymic());
    setEnFirstName(staff.getEnFirstName());
    setEnLastName(staff.getEnLastName());
    setBirthDate(staff.getBirthDate());
    setSection(staff.getSection());
    setShop(staff.getShop());
    setTeam(staff.getTeam());
    setShift(staff.getShift());
    setEnPosition(staff.getEnPosition());
    setRuPosition(staff.getRuPosition());
    setGwmId(staff.getGwmId());
    setBeginDate(staff.getBeginDate());
    setEndDate(staff.getEndDate());
    setEmail(staff.getEmail());
    setSkype(staff.getSkype());
    setPhone1(staff.getPhone1());
    setPhone2(staff.getPhone2());
    setAddress(staff.getAddress());
    setNumPlate(staff.getNumPlate());
    setShoeSize(staff.getShoeSize());
    setClothesSize(staff.getClothesSize());
  }
}
