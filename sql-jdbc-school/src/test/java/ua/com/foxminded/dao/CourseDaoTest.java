package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.utils.TestTools;

class CourseDaoTest {
    
    CourseDao courseDao = new CourseDao();
    TestTools testTools = new TestTools();
    
    @BeforeEach
    void init() throws IOException, SQLException {
	testTools.createTables();
    }

    @Test
    void selectAll_ShouldReturnListCourses_WhenCoursesExist() throws SQLException, IOException {
	courseDao.addAll(testTools.generateCourses(5));
	assertIterableEquals(testTools.generateCourses(5), courseDao.selectAll());	
    }
    
    @Test
    void selectAll_ShouldReturnEmptyList_WhenCoursesNotExist() throws SQLException, IOException {
	assertIterableEquals(new ArrayList<Course>(), courseDao.selectAll());	
    }

    @Test
    void findById_ShouldReturnCourseById_WhenCourseExist() throws SQLException, IOException {
	courseDao.addAll(testTools.generateCourses(5));
	Course expected = new Course("Mathematics");
	expected.setId(1);
	assertEquals(expected, courseDao.findById(1));	
    }
    
    @Test
    void findById_ShouldReturnNull_WhenCourseNotExist() throws SQLException, IOException {
	assertNull(courseDao.findById(10));	
    }
    
    @Test
    void deleteById_ShouldDeleteCourseById() throws SQLException, IOException {
	courseDao.addAll(testTools.generateCourses(5));
	int countBeforeDelete = courseDao.selectAll().size(); 
	assertNotNull(courseDao.findById(1));
	courseDao.deleteById(1);
	assertNull(courseDao.findById(1));
	int countAfterDelete = courseDao.selectAll().size(); 
	assertEquals(countBeforeDelete - 1, countAfterDelete);
    }
    
    @Test
    void findByName_ShouldReturnCourseByName_WhenCourseExist() throws SQLException, IOException {
	courseDao.addAll(testTools.generateCourses(5));
	Course expected = new Course("Biology");
	expected.setId(2);
	assertEquals(expected, courseDao.findByName("Biology"));	
    }
    
    @Test
    void findByName_ShouldReturnNull_WhenCourseNotExist() throws SQLException, IOException {
	assertNull(courseDao.findByName("Biology"));	
    }

}
