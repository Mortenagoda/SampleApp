package dk.vandborgandersen.restapi.environment;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * @author Morten Andersen (mortena@gmail.com)
 */
public class DBFactory {

    public static final String JDBC_MYSQL_LOCALHOST_API = "jdbc:postgresql://192.168.0.16/api";
    public static final String DRIVER_CLASS = "org.postgresql.Driver";

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = getDataSourceSync();
        }
        return dataSource;
    }

    private static DataSource getDataSourceSync() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS);
        dataSource.setUrl(JDBC_MYSQL_LOCALHOST_API);
        dataSource.setUsername("api");
        dataSource.setPassword("password");
        dataSource.setInitialSize(0);
        dataSource.setMaxTotal(10);
        dataSource.setTimeBetweenEvictionRunsMillis(1000);
        dataSource.setMaxConnLifetimeMillis(6000);
        return dataSource;
    }

}
