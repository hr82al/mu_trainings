package ru.haval.muTrainings.accessingdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentsRepository extends CrudRepository<Department, Integer> {
    List<Department> findByDelIsFalseOrderByTextAsc();

    List<Department> findByText(String text);

    @Query("update Department d set d.del = :del where d.text = :text")
    void updateByText(@Param(value = "text") String text, @Param(value = "del") boolean del);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO trainings_departments (department) VALUES (:department)  ON DUPLICATE KEY UPDATE del = 0", nativeQuery = true)
    void addDepartmentQuery(@Param(value = "department") String department);

    @Transactional
    @Modifying
    @Query(value = "UPDATE trainings_departments SET del = 1 WHERE department_id = :id", nativeQuery = true)
    void delById(@Param(value = "id") Long id);

    List<Department> findByDelIsFalse();

}
