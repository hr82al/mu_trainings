package ru.haval.muTrainings.accessingdatajpa;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
@Entity(name="user")
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
    @Column(name = "user_del")
    private Integer userDel = 0;
    private String Descript;
}
