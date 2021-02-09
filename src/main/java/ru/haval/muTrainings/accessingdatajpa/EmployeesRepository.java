package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

public interface EmployeesRepository extends CrudRepository<Employees, Long> {

}
