package ru.haval.muTrainings.accessingdatajpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name="trainings_departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departmentId")
    private long id;
    @Column(name = "department", unique = true)
    private String text;
    @Column
    private Boolean del = false;
}
