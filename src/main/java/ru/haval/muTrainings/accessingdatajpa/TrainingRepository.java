package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

public interface TrainingRepository extends CrudRepository<Training, Long> {
}
