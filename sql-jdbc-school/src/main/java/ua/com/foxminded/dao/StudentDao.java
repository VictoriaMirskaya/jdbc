package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Student;

public class StudentDao implements Dao<Student> {

    @Override
    public void saveElement(Student s) {
	String sql = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, s.getFirstName());
	    statement.setString(2, s.getLastName());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    @Override
    public void saveList(List<Student> s) {
	String sql = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
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
	sql = "INSERT INTO students_courses (course_id, student_id) VALUES (?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    List<Course> cources;
	    for (Student student : s) {
		cources = student.getCources();
		if(cources!=null) {		    
		    for(Course course: cources) {		    
			statement.setInt(1, course.getId());
			statement.setInt(2, student.getId());
			statement.addBatch();
		    }
		}
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void delete(int id) {
	String sql = "DELETE FROM students WHERE student_id = " + id;
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}	
    }

}
