package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.UserMessages;
import ua.com.foxminded.domain.Course;

class CourseDaoTest {

    private static Dao<Course> courseDao = new CourseDao();
    private static List<Course> courses;

    @BeforeAll
    public static void init() {
	List<CourseNames> coursesNames = Arrays.asList(CourseNames.values());
	courses = new ArrayList<>();
	Course course;
	for (int i = 1; i <= 10; i++) {
	    course = new Course(coursesNames.get(i - 1).getTitle());
	    course.setId(i);
	    courses.add(course);
	}	
    }
    
    @BeforeEach
    public void createTables() throws IOException, SQLException {
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement()) {
	    statement.execute("DROP TABLE IF EXISTS courses");
	    statement.execute("CREATE TABLE courses("
	    		    + "course_id INT GENERATED ALWAYS AS IDENTITY, "
	    		    + "course_name VARCHAR(255) NOT NULL, "
	    		    + "course_description VARCHAR(255), "
	    		    + "PRIMARY KEY(course_id))");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
   
    @Test
    void addAllShouldAddListCourses() throws SQLException, IOException {	
	courseDao.addAll(courses);
	List<Course> actual = new ArrayList<>();
	final String sql = "SELECT * FROM courses";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Course course;
	    while (rs.next()) {
		course = new Course(rs.getString("course_name"));
		course.setId(rs.getInt("course_id"));
		course.setDescription(rs.getString("course_description"));
		actual.add(course);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	assertIterableEquals(courses, actual);
    }
    
    @Test
    void selectAllShouldReturnListCourses() throws SQLException, IOException {
	final String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Course course : courses) {
		statement.setString(1, course.getName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
	List<Course> actual = courseDao.selectAll();
	assertIterableEquals(courses, actual);
    }
    
    @Test
    void deleteByIdShouldDeleteCourseById() throws SQLException, IOException {
	String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Course course : courses) {
		statement.setString(1, course.getName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
	courseDao.deleteById(1);
	sql = "SELECT * FROM courses";
	List<Course> actual = new ArrayList<>();
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Course course;
	    while (rs.next()) {
		course = new Course(rs.getString("course_name"));
		course.setId(rs.getInt("course_id"));
		course.setDescription(rs.getString("course_description"));
		actual.add(course);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	assertEquals(courses.size() - 1, actual.size());
    }

    private enum CourseNames {

	MATHEMATICS("Mathematics"), BIOLOGY("Biology"), ENGLISH("English"), IT("Information Technology"),
	DATABASE("Database"), HISTORY("History"), ECONOMY("Economy"), ART("Art"), DESIGN("Design"),
	ARCHITECTURE("Architecture");

	private String title;

	CourseNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}

    }

}
