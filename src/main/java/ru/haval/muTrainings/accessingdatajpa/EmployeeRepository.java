package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long>, JpaSpecificationExecutor {
    List<Employee> findByUserDel(int userDel);
    Employee findById(long id);

}