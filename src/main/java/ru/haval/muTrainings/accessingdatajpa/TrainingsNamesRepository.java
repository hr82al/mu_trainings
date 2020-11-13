package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainingsNamesRepository extends CrudRepository<TrainingName, Long> {
    List<TrainingName> findByDelIsFalseOrderByTraining();
}
