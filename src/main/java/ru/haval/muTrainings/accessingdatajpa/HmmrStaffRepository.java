package ru.haval.muTrainings.accessingdatajpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface HmmrStaffRepository extends CrudRepository<HmmrStaff, Long> {
  List<HmmrStaff> findAll();
}
