package com.inpecotpm.springautoconfiguration.test;

import com.inpecotpm.springautoconfiguration.App;
import com.inpecotpm.springautoconfiguration.SameAppPackageConfig;
import com.inpecotpm.springautoconfiguration.config.AnotherSubAppPackageConfig;
import com.inpecotpm.springautoconfiguration.config.SubAppPackageConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
	initializers = { AppTestInitializer.class },
	classes = { AppTestsConfig.class } // Option 1: External @Configuration class
)
public class AppTests {

	@Autowired
	ApplicationContext applicationContext;

	// Option 2: Nested @Configuration class
	@Configuration
	@EnableAutoConfiguration(
		exclude = AopAutoConfiguration.class
	)
	@ComponentScan(
		basePackageClasses = App.class,
		excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = App.class),
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SubAppPackageConfig.class),
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ConfigurationAnnotationTest.ConfigurationAnnotationTestConfig.class),
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = TestConfigurationAnnotationTest.TestConfigurationAnnotationTestConfig.class)
		}
	)
	static class NestedAppTestsConfig {
	}

	@Test
	public void contextLoads() {
		assertThat(this.applicationContext.getBean(NestedAppTestsConfig.class)).isNotNull();
		assertThat(this.applicationContext.getBean(AppTestsConfig.class)).isNotNull();
		assertThat(this.applicationContext.getBean(SameAppPackageConfig.class)).isNotNull();
		assertThat(this.applicationContext.getBean(AnotherSubAppPackageConfig.class)).isNotNull();

		assertThatThrownBy(() -> this.applicationContext.getBean(SubAppPackageConfig.class))
			.isInstanceOf(NoSuchBeanDefinitionException.class);
		assertThatThrownBy(() -> this.applicationContext.getBean(AopAutoConfiguration.class))
			.isInstanceOf(NoSuchBeanDefinitionException.class);
	}
}
