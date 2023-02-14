package com.example.apppropertiesconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class AppPropertiesConfigApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(AppPropertiesConfigApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Environment env = applicationContext.getEnvironment();

		String applicationName = env.getProperty("app.name");
		System.out.println("app.name: " + applicationName);

		String applicationDefaultValue = env.getProperty("app.default");
		System.out.println("app.default: " + applicationDefaultValue);

		String applicationCompositeGeneric = env.getProperty("app.composite-generic");
		System.out.println("app.composite-generic: " + applicationCompositeGeneric);

		String applicationCompositeSpecific = env.getProperty("app.composite-specific");
		System.out.println("app.composite-specific: " + applicationCompositeSpecific);

		String applicationSelfGeneric = env.getProperty("app.self-generic");
		System.out.println("app.self-generic: " + applicationSelfGeneric);

		String applicationSelfSpecific = env.getProperty("app.self-specific");
		System.out.println("app.self-specific: " + applicationSelfSpecific);

		// String userDir = System.getProperty("user.dir");
		// System.out.println("Current directory is: " + userDir);
	}
}
