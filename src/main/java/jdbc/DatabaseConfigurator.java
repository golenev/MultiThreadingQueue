package jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DatabaseConfigurator {
    private static DataSource dataSource;
    private static JdbcTemplate jdbcTemplate;

    private DatabaseConfigurator() {}

    public static synchronized JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(getDataSource());
        }
        return jdbcTemplate;
    }

    private static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            String url = "jdbc:postgresql://localhost:34567/mydatabase";
            String username = "myuser";
            String password = "mypassword";
            dataSource = createDataSource(url, username, password);
        }
        return dataSource;
    }

    private static DataSource createDataSource(String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
