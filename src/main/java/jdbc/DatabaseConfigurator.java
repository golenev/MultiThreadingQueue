package jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;


public class DatabaseConfigurator {
    private static JdbcTemplate jdbcTemplate;

    public static synchronized JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate != null && !isJdbcTemplateClosed()) {
            return jdbcTemplate;
        }
        String url = "jdbc:postgresql://localhost:34567/mydatabase";
        String username = "myuser";
        String password = "mypassword";
        jdbcTemplate = createJdbcTemplate(url, username, password);
        return jdbcTemplate;
    }

    private static boolean isJdbcTemplateClosed() {
        try {
            return jdbcTemplate.getDataSource().getConnection().isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    private static JdbcTemplate createJdbcTemplate(String url, String username, String password) {
        DataSource dataSource = getDataSource(url, username, password);
        return new JdbcTemplate(dataSource);
    }

    private static DataSource getDataSource(String url, String username, String password) {
        String driverClassName =  "org.postgresql.Driver";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        return dataSource;
    }
}
