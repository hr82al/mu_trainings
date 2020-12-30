package ru.haval.muTrainings.accessingdatajpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LastDatesRepository extends CrudRepository<LastDate, Long> {
  List<LastDate> findAll();
}
