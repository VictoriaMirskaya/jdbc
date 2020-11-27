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
    public List<Student> selectAll() {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public void addAll(List<Student> students) {	
	final String sql = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?);"
	                 + "INSERT INTO students_courses (course_id, student_id) VALUES (?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
		for (Student student : students) {
		    if (student.getId() == 0) {
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setInt(3, student.getGroup().getId());
			statement.addBatch();
		    }
		}
		List<Course> cources;
		for (Student student : students) {
		    cources = student.getCources();
		    if (cources != null) {
			for (Course course : cources) {
			    statement.setInt(4, course.getId());
			    statement.setInt(5, student.getId());
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
    public void deleteById(int id) {
	final String sql = "DELETE FROM students WHERE student_id = ?;"
		         + "DELETE FROM students_courses WHERE student_id = ?";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {		
		statement.setInt(1, id);		
		statement.setInt(2, id);
		statement.execute();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
    
    public List<Student> findStudentsRelatedToCourseWithGivenName(String courseName) {
	List<Student> students = new ArrayList<>();
	final String sql = "" + courseName;
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

    public void addStudentToCourse(int studentId, int courseId) {

    }

    public void removeStudentFromCourse(int studentId, int courseId) {

    }
    
}
