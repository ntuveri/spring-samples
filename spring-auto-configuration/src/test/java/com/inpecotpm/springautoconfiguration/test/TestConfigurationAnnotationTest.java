package com.inpecotpm.springautoconfiguration.test;

import com.inpecotpm.SuperAppPackageConfig;
import com.inpecotpm.springautoconfiguration.App;
import com.inpecotpm.springautoconfiguration.SameAppPackageConfig;
import com.inpecotpm.springautoconfiguration.config.AnotherSubAppPackageConfig;
import com.inpecotpm.springautoconfiguration.config.SubAppPackageConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestConfigurationAnnotationTest {

	@Autowired
	private ApplicationContext applicationContext;

	@TestConfiguration
	static class TestConfigurationAnnotationTestConfig {}

	@Test
	public void loadContext() {
		assertThat(this.applicationContext.getBean(App.class)).isNotNull();
		assertThat(this.applicationContext.getBean(SameAppPackageConfig.class)).isNotNull();
		assertThat(this.applicationContext.getBean(SubAppPackageConfig.class)).isNotNull();
		assertThat(this.applicationContext.getBean(AnotherSubAppPackageConfig.class)).isNotNull();
		assertThat(this.applicationContext.getBean(AopAutoConfiguration.class)).isNotNull();

		assertThatThrownBy(() -> this.applicationContext.getBean(SuperAppPackageConfig.class))
			.isInstanceOf(NoSuchBeanDefinitionException.class);
	}
}
