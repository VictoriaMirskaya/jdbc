package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.utils.TestTools;

class StudentDaoTest {

    GroupDao groupDao = new GroupDao();
    CourseDao courseDao = new CourseDao();
    StudentDao studentDao = new StudentDao();
    TestTools testTools = new TestTools();
    
    @BeforeEach
    void init() throws IOException, SQLException {
	testTools.createTables();	
    }

    @Test
    void selectAll_ShouldReturnListStudents_WhenStudentsExist() throws SQLException, IOException {
	studentDao.addAll(testTools.generateStudents(5));
	assertIterableEquals(testTools.generateStudents(5), studentDao.selectAll());	
    }
    
    @Test
    void selectAll_ShouldReturnEmptyList_WhenStudentsNotExist() throws SQLException, IOException {
	assertIterableEquals(new ArrayList<Student>(), studentDao.selectAll());	
    }

    @Test
    void findById_ShouldReturnStudentById_WhenStudentExist() throws SQLException, IOException {
	studentDao.addAll(testTools.generateStudents(5));
	Student expected = new Student("Adam", "Anderson");
	expected.setId(1);
	assertEquals(expected, studentDao.findById(1));	
    }
    
    @Test
    void findById_ShouldReturnNull_WhenStudentNotExist() throws SQLException, IOException {
	assertNull(studentDao.findById(100));	
    }
    
    @Test
    void deleteById_ShouldDeleteStudentById_WhenStudentExist() throws SQLException, IOException {
	studentDao.addAll(testTools.generateStudents(5));
	int countBeforeDelete = studentDao.selectAll().size(); 
	assertNotNull(studentDao.findById(1));
	studentDao.deleteById(1);
	assertNull(studentDao.findById(1));
	int countAfterDelete = studentDao.selectAll().size(); 
	assertEquals(countBeforeDelete - 1, countAfterDelete);
    }
    
    @Test
    void assignStudentsToGroup_ShouldAssignStudentToGroup() throws SQLException, IOException {
	List<Group> groups = testTools.generateGroups(1);
	List<Student> students = testTools.generateStudents(2);
	for (Student student : students) {
	    student.setGroup(groups.get(0));
	}
	groupDao.addAll(groups);
	studentDao.addAll(students);
	for (Student student : studentDao.selectAll()) {
	    assertFalse(studentDao.IsStudentOnGroup(student.getId(), 1));
	}
	studentDao.assignStudentsToGroup(students);
	for (Student student : studentDao.selectAll()) {
	    assertTrue(studentDao.IsStudentOnGroup(student.getId(), 1));
	}		
    }

    @Test
      void assignStudentsToCourses_ShouldAssignStidentToCourse() throws SQLException, IOException {
	List<Course> courses = testTools.generateCourses(2);
	List<Student> students = testTools.generateStudents(2);
	for (Student student : students) {
	    student.setCourses(courses);
	}
	courseDao.addAll(courses);
	studentDao.addAll(students);
	for (Student student : studentDao.selectAll()) {
	    for (Course course : courses) {
		assertFalse(studentDao.IsStudentOnCourse(student.getId(), course.getId()));
	    }
	}
	studentDao.assignStudentsToCourses(students);
	for (Student student : studentDao.selectAll()) {
	    for (Course course : courses) {
		assertTrue(studentDao.IsStudentOnCourse(student.getId(), course.getId()));
	    }
	}
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturnListStudents_WhenStudentsAreOnCourse() throws SQLException, IOException {
	List<Course> courses = testTools.generateCourses(3);
	List<Student> students = testTools.generateStudents(2);
	for (Student student : students) {
	    student.setCourses(courses);
	}
	courseDao.addAll(courses);
	studentDao.addAll(students);
	studentDao.assignStudentsToCourses(students);
	assertIterableEquals(students, studentDao.findStudentsRelatedToCourseWithGivenName("Mathematics"));
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturnEmptyList_WhenStudentsNotOnCourse() throws SQLException, IOException {
	assertIterableEquals(new ArrayList<Student>(), studentDao.findStudentsRelatedToCourseWithGivenName("Test"));
    }

    @Test
    void addStudentToCourse_ShouldAddStudentToCourse() throws SQLException, IOException {
	List<Course> courses = testTools.generateCourses(1);
	List<Student> students = testTools.generateStudents(1);
	students.get(0).setCourses(courses);
	courseDao.addAll(courses);
	studentDao.addAll(students);
	for (Student student : studentDao.selectAll()) {
	    assertFalse(studentDao.IsStudentOnCourse(student.getId(), 1));
	}
	studentDao.addStudentToCourse(1, 1);
	for (Student student : studentDao.selectAll()) {
	    assertTrue(studentDao.IsStudentOnCourse(student.getId(), 1));
	}
    }

    @Test
    void removeStudentFromCourse_ShouldRemoveStudentFromCourse() throws SQLException, IOException {
	List<Course> courses = testTools.generateCourses(1);
	List<Student> students = testTools.generateStudents(1);
	students.get(0).setCourses(courses);
	courseDao.addAll(courses);
	studentDao.addAll(students);
	studentDao.addStudentToCourse(1, 1);
	for (Student student : studentDao.selectAll()) {
	    assertTrue(studentDao.IsStudentOnCourse(student.getId(), 1));
	}
	studentDao.removeStudentFromCourse(1, 1);
	for (Student student : studentDao.selectAll()) {
	    assertFalse(studentDao.IsStudentOnCourse(student.getId(), 1));
	}
    }

    @Test
    void findByFirstNameLastName_ShouldReturnStudent_WhenStudentExist() throws IOException, SQLException {
	studentDao.addAll(testTools.generateStudents(5));
	Student expected = new Student("Adam", "Anderson");
	expected.setId(1);
	assertEquals(expected, studentDao.findByFirstNameLastName("Adam", "Anderson"));	
    }
    
    @Test
    void findByFirstNameLastName_ShouldReturnNull_WhenStudentNotExist() throws IOException, SQLException {
	assertNull(studentDao.findByFirstNameLastName("Adam", "Anderson"));	
    }

    @Test
    void IsStudentOnCourse_ShouldReturnTrue_WhenStudentIsOnCourse() throws SQLException, IOException {
	List<Course> courses = testTools.generateCourses(1);
	List<Student> students = testTools.generateStudents(1);
	students.get(0).setCourses(courses);
	courseDao.addAll(courses);
	studentDao.addAll(students);
	studentDao.addStudentToCourse(1, 1);
	for (Student student : studentDao.selectAll()) {
	    assertTrue(studentDao.IsStudentOnCourse(student.getId(), 1));
	}
    }
    
    @Test
    void IsStudentOnCourse_ShouldReturnFalse_WhenStudentIsNotOnCourse() throws SQLException, IOException {
	List<Course> courses = testTools.generateCourses(1);
	List<Student> students = testTools.generateStudents(3);
	students.get(0).setCourses(courses);
	courseDao.addAll(courses);
	studentDao.addAll(students);
	for (Student student : studentDao.selectAll()) {
	    assertFalse(studentDao.IsStudentOnCourse(student.getId(), 1));
	}
    }

    @Test
    void IsStudentOnGroup_ShouldReturnTrue_WhenStudentIsOnGroup() throws SQLException, IOException {
	List<Group> groups = testTools.generateGroups(1);
	List<Student> students = testTools.generateStudents(2);
	for (Student student : students) {
	    student.setGroup(groups.get(0));
	}
	groupDao.addAll(groups);
	studentDao.addAll(students);
	studentDao.assignStudentsToGroup(students);
	for (Student student : studentDao.selectAll()) {
	    assertTrue(studentDao.IsStudentOnGroup(student.getId(), 1));
	}
    }
    
    @Test
    void IsStudentOnGroup_ShouldReturnFalse_WhenStudentIsNotOnGroup() throws SQLException, IOException {
	List<Group> groups = testTools.generateGroups(1);
	List<Student> students = testTools.generateStudents(2);
	groupDao.addAll(groups);
	studentDao.addAll(students);
	for (Student student : studentDao.selectAll()) {
	    assertFalse(studentDao.IsStudentOnGroup(student.getId(), 1));
	}
    }

}
