package am.ik.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseUrlParserTest {

	@Test
	void test() {
		final DatabaseUrlParser parser = new DatabaseUrlParser(
				"postgres://username:password@postgresql.example.com:5432/dbname?sslmode=disable");
		assertThat(parser.getScheme()).isEqualTo("postgres");
		assertThat(parser.getHost()).isEqualTo("postgresql.example.com");
		assertThat(parser.getPort()).isEqualTo(5432);
		assertThat(parser.getDatabase()).isEqualTo("dbname");
		assertThat(parser.getUsername()).isEqualTo("username");
		assertThat(parser.getPassword()).isEqualTo("password");
		assertThat(parser.getQuery()).isEqualTo("sslmode=disable");
		assertThat(parser.getJdbcScheme()).isEqualTo("postgresql");
		assertThat(parser.getJdbcUrl())
				.isEqualTo("jdbc:postgresql://postgresql.example.com:5432/dbname?sslmode=disable");
	}

}