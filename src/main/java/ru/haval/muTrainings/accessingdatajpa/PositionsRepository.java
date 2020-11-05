package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionsRepository extends CrudRepository<Position, Long> {
    List<Position> findAll();
    List<Position> findByOrderByIdAsc();
}
