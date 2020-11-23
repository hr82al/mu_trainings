package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TrainingJoinedRepository extends CrudRepository<TrainingJoined, Long> {
  List<TrainingJoined> findAll();
}
