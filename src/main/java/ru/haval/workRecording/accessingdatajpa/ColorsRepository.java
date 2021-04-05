package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ColorsRepository extends CrudRepository<Colors, Long> {
  List<Colors> findAll();
}
