package am.ik.utils.spring;

import java.util.Map;
import java.util.TreeMap;

import am.ik.utils.DatabaseUrlParser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigDataEnvironmentPostProcessor;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import static org.springframework.core.env.CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME;

public class DatabaseUrlEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

	public static final String DATABASE_URL_KEY = "DATABASE_URL";

	public static final String SOURCE_NAME = "databaseUrlProperties";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		final String databaseUrl = (String) environment.getSystemEnvironment().get(DATABASE_URL_KEY);
		if (databaseUrl == null) {
			return;
		}
		final DatabaseUrlParser urlParser = new DatabaseUrlParser(databaseUrl);
		final Map<String, Object> properties = new TreeMap<>();
		properties.put("spring.datasource.url", urlParser.getJdbcUrl());
		properties.put("spring.datasource.username", urlParser.getUsername());
		properties.put("spring.datasource.password", urlParser.getPassword());
		final MapPropertySource propertySource = new MapPropertySource(SOURCE_NAME, properties);
		final MutablePropertySources propertySources = environment.getPropertySources();
		if (propertySources.contains(COMMAND_LINE_PROPERTY_SOURCE_NAME)) {
			propertySources.addAfter(COMMAND_LINE_PROPERTY_SOURCE_NAME, propertySource);
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
