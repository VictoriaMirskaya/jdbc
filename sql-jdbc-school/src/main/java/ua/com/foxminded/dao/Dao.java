package ua.com.foxminded.dao;

import java.util.List;

public interface Dao<T> {  

    T findElement(String condition, int parameterValue);
    
    T findElement(String condition, String parameterValue);
    
    List<T> findList(String condition, int parameterValue);
    
    List<T> findList(String condition, String parameterValue);
    
    void addElement(T t);
    
    void addList(List<T> t);

    void deleteElement(T t);
    
    void deleteList(List<T> t);

}
