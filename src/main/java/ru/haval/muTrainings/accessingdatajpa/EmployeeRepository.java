package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    // List<Employee> findByUserDel(int userDel);
    // Employee findById(long id);
    List<Employee> findAll();

    List<Employee> findByOrderByFioAsc();

}