package ru.haval.workRecording;

import lombok.Data;

@Data
public class ActionPlanHeader extends Translated {
  private String id;
  private String pmNum;
  private String type;
  private String description;
  private String dueDate;
  private String equipment;
  private String executor;
  private String responsible;
  private String owner;
  private String priorityIconPath;
  private String activityTypeIconPath;
  private String instruction;
  private String apTitle;

  public ActionPlanHeader(String language) {
    super(language);
  }
}
