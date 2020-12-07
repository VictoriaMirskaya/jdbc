package ua.com.foxminded;

import java.io.IOException;
import java.sql.SQLException;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.db.SQLTablesCreator;
import ua.com.foxminded.init.DataGenerator;
import ua.com.foxminded.ui.ConsoleMenu;
import ua.com.foxminded.ui.UserMessages;

public class Main {

    public static void main(String[] args) {
	try {
	    CourseDao courseDao = new CourseDao();
	    GroupDao groupDao = new GroupDao();
	    StudentDao studentDao = new StudentDao();
	    new SQLTablesCreator().createTables();
	    new DataGenerator().generateTestData(courseDao, groupDao, studentDao);
	    new ConsoleMenu().show(courseDao, groupDao, studentDao);
	} catch (IllegalArgumentException | IOException | SQLException e) {
	    System.err.println(e.getMessage());
	} catch (RuntimeException e) {
	    System.err.println(UserMessages.SYSTEM_ERROR);
	}
    }
	    
}
