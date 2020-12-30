package ru.haval.muTrainings.accessingdatajpa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "trainings_names_list")
public class TrainingName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private Integer id;

    @Column(name = "training", unique = true)
    private String text;

    private Integer trainingPeriod;

    private Boolean del = false;

}