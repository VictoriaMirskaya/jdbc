package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

    @Override
    public List<Course> selectAll() {
	List<Course> courses = new ArrayList<>();
	final String sql = "SELECT courses.course_id, courses.course_name, courses.course_description FROM courses";
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
	    e.printStackTrace();
	}
	return courses;
    }
    
    @Override
    public void addAll(List<Course> courses) {
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
	    e.printStackTrace();
	}
    }

    @Override
    public void deleteById(int id) {
	final String sql = "DELETE FROM courses WHERE course_id = ?";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    statement.execute();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

}
