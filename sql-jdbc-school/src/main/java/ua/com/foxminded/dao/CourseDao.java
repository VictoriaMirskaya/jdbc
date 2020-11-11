package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ua.com.foxminded.AuthorizationData;
import ua.com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

    @Override
    public Optional<Course> get(long id) {

	return null;
    }

    @Override
    public List<Course> getAll() {

	return null;
    }

    @Override
    public void save(Course t) {
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
    public void update(Course t, String[] params) {

    }

    @Override
    public void delete(Course t) {

    }

}
