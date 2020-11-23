package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ua.com.foxminded.AuthorizationData;
import ua.com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

    @Override
    public void saveElement(Course t) {
	String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, t.getName());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void saveList(List<Course> t) {
	String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Course course : t) {
		statement.setString(1, course.getName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void delete(int id) {
	String sql = "DELETE FROM courses WHERE course_id = " + id;
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

}
