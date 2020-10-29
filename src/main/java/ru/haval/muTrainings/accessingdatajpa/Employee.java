package ru.haval.muTrainings.accessingdatajpa;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
//@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
@Entity(name = "users")
public class Employee {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
//first_name	varchar(50)	YES
    @Column(name="first_name", length = 50)
    private String firstNameRu;
//last_name	varchar(50)	YES
    @Column(name="last_name", length = 50)
    private String lastNameRu;
//login	varchar(17)	NO	UNI
    @NotNull
    @Column(unique = true,length = 17)
    private String login;
//passwd	varchar(17)	NO	UNI
    @NotNull
    @Column(name="passwd", length = 17)
    private String password;
//create_date	date	YES
    @Column(name="create_date")
    LocalDate createDate;
//last_login	timestamp	NO		CURRENT_TIMESTAMP	DEFAULT_GENERATED
//role	enum('Administrator','Group Lead','Team Lead','Engeneer','Technics')	NO
//user_del	int	NO		0
//Descript	char(255)	YES



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

