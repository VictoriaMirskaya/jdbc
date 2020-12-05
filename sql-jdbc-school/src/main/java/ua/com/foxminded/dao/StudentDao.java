package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.com.foxminded.UserMessages;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Student;

public class StudentDao implements Dao<Student> {

    @Override
    public List<Student> selectAll() throws SQLException, IOException {
	List<Student> students = new ArrayList<>();
	final String sql = "SELECT s.student_id, s.first_name, s.last_name FROM students s";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Student student;
	    while (rs.next()) {
		student = new Student(rs.getString("first_name"), rs.getString("last_name"));
		student.setId(rs.getInt("student_id"));
		students.add(student);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return students;
    }
    
    @Override
    public void addAll(List<Student> students) throws SQLException, IOException {	
	final String sql = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Student student : students) {
		statement.setString(1, student.getFirstName());
		statement.setString(2, student.getLastName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
    }
    
    @Override
    public Student findById(int studentId) throws SQLException, IOException {
	Student student = null;
	final String sql = "SELECT s.student_id, s.first_name, s.last_name FROM students s "
			 + "WHERE s.student_id = " + studentId;
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    while (rs.next()) {
		student = new Student(rs.getString("first_name"), rs.getString("last_name"));
		student.setId(rs.getInt("student_id"));
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return student;
    }

    @Override
    public void deleteById(int id) throws SQLException, IOException {
	final String sql = "DELETE FROM students WHERE student_id = ?;"
		         + "DELETE FROM students_courses WHERE student_id = ?";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    statement.setInt(2, id);
	    statement.execute();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_DELETING_DATA_FROM_DATABASE);
	}
    }
    
    public void assignStudentsToGroup(List<Student> students) throws SQLException, IOException {
	final String sql = "UPDATE students SET group_id = ? WHERE student_id = ?";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Student student : students) {
		if (student.getGroup() != null) {
		    statement.setInt(1, student.getGroup().getId());
		    statement.setInt(2, student.getId());
		}
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
    }
    
    public void assignStudentsToCourses(List<Student> students) throws SQLException, IOException {
	final String sql = "INSERT INTO students_courses (course_id, student_id) VALUES (?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
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
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
    }
    
    public List<Student> findStudentsRelatedToCourseWithGivenName(String courseName) throws SQLException, IOException {
	List<Student> students = new ArrayList<>();
	final String sql = "SELECT students.student_id, students.first_name, students.last_name, courses.course_name FROM courses"
		         + " JOIN students_courses ON courses.course_id = students_courses.course_id" 
		         + " JOIN students ON students_courses.student_id = students.student_id" 
		         + " WHERE courses.course_name = '" + courseName + "'"
		         + " ORDER BY students.student_id";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Student student;
	    while (rs.next()) {
		student = new Student(rs.getString("first_name"), rs.getString("last_name"));
		student.setId(rs.getInt("student_id"));
		students.add(student);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return students;
    }

    public void addStudentToCourse(int studentId, int courseId) throws SQLException, IOException {
	final String sql = "INSERT INTO students_courses (course_id, student_id) VALUES (" + courseId + "," + studentId + ")";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.execute();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
    }
    
    public void removeStudentFromCourse(int studentId, int courseId) throws SQLException, IOException {
	final String sql = "DELETE FROM students_courses WHERE course_id = " + courseId + " AND student_id = " + studentId;
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.execute();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_DELETING_DATA_FROM_DATABASE);
	}
    }
        
    public Student findByFirstNameLastName(String firstName, String lastName) throws IOException, SQLException {
	Student student = null;
	final String sql = "SELECT s.student_id, s.first_name, s.last_name FROM students s "
			 + "WHERE s.first_name = '" + firstName + "' AND s.last_name = '" + lastName + "'";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    while (rs.next()) {
		student = new Student(rs.getString("first_name"), rs.getString("last_name"));
		student.setId(rs.getInt("student_id"));
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return student;	
    }
    
    public boolean IsStudentOnCourse(int studentId, int courseId) throws SQLException, IOException {
   	final String sql = "SELECT * FROM students_courses sc "
   			 + "WHERE sc.student_id = " + studentId + " AND sc.course_id = " + courseId;
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    while (rs.next()) {
		return true;
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return false;
    }
    
    public boolean IsStudentOnGroup(int studentId, int groupId) throws SQLException, IOException {
   	final String sql = "SELECT s.group_id FROM students s "
   			 + "WHERE s.student_id =" + studentId + " AND s.group_id =" + groupId;
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    while (rs.next()) {
		return true;
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return false;
    }
    
}
