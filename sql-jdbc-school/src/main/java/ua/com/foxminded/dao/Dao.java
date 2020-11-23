package ua.com.foxminded.dao;

import java.util.List;

public interface Dao<T> {

    void saveElement(T t);
    
    void saveList(List<T> t);

    void delete(int id);

}
