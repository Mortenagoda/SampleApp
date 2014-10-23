package dk.vandborgandersen.restapi.backendservices;

import dk.vandborgandersen.restapi.domain.ApiKey;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class ApiKeysService {
    private static final Logger LOGGER = Logger.getLogger(ApiKeysService.class.getName());

    private final JdbcTemplate jdbcTemplate;
    RowMapper<ApiKey> apiKeyRowMapper;

    public ApiKeysService(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ApiKey getApiKey() {
        String newApiKey = UUID.randomUUID().toString();
        Date timestamp = new Date();
        jdbcTemplate.update("INSERT INTO api_keys VALUES (?, ?)", newApiKey, timestamp);
        ApiKey apiKey = new ApiKey();
        apiKey.setApiKey(newApiKey);
        apiKey.setKeyTimestamp(timestamp);
        return apiKey;
    }

    public boolean validateApiKey(String key) {
        ApiKey apiKey = jdbcTemplate.queryForObject("SELECT * FROM api_keys WHERE key = ?", getApiKeyRowMapper(), key);
        return apiKey != null;
    }

    public RowMapper<ApiKey> getApiKeyRowMapper() {
        if (apiKeyRowMapper == null) {
            apiKeyRowMapper = getApiKeyRowMapperSync();
        }
        return apiKeyRowMapper;
    }

    private synchronized RowMapper<ApiKey> getApiKeyRowMapperSync() {
        return new RowMapper<ApiKey>() {
            @Override
            public ApiKey mapRow(ResultSet resultSet, int i) throws SQLException {
                ApiKey apiKey = new ApiKey();
                apiKey.setKeyTimestamp(resultSet.getDate("key_timestamp"));
                apiKey.setApiKey(resultSet.getString("key"));
                return apiKey;
            }
        };
    }
}
