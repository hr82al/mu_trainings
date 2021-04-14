package ru.haval.workRecording.accessingdatajpa;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActionPlanRepository extends CrudRepository<ActionPlan, Long> {
  @Transactional
  @Modifying
  @Query(value = "UPDATE hmmr_mu.hmmr_action_plan hap SET hap.flag_otv = :otv, hap.flag_oft = :oft, hap.flag_tm = :tm WHERE id = :id ;", nativeQuery = true)
  void setFlags(@Param(value = "id") Long id, @Param(value = "otv") String otv, @Param(value = "oft") String oft,
      @Param(value = "tm") String tm);
}
