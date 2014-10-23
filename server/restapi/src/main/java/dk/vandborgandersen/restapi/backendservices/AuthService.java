package dk.vandborgandersen.restapi.backendservices;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class AuthService {
    private JdbcTemplate jdbcTemplate;

    public AuthService(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public boolean validateEmailPassword(String email, String password) {
        int count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users WHERE username = ? AND password = ?",
                new Object[]{email, password});
        return count == 1;
    }
}
