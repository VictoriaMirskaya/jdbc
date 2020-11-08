package ua.com.foxminded;

import java.io.IOException;
import java.sql.SQLException;
import ua.com.foxminded.dao.SQLTablesCreator;
import ua.com.foxminded.dao.TestDataGenerator;
import ua.com.foxminded.ui.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
	try {
	    createTables();
	    generateTestData();
	    showConsoleMenu();
	} catch (IOException | SQLException e) {
	    System.err.println(e.getMessage());
	} catch (RuntimeException e) {
	    System.err.println(UserMessages.SYSTEM_ERROR);
	}
    }

    private static void createTables() throws IOException, SQLException {
	new SQLTablesCreator().createTables();
    }

    private static void generateTestData() {
	new TestDataGenerator().createTestData();
    }

    private static void showConsoleMenu() {
	new ConsoleMenu().show();
    }

}
