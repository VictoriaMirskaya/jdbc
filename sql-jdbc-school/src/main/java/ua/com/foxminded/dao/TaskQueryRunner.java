package ua.com.foxminded.dao;

import java.util.List;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TaskQueryRunner {
    
    public static List<Group> findGroups(int count) {

	
	return null;	
    }

    public static List<Student> findStudents(String courseName) {
	
	
	return null;
    }

    public static void addNewStudent(Dao<Student> studentDao, String firstName, String lastName) {
	studentDao.saveElement(new Student(firstName, lastName));
    }

    public static boolean deleteStudent(int StudentId) {
	
	
	return true;
    }

    public static boolean addStudentToCourse(int studentId, int courseId) {
	
	
	return true;
    }

    public static boolean removeStudentFromCourse(int studentId, int courseId) {
	
	
	return true;
    }

}
