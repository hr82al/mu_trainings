package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

public interface WorkRecordingUsersRepository extends CrudRepository<WorkRecordingUsers, Long> {
  Boolean existsByLogin(String login);

  Boolean existsByPassword(String value);
}
