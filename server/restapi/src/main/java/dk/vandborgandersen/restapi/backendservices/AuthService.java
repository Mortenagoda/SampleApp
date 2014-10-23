package dk.vandborgandersen.restapi.backendservices;

import dk.vandborgandersen.restapi.domain.Credentials;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class AuthService {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Credentials> rowMapper;

    public AuthService(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Credentials validateCredentials(Credentials credentials) {
        credentials = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ? AND password = ?", getUserRowMapper(), credentials.getUsername(), credentials.getPassword());

        return credentials;
    }

    public Credentials createCredentials(String username, String password) {
        String newId = UUID.randomUUID().toString();
        jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?)", newId, username, password);

        Credentials credentials = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", getUserRowMapper(), newId);
        return credentials;
    }

    public RowMapper<Credentials> getUserRowMapper() {
        if (rowMapper == null) {
            rowMapper = getRowMapperSync();
        }

        return rowMapper;
    }

    private synchronized RowMapper<Credentials> getRowMapperSync() {
        return new RowMapper<Credentials>() {
            @Override
            public Credentials mapRow(ResultSet resultSet, int i) throws SQLException {
                Credentials credentials = new Credentials();
                credentials.setUsername(resultSet.getString("username"));
                credentials.setPassword(resultSet.getString("password"));
                return credentials;
            }
        };
    }
}
