package ru.haval.workRecording.accessingdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WorkRecordingUsersRepository extends CrudRepository<WorkRecordingUsers, Long> {
  Boolean existsByLogin(String login);

  Boolean existsByPassword(String value);

  @Query(value = "SELECT * FROM hmmr_mu.users mu WHERE mu.login NOT IN (SELECT tu.username FROM trainings.users tu) AND mu.user_del = 0;", nativeQuery = true)
  List<WorkRecordingUsers> getAbsentUsers();
}
