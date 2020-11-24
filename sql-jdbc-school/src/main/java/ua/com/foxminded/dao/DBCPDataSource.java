package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {

private static BasicDataSource ds = new BasicDataSource();
    
    static {
        ds.setUrl("jdbc:postgresql://localhost:5432/school");
        ds.setUsername("postgres");
        ds.setPassword("310589");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    private DBCPDataSource(){ }
    
}
