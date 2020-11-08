package ua.com.foxminded.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import ua.com.foxminded.UserMessages;

public class SQLTablesCreator implements TablesCreator {

    private static final String URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "310589";
    private static final String FILE_SCRIPT_NAME = "CreateTablesScript.sql";
    
    @Override
    public void createTables() throws IOException, SQLException {
	try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		BufferedReader sqlFile = Files.newBufferedReader(producePath(FILE_SCRIPT_NAME));
		Statement statement = connection.createStatement()) {
	    String line = null;
	    while ((line = sqlFile.readLine()) != null) {
		if (line.endsWith(";")) {
		    line = line.substring(0, line.length() - 1);
		}
		statement.executeUpdate(line);
	    }
	} catch (IOException e) {
	    throw new IOException(String.format(UserMessages.ERROR_FILE_READING_MASK, FILE_SCRIPT_NAME));
	} catch (SQLException e) {
	    throw new SQLException(String.format(UserMessages.SQL_EXCEPTION_MASK, FILE_SCRIPT_NAME));
	}
    }
    
    private Path producePath(String fileName) {
  	Path path = null;
  	    try {
  		path = Paths
  		            .get(this.getClass()
  		            .getClassLoader()
  		            .getResource(fileName)
  		            .toURI());
  	    } catch (URISyntaxException e) {
  		System.err.println(UserMessages.ERROR_GET_PATH);
  	    }
  	 return path;   
      }

}
