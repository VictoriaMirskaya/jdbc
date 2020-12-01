package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Course;

class CourseDaoTest {

    private static Dao<Course> courseDao = new CourseDao();

    @Test
    void selectAllShouldReturnListCourses() throws SQLException {
	List<CourseNames> coursesNames = Arrays.asList(CourseNames.values());
	List<Course> expected = new ArrayList<>();
	Course course;
	for (int i = 1; i <= 10; i++) {
	    course = new Course(coursesNames.get(i - 1).getTitle());
	    course.setId(i);
	    expected.add(course);
	}
	List<Course> actual = courseDao.selectAll();
	assertIterableEquals(expected, actual);
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
