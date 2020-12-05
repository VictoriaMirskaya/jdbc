package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {  

    List<T> selectAll() throws SQLException, IOException;
    
    void addAll(List<T> t) throws SQLException, IOException;
    
    T findById(int id) throws SQLException, IOException;
    
    void deleteById(int id) throws SQLException, IOException;

}
