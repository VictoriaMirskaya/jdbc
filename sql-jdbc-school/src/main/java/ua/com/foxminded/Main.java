package ua.com.foxminded;

import ua.com.foxminded.dao.SQLTablesCreator;
import ua.com.foxminded.dao.TestDataCreator;
import ua.com.foxminded.ui.ConsoleMenu;

public class Main {

    public static void main(String[] args) {
	createTables();
	generateTestData();
	showConsoleMenu();
    }

    private static void createTables() {
	new SQLTablesCreator().createTables();
    }

    private static void generateTestData() {
	new TestDataCreator().createTestData();
    }

    private static void showConsoleMenu() {
	new ConsoleMenu().show();
    }

}
