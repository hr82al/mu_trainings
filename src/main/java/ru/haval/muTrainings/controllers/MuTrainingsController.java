/**
 * 
 */
package ru.haval.muTrainings.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 	Aleksandr Khomov. hr82al@gmail.com
 *
 */
@Controller
public class MuTrainingsController {

	@GetMapping("/mu_trainings/")
	public String muTrainings() {
		return "muTrainings";
	}
}
