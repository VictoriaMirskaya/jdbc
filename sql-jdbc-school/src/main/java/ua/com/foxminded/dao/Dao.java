package ua.com.foxminded.dao;

import java.util.List;

public interface Dao<T> {  

    List<T> selectAll();
    
    void addAll(List<T> t);
    
    void deleteById(int id);

}
