package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ua.com.foxminded.AuthorizationData;
import ua.com.foxminded.domain.StudentCourse;

public class StudentCourseDao implements Dao<StudentCourse> {

    @Override
    public Optional<StudentCourse> get(long id) {
	return null;
    }

    @Override
    public List<StudentCourse> getAll() {
	return null;
    }

    @Override
    public void saveElement(StudentCourse sc) {
	String sql = "INSERT INTO students_courses (course_id, student_id) VALUES (?, ?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, sc.getCourseId());
	    statement.setInt(2, sc.getStudentId());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void saveList(List<StudentCourse> sc) {
	String sql = "INSERT INTO students_courses (course_id, student_id) VALUES (?, ?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (StudentCourse studentCourse : sc) {
		statement.setInt(1, studentCourse.getCourseId());
		statement.setInt(2, studentCourse.getStudentId());
		statement.addBatch();
	    }	    
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void update(StudentCourse t, String[] params) {
	
    }

    @Override
    public void delete(StudentCourse t) {
	
    }

}
