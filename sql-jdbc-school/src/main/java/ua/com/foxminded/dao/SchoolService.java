package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class SchoolService {

    public List<Group> findGroupsWhithLessOrEqualsStudentCount(GroupDao groupDao, int count) throws SQLException, IOException {
	if(count < 10 || count > 30) {
	    throw new IOException("Enter correct value: from 10 to 30.");
	}
	return groupDao.findGroupsWhithLessOrEqualsStudentCount(count);	
    }
    
    public List<Student> findStudentsRelatedToCourseWithGivenName(CourseDao courseDao, StudentDao studentDao, String courseName) throws SQLException, IOException {
	if(courseDao.findByName(courseName) == null) {
	    throw new IOException("There are no courses with a given name.");
	}
	return studentDao.findStudentsRelatedToCourseWithGivenName(courseName);	
    }
    
    public boolean addNewStudent(StudentDao studentDao, String firstName, String lastName) throws SQLException, IOException {
	List<Student> students = Arrays.asList(new Student(firstName, lastName));
	studentDao.addAll(students);	
	return true;
    }
    
    public boolean deleteStudentById(StudentDao studentDao, int studentId) throws SQLException, IOException {
	if(studentDao.findById(studentId) == null) {
	    throw new IOException("There are no student with a given student id.");
	}
	studentDao.deleteById(studentId);	
	return true;
    }
    
    public boolean addStudentToCourse(CourseDao courseDao, StudentDao studentDao, int studentId, int courseId) throws SQLException, IOException {
	if(courseDao.findById(courseId) == null) {
	    throw new IOException("There are no courses with a given course id.");
	}
	if(studentDao.findById(studentId) == null) {
	    throw new IOException("There are no student with a given student id.");
	}
	studentDao.addStudentToCourse(studentId, courseId);
	return true;
    }
    
    public boolean removeStudentFromCourse(CourseDao courseDao, StudentDao studentDao, int studentId, int courseId) throws SQLException, IOException {
	if(courseDao.findById(courseId) == null) {
	    throw new IOException("There are no courses with a given course id.");
	}
	if(studentDao.findById(studentId) == null) {
	    throw new IOException("There are no student with a given student id.");
	}
	studentDao.removeStudentFromCourse(studentId, courseId);
	return true;
    }   
    
}
