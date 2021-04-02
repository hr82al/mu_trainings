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

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` enum('Administrator','Group Lead','Team Lead','Engeneer','Technics') NOT NULL DEFAULT 'Technics', 
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`)
) DEFAULT CHARSET=utf8


CREATE VIEW accounts AS SELECT concat(`hs`.`L_Name_RUS`,' ',concat(substr(`hs`.`F_Name_RUS`,1,1)),'. ',if((`hs`.`Otchestvo` <> ''),concat(substr(`hs`.`Otchestvo`,1,1),'.'),'')) AS `FIO`,
tu.username, tu.role
FROM hmmr_mu.users hmu LEFT JOIN hmmr_mu.hmmr_mu_staff  hs ON hs.user_id = hmu.id LEFT JOIN trainings.users tu ON hmu.id = tu.user_id WHERE hmu.user_del = 0;

# Table current action plans tasks
CREATE VIEW action_plan_base AS (SELECT hap.id,hap.PM_Num,hap.Type,hap.Description,hap.Due_Date,hap.Equipment,hap.Instruction,hap.Otv_For_Task,hap.Otv,hap.Tsk_maker,hap.flag_otv,hap.flag_oft,hap.flag_tm,hap.Icon,hap.Icon_AT, hap.user_id, hmp.Icon AS priority_icon_path, hat.Icon AS activity_type_icon_path, hmp.Description AS priority, hat.Description AS activity_type, hap.del_rec, hap.shop, hgc.PM_Duration as duration FROM hmmr_action_plan hap INNER JOIN hmmr_mu_prior hmp ON hap.icon =  hmp.ID_TSK INNER JOIN hmmr_activity_type hat ON hap.Icon_AT = hat.Name  INNER JOIN hmmr_mu_staff hms ON hap.Tsk_maker = hms.ID INNER JOIN hmmr_order_type hot ON hot.id = hat.ID_OT AND hap.Type = hot.Name INNER JOIN hmmr_pm hp ON hp.id = hap.PM_Num INNER JOIN hmmr_group_cycle hgc ON hgc.PM_Group = hp.PM_Group);