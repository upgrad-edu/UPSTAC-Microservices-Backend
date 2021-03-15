package org.upgrad.upstac;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

import java.io.File;

@SpringBootApplication
@Log4j2
public class UpstacApplication {

	public static void main(String[] args) {


		//SpringApplication.run(UpstacApplication.class, args);

		String fileName = System.getProperty("user.home") + File.separator + "upstacApp.pid";
		SpringApplication application = new SpringApplication(UpstacApplication.class);
		log.info("Updated Process Id is available on " + fileName);
		application.addListeners(new ApplicationPidFileWriter(fileName));
		application.run();
	}  


}
