package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionsTrainingsRepository extends CrudRepository<PositionTraining, Long> {
    List<PositionTraining> findAll();
}
