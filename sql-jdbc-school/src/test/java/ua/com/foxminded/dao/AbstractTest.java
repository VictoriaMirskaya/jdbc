package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractTest {
    
    @BeforeEach
    public void createTables() throws IOException, SQLException {
	new SQLTablesCreator().createTables();
    }

}
