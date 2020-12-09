package ua.com.foxminded.serviсe;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.service.SchoolService;
import ua.com.foxminded.utils.TestTools;

class SchoolServiceTest {

    CourseDao courseDao = new CourseDao();
    GroupDao groupDao = new GroupDao();
    StudentDao studentDao = new StudentDao();
    SchoolService schoolService = new SchoolService();
    TestTools testTools = new TestTools();
    
    @BeforeEach
    void init() throws SQLException, IOException {
	testTools.createTables();
	testTools.generateData(courseDao, groupDao, studentDao);	
    }

    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldThrowIOException_WhenInputIncorrectValue() {
	assertThrows(IllegalArgumentException.class, () -> schoolService.findGroupsWhithLessOrEqualsStudentCount(groupDao, 5));
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturn_EmptyList_WhenGroupsNotFound() throws SQLException, IOException {
	assertIterableEquals(new ArrayList<Group>(), schoolService.findGroupsWhithLessOrEqualsStudentCount(groupDao, 11));	
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturn_ListGoups_WhenGroupsFound() throws SQLException, IOException {
	assertIterableEquals(groupDao.selectAll(), schoolService.findGroupsWhithLessOrEqualsStudentCount(groupDao, 20));		
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldThrowIOException_WhenInputIncorrectValue() throws SQLException, IOException {	
	assertThrows(IllegalArgumentException.class, () -> schoolService.findStudentsRelatedToCourseWithGivenName(courseDao, studentDao, "TestName"));
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturnEmptyListStudents_WhenStudentsNotFound() throws SQLException, IOException {
	assertIterableEquals(new ArrayList<Student>(), schoolService.findStudentsRelatedToCourseWithGivenName(courseDao, studentDao, "Biology"));
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturn_ListStudents_WhenStudentsFound() throws SQLException, IOException {
	assertIterableEquals(studentDao.selectAll(), schoolService.findStudentsRelatedToCourseWithGivenName(courseDao, studentDao, "Mathematics"));
    }
    
    @Test
    void addNewStudent_ShouldAddedStudentToDB() throws SQLException, IOException {
	assertTrue(schoolService.addNewStudent(studentDao, "TestFirstName", "TestLastName"));
	assertNotNull(studentDao.findByFirstNameLastName("TestFirstName", "TestLastName"));
    }
    
    @Test
    void deleteStudentById_ShouldThrowIOException_WhenStudentNotFound() {
	assertThrows(IllegalArgumentException.class, () -> schoolService.deleteStudentById(studentDao, 1000));
    }
    
    @Test
    void deleteStudentById_ShouldDeleteStudentFromDB() throws SQLException, IOException {
	assertNotNull(studentDao.findById(1));	
	assertTrue(schoolService.deleteStudentById(studentDao, 1));
	assertNull(studentDao.findById(1));	
    }

    @Test
    void addStudentToCourse_ShouldThrowIOException_WhenInputIncorrectCourseId() throws SQLException, IOException {
	assertThrows(IllegalArgumentException.class, () -> schoolService.addStudentToCourse(courseDao, studentDao, 1, 100));
    }
    
    @Test
    void addStudentToCourse_ShouldThrowIOException_WhenInputIncorrectStudentId() throws SQLException, IOException {
	assertThrows(IllegalArgumentException.class, () -> schoolService.addStudentToCourse(courseDao, studentDao, 5000, 1));
    }
    
    @Test
    void addStudentToCourse_ShouldAddStudentToCourse() throws SQLException, IOException {
	assertTrue(schoolService.addStudentToCourse(courseDao, studentDao, 1, 2));
	assertTrue(studentDao.IsStudentOnCourse(1, 2));
    }
 
    @Test
    void removeStudentFromCourse_ShouldThrowIOException_WhenInputIncorrectCourseId() throws SQLException, IOException {
	assertThrows(IllegalArgumentException.class, () -> schoolService.removeStudentFromCourse(courseDao, studentDao, 1, 100));
    }
    
    @Test
    void removeStudentFromCourse_ShouldThrowIOException_WhenInputIncorrectStudentId() throws SQLException, IOException {
	assertThrows(IllegalArgumentException.class, () -> schoolService.removeStudentFromCourse(courseDao, studentDao, 5000, 1));
    } 
	
    @Test
    void removeStudentFromCourse_ShouldRemoveStudentFromCourse() throws SQLException, IOException {
	assertTrue(schoolService.removeStudentFromCourse(courseDao, studentDao, 1, 1));
	assertFalse(studentDao.IsStudentOnCourse(1, 1));
    }       

}
