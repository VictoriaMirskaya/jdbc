package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

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
    void selectAll_ShouldReturnListStudents() throws SQLException, IOException {
	studentDao.addAll(testTools.generateStudents(5));
	assertIterableEquals(testTools.generateStudents(5), studentDao.selectAll());	
    }

    @Test
    void findById_ShouldReturnStudentById() throws SQLException, IOException {
	studentDao.addAll(testTools.generateStudents(5));
	Student expected = new Student("Adam", "Anderson");
	expected.setId(1);
	assertEquals(expected, studentDao.findById(1));	
    }
    
    @Test
    void deleteById_ShouldDeleteStudentById() throws SQLException, IOException {
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
	    student.setCources(courses);
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
    void findStudentsRelatedToCourseWithGivenName_ShouldReturnListStudents() throws SQLException, IOException {

    }

    @Test
    void addStudentToCourse_ShouldAddStudentToCourse() throws SQLException, IOException {

    }

    @Test
    void removeStudentFromCourse_ShouldRemoveStudentFromCourse() throws SQLException, IOException {

    }

    @Test
    void findByFirstNameLastName_ShouldReturnStudent() throws IOException, SQLException {

    }

    @Test
    void IsStudentOnCourse_ShouldReturnTrue() throws SQLException, IOException {

    }

    @Test 
       void IsStudentOnGroup_ShouldReturnTrue() throws SQLException, IOException {
	
    }

}
