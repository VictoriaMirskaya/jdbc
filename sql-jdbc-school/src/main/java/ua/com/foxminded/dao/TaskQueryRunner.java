package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TaskQueryRunner {
    
    public static List<Group> findGroups(int count) {
	List<Group> groups = new ArrayList<>();
	String sql = "SELECT students.group_id, groups.group_name FROM students "
		   + "JOIN groups ON students.group_id = groups.group_id "
		   + "GROUP BY (students.group_id, groups.group_name) "
		   + "HAVING count(students.student_id) <=" + count;
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()){
        	groups.add(new Group(rs.getInt("group_id"), rs.getString("group_name")));
            }
	} catch (SQLException e) {
	    e.printStackTrace();
	}	
	return groups;	
    }

    public static List<Student> findStudents(String courseName) {
	List<Student> students = new ArrayList<>();
//	String sql = "" + courseName;
//	try (Connection connection = DBCPDataSource.getConnection();
//		Statement statement = connection.createStatement();
//		ResultSet rs = statement.executeQuery(sql)) {
//            while(rs.next()){
//        	students.add(new Student(rs.getInt("student_id"), rs.getString("first_name"), rs.getString("last_name")));
//            }
//	} catch (SQLException e) {
//	    e.printStackTrace();
//	}	
	return students;	
    }

    public static void addNewStudent(Dao<Student> studentDao, String firstName, String lastName) {
	studentDao.saveElement(new Student(firstName, lastName));
    }

    public static void deleteStudent(Dao<Student> studentDao, int studentId) {
	studentDao.delete(studentId);
    }

    public static void addStudentToCourse(int studentId, int courseId) {
//	Add a student to the course (from a list)
		
    }

    public static void removeStudentFromCourse(int studentId, int courseId) {
//	Remove the student from one of his or her courses
		
    }

}
