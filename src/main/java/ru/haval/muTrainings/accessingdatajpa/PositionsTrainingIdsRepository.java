package ru.haval.muTrainings.accessingdatajpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface PositionsTrainingIdsRepository extends CrudRepository<PositionTrainingIds, Long> {
  List<PositionTrainingIds> findAll();

  List<PositionTrainingIds> findByDelIsFalse();

  List<PositionTrainingIds> findByDelIsFalseOrderByPositionIdAsc();

  List<PositionTrainingIds> findByDelOrderByPositionIdAsc(Boolean del);

}
