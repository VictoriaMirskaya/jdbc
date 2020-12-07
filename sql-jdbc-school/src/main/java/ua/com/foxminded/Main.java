package ua.com.foxminded;

import java.io.IOException;
import java.sql.SQLException;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.SQLTablesCreator;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.dao.TestDataGenerator;
import ua.com.foxminded.servise.UserMessages;
import ua.com.foxminded.ui.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
	try {
	    CourseDao courseDao = new CourseDao();
	    GroupDao groupDao = new GroupDao();
	    StudentDao studentDao = new StudentDao();
	    new SQLTablesCreator().createTables();
	    new TestDataGenerator().generateTestData(courseDao, groupDao, studentDao);
	    new ConsoleMenu().show(courseDao, groupDao, studentDao);
	} catch (IllegalArgumentException | IOException | SQLException e) {
	    System.err.println(e.getMessage());
	} catch (RuntimeException e) {
	    System.err.println(UserMessages.SYSTEM_ERROR);
	}
    }
	    
}
