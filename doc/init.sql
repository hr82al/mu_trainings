# mu_trainings
#The program controls terms of trainings of employers.

##### Таблицы проекта

# Работники (trainings_employees)
CREATE TABLE IF NOT EXISTS trainings_employees
(
		user_id INT NOT NULL PRIMARY KEY,
		position_id INT NOT NULL,
		department_id INT NOT NULL,
		del BOOL DEFAULT 0
);

# Должности (trainings_positions)
CREATE TABLE IF NOT EXISTS trainings_positions
(
		position_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		position VARCHAR(255) NOT NULL UNIQUE,
		del BOOL DEFAULT 0
);



#Департаменты
CREATE TABLE IF NOT EXISTS trainings_departments
(
		department_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		department VARCHAR(255) NOT NULL UNIQUE,
		del BOOL DEFAULT 0
);

#Список названий лицензированный обучений trainingsNamesList
CREATE TABLE IF NOT EXISTS trainings_names_list
(
		training_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		training VARCHAR(255) NOT NULL UNIQUE,
		training_period INT NOT NULL,
		del BOOL DEFAULT 0
);

##### Виды

# Вид сотрудники
CREATE VIEW employees AS SELECT
		hs.user_id,
		CONCAT(hs.L_Name_RUS, ' ', hs.F_Name_RUS, ' ',  hs.Otchestvo) AS FIO,
		tp.position, td.department
FROM hmmr_mu_staff hs
		LEFT JOIN trainings_employees te ON hs.user_id = te.user_id
		LEFT JOIN trainings_positions tp ON te.position_id = tp.position_id
		LEFT JOIN trainings_departments td ON te.department_id = td.department_id
		WHERE user_del=0;


