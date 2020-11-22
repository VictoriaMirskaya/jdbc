package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ua.com.foxminded.AuthorizationData;
import ua.com.foxminded.domain.Student;

public class StudentDao implements Dao<Student> {

    @Override
    public Optional<Student> get(long id) {

	return null;
    }

    @Override
    public List<Student> getAll() {

	return null;
    }

    @Override
    public void saveElement(Student s) {
	String sql = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, s.getFirstName());
	    statement.setString(2, s.getLastName());
	    statement.setInt(3, s.getGroup().getId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    @Override
    public void saveList(List<Student> s) {
	String sql = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Student student : s) {
		statement.setString(1, student.getFirstName());
		statement.setString(2, student.getLastName());
		statement.setInt(3, student.getGroup().getId());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void update(Student s, String[] params) {

	
    }

    @Override
    public void delete(Student s) {

	
    }

}
