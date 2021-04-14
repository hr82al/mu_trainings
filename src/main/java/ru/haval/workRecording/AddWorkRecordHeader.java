package ru.haval.workRecording;

public class AddWorkRecordHeader extends Translated {
  private String description;
  private String dueDate;
  private String equipment;
  private String startTime;
  private String endTime;
  private String addWorkRecordTitle;

  public AddWorkRecordHeader(String language) {
    super(language);
  }

  public AddWorkRecordHeader() {
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDueDate() {
    return dueDate;
  }

  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  public String getEquipment() {
    return equipment;
  }

  public void setEquipment(String equipment) {
    this.equipment = equipment;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getAddWorkRecordTitle() {
    return addWorkRecordTitle;
  }

  public void setAddWorkRecordTitle(String addWorkRecordTitle) {
    this.addWorkRecordTitle = addWorkRecordTitle;
  }
}
