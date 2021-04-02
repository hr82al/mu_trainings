package ru.haval.workRecording;

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPmNum() {
    return pmNum;
  }

  public void setPmNum(String pmNum) {
    this.pmNum = pmNum;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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

  public String getExecutor() {
    return executor;
  }

  public void setExecutor(String executor) {
    this.executor = executor;
  }

  public String getResponsible() {
    return responsible;
  }

  public void setResponsible(String responsible) {
    this.responsible = responsible;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getPriorityIconPath() {
    return priorityIconPath;
  }

  public void setPriorityIconPath(String priorityIconPath) {
    this.priorityIconPath = priorityIconPath;
  }

  public String getActivityTypeIconPath() {
    return activityTypeIconPath;
  }

  public void setActivityTypeIconPath(String activityTypeIconPath) {
    this.activityTypeIconPath = activityTypeIconPath;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public String getApTitle() {
    return apTitle;
  }

  public void setApTitle(String apTitle) {
    this.apTitle = apTitle;
  }
}
