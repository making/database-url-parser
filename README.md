## Database Url Parser

[![Apache 2.0](https://img.shields.io/github/license/making/yavi.svg)](https://www.apache.org/licenses/LICENSE-2.0) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/am.ik.util/database-url-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/am.ik.util/database-url-parser) [![Java CI with Maven](https://github.com/making/database-url-parser/actions/workflows/ci.yaml/badge.svg)](https://github.com/making/database-url-parser/actions/workflows/ci.yaml)

```xml
<dependency>
	<groupId>am.ik.util</groupId>
	<artifactId>database-url-parser</artifactId>
	<version>0.1.3</version>
</dependency>
```


The Database Url Parser parses the `DATABASE_URL` environment variable and set the corresponding `spring.datasource.*` properties.

The format of `DATABASE_URL` must be `<scheme>://<username>:<password>@<host>:<port>/<database>(?<options>)`.

If `postgres://username:password@postgresql.example.com:5432/dbname?sslmode=disable` is given in the `DATABASE_URL`, the bellow properties will configured

| Property | Value |
| -------- | ----- |
| `spring.datasource.url` | `jdbc:postgresql://postgresql.example.com:5432/dbname?sslmode=disable` |
| `spring.datasource.username` | `username` |
| `spring.datasource.password` | `password` |

> The order of `PropertySources` added by this library is before `systemEnvironment`.