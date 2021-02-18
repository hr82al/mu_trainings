package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionTrainingNamesRepository extends CrudRepository<PositionTrainingNames, Long> {
  @Query(value = "SELECT tpt.*, tp.position, tnl.training FROM trainings_positions_trainings tpt INNER JOIN trainings_positions tp ON tpt.position_id = tp.position_id INNER JOIN trainings_names_list tnl ON tpt.training_id = tnl.training_id;", nativeQuery = true)
  List<PositionTrainingNames> findAll();
}
