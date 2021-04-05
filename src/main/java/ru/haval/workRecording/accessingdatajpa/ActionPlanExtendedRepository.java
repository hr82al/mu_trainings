package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionPlanExtendedRepository extends CrudRepository<ActionPlanExtended, Long> {

  List<ActionPlanExtended> findByDelRecIsFalse();

  @Query(value = "SELECT * FROM hmmr_mu.action_plan_base WHERE (Otv_For_Task = :letters OR Otv = :letters OR Tsk_maker = :letters) AND del_rec = 0;", nativeQuery = true)
  List<ActionPlanExtended> findByLetters(@Param(value = "letters") String letters);
}
