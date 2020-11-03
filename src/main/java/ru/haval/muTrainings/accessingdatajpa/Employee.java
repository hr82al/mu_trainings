package ru.haval.muTrainings.accessingdatajpa;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
//@NoArgsConstructor
@Entity
@Immutable
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name="FIO")
    private String fio;
    private String position;
    private String department;
}

