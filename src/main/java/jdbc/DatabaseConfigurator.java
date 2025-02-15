package jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DatabaseConfigurator {
    // Синглтон для DataSource
    private static DataSource dataSource;
    // Синглтон для JdbcTemplate
    private static JdbcTemplate jdbcTemplate;

    // Приватный конструктор, чтобы запретить создание экземпляров класса
    private DatabaseConfigurator() {}

    // Метод для получения JdbcTemplate
    public static synchronized JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(getDataSource());
        }
        return jdbcTemplate;
    }

    // Метод для получения DataSource (синглтон)
    private static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            String url = "jdbc:postgresql://localhost:34567/mydatabase";
            String username = "myuser";
            String password = "mypassword";
            dataSource = createDataSource(url, username, password);
        }
        return dataSource;
    }

    // Создание DataSource
    private static DataSource createDataSource(String url, String username, String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
