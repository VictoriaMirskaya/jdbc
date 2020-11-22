package ua.com.foxminded.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void saveElement(T t);
    
    void saveList(List<T> t);

    void update(T t, String[] params);

    void delete(T t);

}
