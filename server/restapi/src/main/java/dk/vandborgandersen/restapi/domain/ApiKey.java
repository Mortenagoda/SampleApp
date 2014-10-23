package dk.vandborgandersen.restapi.domain;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class ApiKey {
    private static final Logger LOGGER = Logger.getLogger(ApiKey.class.getName());

    private String apiKey;
    private Date keyTimestamp;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Date getKeyTimestamp() {
        return keyTimestamp;
    }

    public void setKeyTimestamp(Date keyTimestamp) {
        this.keyTimestamp = keyTimestamp;
    }
}
