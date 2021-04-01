package ru.haval.workRecording.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ActionPlanExtendedRepository extends CrudRepository<ActionPlanExtended, Long> {

  List<ActionPlanExtended> findByDelRecIsFalse();

}
