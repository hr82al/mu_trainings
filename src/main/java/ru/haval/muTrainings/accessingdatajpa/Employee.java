package ru.haval.muTrainings.accessingdatajpa;



import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
//@NoArgsConstructor
@Entity
@Immutable
@Table(name = "employees")
@Subselect("SELECT * FROM hmmr_mu.employees;")
public class Employee {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name="FIO")
    private String fio;
    private String position;
    private String department;
}

