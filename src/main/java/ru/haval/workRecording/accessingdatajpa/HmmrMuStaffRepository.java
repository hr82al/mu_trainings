package ru.haval.workRecording.accessingdatajpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HmmrMuStaffRepository extends CrudRepository<HmmrMuStaff, Long> {
  List<HmmrMuStaff> findAll();

  @Query(value = "SELECT * FROM hmmr_mu.hmmr_mu_staff GROUP BY Position_RUS;", nativeQuery = true)
  List<HmmrMuStaff> getRuPositions();

  @Query(value = "SELECT * FROM hmmr_mu.hmmr_mu_staff GROUP BY Position;", nativeQuery = true)
  List<HmmrMuStaff> getEnPosition();

  Boolean existsByStaffId(String value);

  Boolean existsByUserLettersId(String value);

  Optional<HmmrMuStaff> findByUserId(Long id);

  Optional<HmmrMuStaff> findIdByUserId(Long id);

}