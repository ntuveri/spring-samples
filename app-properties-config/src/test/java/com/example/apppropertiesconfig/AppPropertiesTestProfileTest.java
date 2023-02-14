package com.example.apppropertiesconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AppPropertiesTestProfileTest {

	@Autowired
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(AppPropertiesConfigApplication.class, args);
	}

	@Test
	public void printProperties() {
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

		String testResourceSpecific = env.getProperty("app.test.specific");
		System.out.println("app.test.specific: " + testResourceSpecific);
	}
}