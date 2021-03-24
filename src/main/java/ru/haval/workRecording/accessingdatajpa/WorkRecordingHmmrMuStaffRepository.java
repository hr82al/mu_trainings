package ru.haval.workRecording.accessingdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WorkRecordingHmmrMuStaffRepository extends CrudRepository<WorkRecordingHmmrMuStaff, Long> {
  List<WorkRecordingHmmrMuStaff> findAll();

  @Query(value = "SELECT * FROM hmmr_mu.hmmr_mu_staff GROUP BY Position_RUS;", nativeQuery = true)
  List<WorkRecordingHmmrMuStaff> getRuPositions();

  @Query(value = "SELECT * FROM hmmr_mu.hmmr_mu_staff GROUP BY Position;", nativeQuery = true)
  List<WorkRecordingHmmrMuStaff> getEnPosition();

  Boolean existsByStaffId(String value);

  Boolean existsByUserLettersId(String value);

}