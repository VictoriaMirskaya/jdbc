package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import ua.com.foxminded.UserMessages;

public class SQLTablesCreator implements TablesCreator {

    private static final String FILE_SCRIPT_NAME = "CreateTablesScript.sql";
    
    @Override
    public void createTables() throws IOException, SQLException {
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement()) {
	    ScriptRunner scriptRunner = new ScriptRunner(connection);
	    scriptRunner.runScript(Resources.getResourceAsReader(FILE_SCRIPT_NAME));
	} catch (IOException e) {
	    throw new IOException(String.format(UserMessages.ERROR_FILE_READING_MASK, FILE_SCRIPT_NAME));
	} catch (SQLException e) {
	    throw new SQLException(String.format(UserMessages.ERROR_SCRIPT_EXECUTION_MASK, FILE_SCRIPT_NAME));
	}
    }

}
