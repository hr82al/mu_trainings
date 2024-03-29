package ru.haval.workRecording.accessingdatajpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hmmr_colors", schema = "hmmr_mu", catalog = "hmmr_mu")
public class Colors {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String days;
  @Column(name = "colors")
  private String color;

  public int getDays() {
    return Integer.parseInt(days);
  }
}
