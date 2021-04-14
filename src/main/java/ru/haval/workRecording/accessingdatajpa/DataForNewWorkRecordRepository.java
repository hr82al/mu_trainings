package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface DataForNewWorkRecordRepository extends CrudRepository<DataForNewWorkRecord, Long> {
  @Query(value = "SELECT hms.user_id as user_id, hps.FL03_Shop_s as shop, hps.FL04_Group_s as `group`, hps.FL05_Line_s as line, hps.FL06_Station_s as station, hps.FL07_Equipment_s as equipment, hap.Equipment as equipment_full, hap.Type as record_type,hap.Otv as task_resp_id, hap.Description as task_description, hap.Icon_AT as activity_type, hap.Description as description FROM hmmr_mu.hmmr_action_plan hap INNER JOIN hmmr_mu.hmmr_mu_staff hms ON  hap.Otv = hms.ID INNER JOIN hmmr_mu.hmmr_pm hp ON hap.PM_Num = hp.id INNER JOIN hmmr_mu.hmmr_plant_structure hps ON hp.Eq_ID = hps.id WHERE hap.id = :id", nativeQuery = true)
  DataForNewWorkRecord findByActionPlanId(@Param(value = "id") Long id);
}
