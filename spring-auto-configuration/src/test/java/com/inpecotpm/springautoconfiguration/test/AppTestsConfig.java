package com.inpecotpm.springautoconfiguration.test;

import com.inpecotpm.springautoconfiguration.App;
import com.inpecotpm.springautoconfiguration.config.SubAppPackageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


// Option 1
@Configuration // top-level @Configuration classes accidentally get picked up if other tests use component scan
// @TestConfiguration // top-level @TestConfiguration indicates that classes in src/test/java should not be picked up by scanning
@EnableAutoConfiguration(
	exclude = AopAutoConfiguration.class)
@ComponentScan(
	basePackageClasses = App.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = App.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SubAppPackageConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ConfigurationAnnotationTest.ConfigurationAnnotationTestConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = TestConfigurationAnnotationTest.TestConfigurationAnnotationTestConfig.class)}
)
public class AppTestsConfig {
}
