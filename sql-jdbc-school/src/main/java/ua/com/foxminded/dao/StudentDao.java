package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Student;

public class StudentDao implements Dao<Student> {

    @Override
    public List<Student> find(String condition, Object parameterValue) {
	List<Student> students = new ArrayList<>();
	if (condition.equals("Find all students related to course with given name")) {
	    findStudentsRelatedToCourseWithGivenName(students, (String)parameterValue);
	}
	return students;
    }

    @Override
    public void add(List<Student> students) {	
	try (Connection connection = DBCPDataSource.getConnection()) {
	    String sql = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		for (Student student : students) {
		    if (student.getId() == 0) {
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setInt(3, student.getGroup().getId());
			statement.addBatch();
		    }
		}
		statement.executeBatch();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    sql = "INSERT INTO students_courses (course_id, student_id) VALUES (?, ?)";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		List<Course> cources;
		for (Student student : students) {
		    cources = student.getCources();
		    if (cources != null) {
			for (Course course : cources) {
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
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void delete(List<Student> students) {
	try (Connection connection = DBCPDataSource.getConnection()) {
	    String sql = "DELETE FROM students WHERE student_id = ?";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		for (Student student : students) {
		    statement.setInt(1, student.getId());
		    statement.addBatch();
		}
		statement.executeBatch();
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	    sql = "DELETE FROM students_courses WHERE course_id = ? AND student_id = ?";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		List<Course> cources;
		for (Student student : students) {
		    cources = student.getCources();
		    if (cources != null) {
			for (Course course : cources) {
			    statement.setInt(1, course.getId());
			    statement.setInt(2, student.getId());
			    statement.addBatch();
			}
			statement.executeBatch();
		    }
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    private List<Student> findStudentsRelatedToCourseWithGivenName(List<Student> students, String courseName) {
   	String sql = "" + courseName;
   	try (Connection connection = DBCPDataSource.getConnection();
   		Statement statement = connection.createStatement();
   		ResultSet rs = statement.executeQuery(sql)) {
   	    while (rs.next()) {
   		students.add(
   			new Student(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name")));
   	    }
   	} catch (SQLException e) {
   	    e.printStackTrace();
   	}
   	return students;
       }

}
