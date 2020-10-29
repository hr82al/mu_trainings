# mu_trainings
The program controls terms of trainings of employers.

###### SQL Employees
CREATE TABLE muTrainingsEmployees
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	userId INT NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	patronymic VARCHAR(50) NOT NULL,
	firstName VARCHAR(50) NOT NULL,
	position VARCHAR(255),
	FOREIGN KEY (lastName) REFERENCES users(last_name) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (patronymic) REFERENCES hmmr_mu_staff (Otchestvo) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (firstName) REFERENCES users(first_name) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (position) REFERENCES hmmr_mu_staff(Position_RUS) ON DELETE CASCADE ON UPDATE CASCADE
	)
