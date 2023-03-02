package am.ik.utils.spring;

import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.SpringApplication;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.mock.env.MockEnvironment;

import static am.ik.utils.spring.DatabaseUrlEnvironmentPostProcessor.DATABASE_URL_KEY;
import static am.ik.utils.spring.DatabaseUrlEnvironmentPostProcessor.SOURCE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

class DatabaseUrlEnvironmentPostProcessorTest {

	private final SpringApplication application = new SpringApplication();

	@Test
	void postProcessEnvironment() {
		final MockEnvironment environment = new MockEnvironment() {
			@Override
			public Map<String, Object> getSystemEnvironment() {
				return Map.of(DATABASE_URL_KEY,
						"postgres://username:password@postgresql.example.com:5432/dbname?sslmode=disable");
			}
		};
		final DatabaseUrlEnvironmentPostProcessor processor = new DatabaseUrlEnvironmentPostProcessor();
		processor.postProcessEnvironment(environment, application);
		final MutablePropertySources propertySources = environment.getPropertySources();
		final PropertySource<?> propertySource = propertySources.get(SOURCE_NAME);
		assertThat(propertySource).isNotNull();
		assertThat(propertySource.getProperty("spring.datasource.url"))
			.isEqualTo("jdbc:postgresql://postgresql.example.com:5432/dbname?sslmode=disable");
		assertThat(propertySource.getProperty("spring.datasource.username")).isEqualTo("username");
		assertThat(propertySource.getProperty("spring.datasource.password")).isEqualTo("password");
	}

	@Test
	void postProcessEnvironment2() {
		final MockEnvironment environment = new MockEnvironment();
		final DatabaseUrlEnvironmentPostProcessor processor = new DatabaseUrlEnvironmentPostProcessor();
		processor.postProcessEnvironment(environment, application);
		final MutablePropertySources propertySources = environment.getPropertySources();
		final PropertySource<?> propertySource = propertySources.get(SOURCE_NAME);
		assertThat(propertySource).isNull();
	}

}