package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TrainingsNamesRepository extends CrudRepository<TrainingName, Long> {
    List<TrainingName> findByDelIsFalseOrderByText();

    List<TrainingName> findByDelIsFalse();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO trainings_names_list (training, training_period, del) VALUES (:training, :trainingPeriod, 0)  ON DUPLICATE KEY UPDATE training = :training, training_period = :trainingPeriod, del = 0 ", nativeQuery = true)
    int addTrainingNameQuery(@Param(value = "training") String training,
            @Param(value = "trainingPeriod") Integer trainingPeriod);
}
