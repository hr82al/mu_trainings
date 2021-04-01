package ru.haval;

import java.util.List;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import ru.haval.muTrainings.authentication.User;
import ru.haval.muTrainings.authentication.UserRepository;
import ru.haval.workRecording.accessingdatajpa.ActionPlanExtendedRepository;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsers;
import ru.haval.workRecording.accessingdatajpa.WorkRecordingUsersRepository;

@SpringBootApplication // (scanBasePackages = { "ru.haval.muTrainings", "ru.haval.workRecording" })
public class MuTrainingsApplication {
	private static String warLocation;
	private static String staticTmpFolder;
	@Autowired
	WorkRecordingUsersRepository wUsersRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	ActionPlanExtendedRepository actionPlanRepository;

	public static void main(String[] args) {
		SpringApplication.run(MuTrainingsApplication.class, args);

		// MuTrainingsApplication app = new MuTrainingsApplication();
		// app.initUsers();
	}

	@EventListener(ApplicationReadyEvent.class)
	public void initUsers() {
		List<WorkRecordingUsers> users = wUsersRepository.getAbsentUsers();
		for (WorkRecordingUsers user : users) {
			User tmpUser = new User();
			tmpUser.setId(user.getId());
			tmpUser.setUsername(user.getLogin());
			// generate password
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			String rawPassword = user.getPassword();
			tmpUser.setPassword(bCryptPasswordEncoder.encode(rawPassword));
			tmpUser.setRole(user.getRole());
			tmpUser.setEnabled(true);
			System.out.println(tmpUser);
			userRepository.save(tmpUser);
		}

		String warLocationUrl = MuTrainingsApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		warLocation = new File(warLocationUrl.toString()).getParent();
		if (warLocation.endsWith("bin")) {
			staticTmpFolder = warLocation + "\\main\static\\tmp";
		} else {
			staticTmpFolder = warLocation + "\\classes\\static\\tmp";
		}
	}

	public static String getWarLocation() {
		return warLocation;
	}

	public static String getStaticTmpFolder() {
		return staticTmpFolder;
	}
	/*
	 * @Bean public CommandLineRunner demo(EmployeeRepository repository) { return
	 * (args) -> { // fetch all customers
	 * log.info("Customers found with findAll():"); log.info("****************");
	 * for (Employee employee : repository.findAll()) {
	 * log.info(employee.toString()); } log.info(""); }; }
	 */

}
