package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportsRepository extends CrudRepository<ActionPlanExtended, Long> {

  // The number of planned works
  @Query(value = "SELECT SUBSTR(Equipment FROM 1 FOR 1), COUNT(*)  FROM hmmr_mu.hmmr_action_plan hap WHERE Due_Date LIKE CONCAT(:year,'-%') GROUP BY SUBSTR(Equipment FROM 1 FOR 1)", nativeQuery = true)
  List<List<String>> getTotalPlanedWorks(@Param(value = "year") String year);

  // The actual number of works planned for the given time.
  @Query(value = "SELECT SUBSTR(Equipment FROM 1 FOR 1), COUNT(*) FROM hmmr_mu.hmmr_action_plan hap   LEFT JOIN hmmr_mu.hmmr_work_recording hwr ON hap.id = hwr.ap_num WHERE Due_Date LIKE CONCAT(:year,'-%') AND hwr.id IS NOT NULL AND hap.del_rec = 1 AND flag_otv <> 0 GROUP BY SUBSTR(Equipment FROM 1 FOR 1)", nativeQuery = true)
  List<List<String>> getCompletedPlannedWorksTotal(@Param(value = "year") String year);

  // The actual number of works not completed by plan for given time.
  @Query(value = "SELECT SUBSTR(Equipment FROM 1 FOR 1), COUNT(*) FROM hmmr_mu.hmmr_action_plan hap LEFT JOIN hmmr_mu.hmmr_work_recording hwr ON hap.id = hwr.ap_num WHERE Due_Date LIKE CONCAT(:year,'-%') AND (hwr.id IS NULL OR hap.del_rec = 0 AND flag_otv = 0) GROUP BY SUBSTR(Equipment FROM 1 FOR 1)", nativeQuery = true)
  List<List<String>> getNotCompletedPlannedWorksTotal(@Param(value = "year") String year);

  // The completed works for given time. It includes also works planned in other
  // month.
  @Query(value = "SELECT FL_WSH, COUNT(*) FROM hmmr_mu.hmmr_work_recording hwr LEFT JOIN hmmr_mu.hmmr_action_plan hap ON hap.id = hwr.ap_num WHERE hap.id IS NOT NULL AND WR_End_Date LIKE CONCAT(:year, '-%') GROUP BY FL_WSH;", nativeQuery = true)
  List<List<String>> getCompletedWorksTotal(@Param(value = "year") String year);
}
