package ru.haval.workRecording.accessingdatajpa;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class DataForNewWorkRecord {
  @Id
  private Long userId;
  private String shop;
  private String group;
  private String line;
  private String station;
  private String equipment;
  private String equipmentFull;
  private String recordType;
  private String taskRespId;
  private String activityType;
  private String description;
}
