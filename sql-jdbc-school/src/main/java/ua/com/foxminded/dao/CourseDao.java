package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ua.com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

    @Override
    public Course findElement(String condition, int parameterValue) {
	return null;	
    }

    @Override
    public Course findElement(String condition, String parameterValue) {
	return null;	
    }
       
    @Override
    public List<Course> findList(String condition, int parameterValue) {
	return null;	
    }
    
    @Override
    public List<Course> findList(String condition, String parameterValue) {
	return null;	
    }
    
    @Override
    public void addElement(Course course) {
	String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, course.getName());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addList(List<Course> courses) {
	String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Course course : courses) {
		statement.setString(1, course.getName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void deleteElement(Course course) {
	String sql = "DELETE FROM courses WHERE course_id = " + course.getId();
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void deleteList(List<Course> t) {
	// TODO Auto-generated method stub
	
    }

}
