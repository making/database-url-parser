package am.ik.utils.spring;

import java.util.Map;
import java.util.TreeMap;

import am.ik.utils.DatabaseUrlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;

public class DatabaseUrlEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

	public static final String DATABASE_URL_KEY = "DATABASE_URL";

	public static final String SOURCE_NAME = "databaseUrlProperties";

	private final Logger log = LoggerFactory.getLogger(DatabaseUrlEnvironmentPostProcessor.class);

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		final String databaseUrl = (String) environment.getSystemEnvironment().get(DATABASE_URL_KEY);
		if (databaseUrl == null) {
			return;
		}
		log.info("Detect '{}' environment variable", DATABASE_URL_KEY);
		final DatabaseUrlParser urlParser = new DatabaseUrlParser(databaseUrl);
		final Map<String, Object> properties = new TreeMap<>();
		properties.put("spring.datasource.url", urlParser.getJdbcUrl());
		properties.put("spring.datasource.username", urlParser.getUsername());
		properties.put("spring.datasource.password", urlParser.getPassword());
		final MapPropertySource propertySource = new MapPropertySource(SOURCE_NAME, properties);
		final MutablePropertySources propertySources = environment.getPropertySources();
		if (propertySources.contains(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)) {
			propertySources.addBefore(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, propertySource);
		}
		else {
			propertySources.addFirst(propertySource);
		}
	}

	@Override
	public int getOrder() {
		return ConfigDataEnvironmentPostProcessor.ORDER - 1;
	}

}
