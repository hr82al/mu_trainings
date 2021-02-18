# mu_trainings
#The program controls terms of trainings of employers.

CREATE DATABASE trainings;

USE trainings;
##### Таблицы проекта

# Работники (trainings_employees)
CREATE TABLE IF NOT EXISTS trainings_employees
(
		user_id INT NOT NULL PRIMARY KEY,
		position_id INT NOT NULL,
		department_id INT NOT NULL,
		del BOOL DEFAULT 0
) CHARSET=utf8;

# Должности (trainings_positions)
CREATE TABLE IF NOT EXISTS trainings_positions
(
		position_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		position VARCHAR(255) NOT NULL UNIQUE,
		del BOOL DEFAULT 0
) CHARSET=utf8;

# Каждая дожность должна иметь списой необходимых обучений
CREATE TABLE IF NOT EXISTS trainings_positions_trainings
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	position_id INT NOT NUll,
	training_id INT NOT NULL,
	optional BOOL DEFAULT 0,
	UNIQUE KEY (position_id, training_id),
	FOREIGN KEY (position_id)
        REFERENCES trainings_positions(position_id),
	FOREIGN KEY (training_id)
				REFERENCES trainings_names_list(training_id)
) CHARSET=utf8;

#Департаменты
CREATE TABLE IF NOT EXISTS trainings_departments
(
		department_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		department VARCHAR(255) NOT NULL UNIQUE,
		del BOOL DEFAULT 0
) CHARSET=utf8;

#Список названий лицензированный обучений trainingsNamesList
CREATE TABLE IF NOT EXISTS trainings_names_list
(
		training_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
		training TEXT NOT NULL UNIQUE,
		training_period INT NOT NULL,
		del BOOL DEFAULT 0,
		KEY ix_length_training (training(255))
) CHARSET=utf8;

#Таблица история
CREATE TABLE IF NOT EXISTS trainings_history
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
	training_id int NOT NULL,
	last_date DATE
) CHARSET=utf8;

#Последние даты обучений
CREATE TABLE IF NOT EXISTS trainings_last_dates
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_id INT NOT NULL,
	training_id INT NOT NULL,
	last_date DATE NOT NULL,
	UNIQUE KEY (user_id, training_id)
) CHARSET=utf8;

#Users
CREATE TABLE IF NOT EXISTS trainings_last_dates
(
	user_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(45) NOT NULL,
	password VARCHAR(64) NOT NULL,
	role VARCHAR(45) NOT NULL,
	enabled BOOL NOT NULL
) CHARSET=utf8;

##### Виды

# Вид сотрудники
CREATE VIEW employees AS SELECT
		hs.user_id,
		CONCAT(hs.L_Name_RUS, ' ', CONCAT(SUBSTR(hs.F_Name_RUS,1,1)), '. ', 
		IF (hs.Otchestvo <> '', CONCAT(SUBSTR(hs.Otchestvo,1,1), '.'), '')
    ) AS FIO,
		tp.position, te.position_id, td.department, te.department_id, hs.Otchestvo
FROM hmmr_mu.hmmr_mu_staff hs
		LEFT JOIN trainings_employees te ON hs.user_id = te.user_id
		LEFT JOIN trainings_positions tp ON te.position_id = tp.position_id
		LEFT JOIN trainings_departments td ON te.department_id = td.department_id
		WHERE hs.user_del=0;


