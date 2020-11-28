package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.com.foxminded.UserMessages;
import ua.com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

    @Override
    public List<Course> selectAll() throws SQLException {
	List<Course> courses = new ArrayList<>();
	final String sql = "SELECT c.course_id, c.course_name, c.course_description FROM courses c";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Course course;
	    while (rs.next()) {
		course = new Course(rs.getString("course_name"));
		course.setId(rs.getInt("course_id"));
		course.setDescription(rs.getString("course_description"));
		courses.add(course);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return courses;
    }
    
    @Override
    public void addAll(List<Course> courses) throws SQLException {
	final String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Course course : courses) {
		if (course.getId() == 0) {
		    statement.setString(1, course.getName());
		    statement.addBatch();
		}
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
    }

    @Override
    public void deleteById(int id) throws SQLException {
	final String sql = "DELETE FROM courses WHERE course_id = ?";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    statement.execute();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_DELETING_DATA_FROM_DATABASE);
	}
    }

}
