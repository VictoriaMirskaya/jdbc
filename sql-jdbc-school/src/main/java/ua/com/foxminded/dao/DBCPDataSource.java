package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.io.Resources;
import ua.com.foxminded.UserMessages;

public class DBCPDataSource {

    private static BasicDataSource dataSourse = new BasicDataSource();
    private static final String FILE_CONFIG_NAME = "properties.txt";

    public static Connection getConnection() throws SQLException, IOException {
	Properties properties = new Properties();
	try {
	    properties.load(Resources.getResourceAsReader(FILE_CONFIG_NAME));
	    dataSourse.setUrl(properties.getProperty("db.url"));
	    if (properties.getProperty("db.username") != null) {
		dataSourse.setUsername(properties.getProperty("db.username"));
	    }
	    if (properties.getProperty("db.password") != null) {
		dataSourse.setPassword(properties.getProperty("db.password"));
	    }
	} catch (IOException e) {
	    throw new IOException(String.format(UserMessages.ERROR_FILE_READING_MASK, FILE_CONFIG_NAME));
	}	
	dataSourse.setMinIdle(5);
	dataSourse.setMaxIdle(10);
	dataSourse.setMaxOpenPreparedStatements(100);
	return dataSourse.getConnection();
    }

    private DBCPDataSource() {
    }

}
