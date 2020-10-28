package ru.haval.muTrainings.accessingdatajpa;


import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@Entity(name = "users")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String first_name;
    private String last_name;
    @Column(unique = true)
    @NotNull
    private String login;
    @Column(unique = true)
    @NotNull
    private String passwd;
    @NotNull
    private java.sql.Date create_date;
    private Timestamp last_login;
    private String role;
    @NotNull
    private Integer user_del = 0;
    private String Descript;
}

