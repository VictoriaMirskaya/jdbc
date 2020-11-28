package ua.com.foxminded.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {  

    List<T> selectAll() throws SQLException;
    
    void addAll(List<T> t) throws SQLException;
    
    void deleteById(int id) throws SQLException;

}
