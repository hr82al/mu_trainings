package ru.haval.muTrainings.accessingdatajpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor    
@Entity
@Table(name = "trainings_positions_trainings")
public class PositionTraining {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    Position positions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id")
    private TrainingName trainingName;

}
