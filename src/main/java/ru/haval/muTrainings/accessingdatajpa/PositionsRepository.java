package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PositionsRepository extends CrudRepository<Position, Integer> {
    List<Position> findAll();

    List<Position> findByOrderByIdAsc();

    List<Position> findByOrderByTextAsc();

    List<Position> findByDelIsFalseOrderByTextAsc();

    List<Position> findByDelIsFalse();
}
