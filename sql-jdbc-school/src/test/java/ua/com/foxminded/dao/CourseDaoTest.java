package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Course;

class CourseDaoTest extends AbstractDaoTest{
    
    @Test
    void addAllShouldAddListCourses() throws SQLException, IOException {
	Dao<Course> courseDao = new CourseDao();
	List<Course> courses = new TestDataGenerator().generateCourses();
	courseDao.addAll(courses);
	assertEquals(courses.size(), courseDao.selectAll().size());
    }
    
    @Test
    void selectAllShouldReturnListCourses() throws SQLException, IOException {
	Dao<Course> courseDao = new CourseDao();
	List<Course> courses = new TestDataGenerator().generateCourses();
	courseDao.addAll(courses);
	assertEquals(courses.size(), courseDao.selectAll().size());
    }
    
    @Test
    void deleteByIdShouldDeleteCourseById() throws SQLException, IOException {
	Dao<Course> courseDao = new CourseDao();
	List<Course> courses = new TestDataGenerator().generateCourses();
	courseDao.addAll(courses);
	courseDao.deleteById(1);
	assertEquals(courses.size() - 1, courseDao.selectAll().size());
    }

}
