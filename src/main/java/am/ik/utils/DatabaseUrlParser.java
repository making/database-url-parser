package am.ik.utils;

import java.net.URI;
import java.util.Objects;

public class DatabaseUrlParser {

	private final String scheme;

	private final String host;

	private final int port;

	private final String database;

	private final String username;

	private final String password;

	private final String query;

	public DatabaseUrlParser(String databaseUrl) {
		final URI uri = URI.create(Objects.requireNonNull(databaseUrl, "'databaseUrl' must not be null."));
		this.scheme = uri.getScheme();
		this.host = uri.getHost();
		this.port = uri.getPort();
		final String path = uri.getPath();
		this.database = path.startsWith("/") ? path.substring(1) : path;
		final String[] userInfo = uri.getUserInfo().split(":");
		this.username = userInfo[0];
		this.password = userInfo[1];
		this.query = uri.getQuery();
	}

	public String getScheme() {
		return scheme;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getQuery() {
		return query;
	}

	public String getJdbcScheme() {
		if (this.scheme.equals("postgres")) {
			return "postgresql";
		}
		return this.scheme;
	}

	public String getJdbcUrl() {
		final StringBuilder url = new StringBuilder();
		url.append("jdbc:").append(this.getJdbcScheme()).append("://").append(this.host);
		if (this.port > 0) {
			url.append(":").append(this.port);
		}
		url.append("/").append(this.database);
		if (this.query != null && !this.query.isBlank()) {
			url.append("?").append(this.query);
		}
		return url.toString();
	}

}
