package ua.com.foxminded.dao;

import java.util.List;

public interface Dao<T> {  

    List<T> find(String condition, Object parameterValue);
  
    void add(List<T> t);
    
    void delete(List<T> t);

}
