package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.SQLException;

public interface TablesCreator {
    
    void createTables() throws IOException, SQLException;
    
}
