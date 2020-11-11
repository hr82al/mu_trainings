package ru.haval.muTrainings.accessingdatajpa;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/*



*/

@Data
@RequiredArgsConstructor
@Entity(name="hmmr_mu_staff")
public class Staff {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id_num")
    private Long idNum;
    @Column(name="STAFF_ID")
    private String staffId;
//ID	varchar(5)	YES	UNI
    @Column(name="ID", unique = true)
    private String id;
//    user_id	int	NO	UNI
    @Column(name="user_id", unique = true)
    @NotNull
    private Integer userId;
//L_Name_RUS	varchar(12)	YES
    @Column(name="L_Name_RUS")
    private String lastNameRu;
//F_Name_RUS	varchar(12)	YES
    @Column(name="F_Name_RUS")
    private String firstNameRu;
//Otchestvo	varchar(25)	YES
    @Column(name="Otchestvo")
    private String patronymic;
//First_Name	varchar(12)	YES
    @Column(name="First_Name")
    private String firstName;
//Last_Name	varchar(12)	YES
    @Column(name = "Last_Name")
    String lastName;
//DoB	date	YES
    @Column(name = "DoB")
    private LocalDate birthday;
//Sec	varchar(5)	YES
    @Column(name="Sec")
    private String department;
//Group_S	varchar(5)	YES
    @Column(name="Group_S")
    private String shopGroup;
//Team	varchar(255)	YES
    @Column(name="Team")
    private String team;
//WorkShift	int	NO	MUL	8
    @Column(name="WorkShift")
    private Integer workShift = 8;
//Shift	varchar(255)	YES		-
    @Column(name="Shift")
    String shift;
//Position	varchar(45)	YES
    @Column(name="Position")
    String position;
//Position_RUS	varchar(255)	YES
    @Column(name="Position_RUS")
    String positionRu;
//HR	int	NO	MUL	0
    @Column(name="HR")
    Integer HR;
//GWM_ID	varchar(25)	YES
    String GWM_ID;
//Date_of_Start	date	YES
    @Column(name="Date_of_Start")
    LocalDate startDate;
//E_Mail	varchar(45)	YES		-
    @Column(name="E_Mail")
    String email;
//Skype	varchar(55)	YES		-
    @Column(name="Skype")
    String skype;
//Cell_1	varchar(25)	YES		-
    @Column(name="Cell_1")
    String cellphone1;
//Cell_2	varchar(25)	YES		-
    @Column(name="Cell_2")
    String cellphone2;
//Address	varchar(255)	YES
    @Column(name="Address")
    String address;
//Avto	varchar(75)	YES		-
    @Column(name="Avto")
    String carId;
//Shoes_size	int	NO	MUL
    @Column(name="Shoe_size")
    Integer shoeSize;
//Clothes_size	int	NO	MUL
    @Column(name="Clothes_size")
    Integer clothesSize;
//Working	int	NO	MUL	1
    @Column(name="Working")
    Integer working = 1;
//Quit_Date	date	YES
    @Column(name="Quit_Date")
    LocalDate quitDate;
//user_del	int	YES		0		0
    @Column(name="user_del")
    Integer isUserDel = 1;

    @OneToOne/*(cascade = CascadeType.ALL)
    @JoinColumn(name = "", referencedColumnName = "user_id")*/
    private Employee employee;
}
