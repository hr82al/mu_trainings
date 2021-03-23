package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

public interface WorkRecordingUsersRepository extends CrudRepository<WorkRecordingUsers, Long> {
  boolean existsByLogin(String login);
}
