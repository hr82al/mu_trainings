package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Accounts, Long> {
  List<Accounts> findAll();
}
