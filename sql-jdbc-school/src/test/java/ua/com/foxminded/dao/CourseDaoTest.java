package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Course;

class CourseDaoTest {
    
    CourseDao courseDao = new CourseDao();
    TestTools testTools = new TestTools();
    
    @BeforeEach
    void init() throws IOException, SQLException {
	testTools.createTables();
	courseDao.addAll(testTools.generateCourses(5));
    }

    @Test
    void selectAll_ShouldReturnListCourses() throws SQLException, IOException {	
	assertIterableEquals(testTools.generateCourses(5), courseDao.selectAll());	
    }

    @Test
    void findById_ShouldReturnCourseById() throws SQLException, IOException {
	Course expected = new Course("Mathematics");
	expected.setId(1);
	assertEquals(expected, courseDao.findById(1));	
    }
    
    @Test
    void deleteById_ShouldDeleteCourseById() throws SQLException, IOException {
	int countBeforeDelete = courseDao.selectAll().size(); 
	assertNotNull(courseDao.findById(1));
	courseDao.deleteById(1);
	assertNull(courseDao.findById(1));
	int countAfterDelete = courseDao.selectAll().size(); 
	assertEquals(countBeforeDelete - 1, countAfterDelete);
    }
    
    @Test
    void findByName_ShouldReturnCourseByName() throws SQLException, IOException {
	Course expected = new Course("Biology");
	expected.setId(2);
	assertEquals(expected, courseDao.findByName("Biology"));	
    }

}
