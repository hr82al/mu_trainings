package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UsersPositionRepository extends CrudRepository<UsersPosition, Integer> {
  List<UsersPosition> findAll();
}
