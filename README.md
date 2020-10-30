# mu_trainings
The program controls terms of trainings of employers.

##### Таблицы проекта

* Работники (trainings_employees)
CREATE TABLE IF NOT EXISTS trainings_employees
(
		user_id INT NOT NULL PRIMARY KEY,
		position_id INT NOT NULL,
		department_id INT NOT NULL
);

* Должности (trainings_positions)
CREATE TABLE IF NOT EXISTS trainings_positions
(
		position_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		position VARCHAR(255) NOT NULL
);



* Департаменты
CREATE TABLE IF NOT EXISTS trainings_departments
(
		department_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		department VARCHAR(255) NOT NULL
);

###### Виды

* Вид сотрудники
CREATE VIEW employees AS SELECT 
		hs.user_id,
		CONCAT(hs.L_Name_RUS, ' ', hs.Otchestvo, ' ', hs.F_Name_RUS) AS FIO,
		tp.position, td.department
FROM hmmr_mu_staff hs 
		LEFT JOIN trainings_employees te ON hs.user_id = te.user_id 
		LEFT JOIN trainings_positions tp ON te.position_id = tp.position_id
		LEFT JOIN trainings_departments td ON te.department_id = td.department_id
		WHERE user_del=0;
		
		
		
		
		
		
		
<!--th:text="#{${'seedstarter.type.' + position}}">Должность</option>-->
 <!-- <td th:text="${employee.position}">Должность</td>-->
