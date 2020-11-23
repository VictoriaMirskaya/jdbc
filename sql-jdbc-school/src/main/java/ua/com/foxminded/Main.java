package ua.com.foxminded;

import java.io.IOException;
import java.sql.SQLException;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.Dao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.SQLTablesCreator;
import ua.com.foxminded.dao.StudentCourseDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.dao.TestDataGenerator;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.domain.StudentCourse;
import ua.com.foxminded.ui.ConsoleMenu;

public class Main {

    private static Dao<Course> courseDao;
    private static Dao<Group> groupDao;
    private static Dao<Student> studentDao;
    private static Dao<StudentCourse> studentCourseDao;

    public static void main(String[] args) {
	courseDao = new CourseDao();
	groupDao = new GroupDao();
	studentDao = new StudentDao();
	studentCourseDao = new StudentCourseDao();
	try {
	    new SQLTablesCreator().createTables();
	    new TestDataGenerator().generateTestData(courseDao, groupDao, studentDao, studentCourseDao);
	    new ConsoleMenu().show(studentDao, studentCourseDao);
	} catch (IOException | SQLException e) {
	    System.err.println(e.getMessage());
	} catch (RuntimeException e) {
	    System.err.println(UserMessages.SYSTEM_ERROR);
	    e.printStackTrace();
	}
    }

}
