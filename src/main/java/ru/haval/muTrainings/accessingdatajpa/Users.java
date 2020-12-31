package ru.haval.muTrainings.accessingdatajpa;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_num")
    private Long idNum;

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

    @Column(name = "Desctipt")
    private String Description;
}
