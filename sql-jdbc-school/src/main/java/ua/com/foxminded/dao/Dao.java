package ua.com.foxminded.dao;

import java.util.List;

public interface Dao<T> {

    void findElement(T t);
    
    void findList(List<T> t);
    
    void addElement(T t);
    
    void addList(List<T> t);

    void deleteElement(T t);
    
    void deleteList(List<T> t);

}
