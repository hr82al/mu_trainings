package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface WorkRecordingFilterRepository extends CrudRepository<WorkRecordingFilter, Long> {
  List<WorkRecordingFilter> findAll();
}
