package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {

private static BasicDataSource dataSourse = new BasicDataSource();
    
    static {
	dataSourse.setUrl("jdbc:postgresql://localhost:5432/school");
	dataSourse.setUsername("postgres");
	dataSourse.setPassword("310589");
	dataSourse.setMinIdle(5);
	dataSourse.setMaxIdle(10);
	dataSourse.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSourse.getConnection();
    }
    
    private DBCPDataSource(){ }
    
}
