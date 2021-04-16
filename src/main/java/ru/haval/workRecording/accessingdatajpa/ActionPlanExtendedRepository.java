package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActionPlanExtendedRepository extends CrudRepository<ActionPlanExtended, Long> {

  List<ActionPlanExtended> findByDelRecIsFalse();

  @Query(value = "SELECT * FROM hmmr_mu.action_plan_base WHERE (Otv_For_Task = :letters OR Otv = :letters OR Tsk_maker = :letters) AND del_rec = 0;", nativeQuery = true)
  List<ActionPlanExtended> findByLetters(@Param(value = "letters") String letters);

  @Query(value = "SELECT * FROM hmmr_mu.action_plan_base WHERE Due_Date LIKE CONCAT(:year, '-%') AND del_rec = 1 ;", nativeQuery = true)
  List<ActionPlanExtended> findByYear(@Param(value = "year") int year);

  // It finds all records in the action plan table by user letters ID
  @Query(value = "SELECT * FROM hmmr_mu.action_plan_base WHERE Otv = 'need select' AND (Otv_For_Task = :letters OR Otv = :letters OR Tsk_maker = :letters) AND del_rec = 0;", nativeQuery = true)
  List<ActionPlanExtended> findWithoutExecutorByUserLettersId(@Param(value = "letters") String letters);

  // The same as the findWithoutExecutorByUserLettersId method but for all users
  @Query(value = "SELECT * FROM hmmr_mu.action_plan_base WHERE Otv = 'need select' AND del_rec = 0;", nativeQuery = true)
  List<ActionPlanExtended> findWithoutExecutor();

  List<ActionPlanExtended> findByShopAndDelRecIsFalse(String shop);

}
