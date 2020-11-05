package ua.com.foxminded;

import ua.com.foxminded.dao.ScriptTablesCreator;
import ua.com.foxminded.ui.ConsoleMenu;

public class Main {

    public static void main(String[] args) {	
	createTables();
	generateTestData();
	showConsoleMenu();	
    }
    
    private static void createTables() {
	new ScriptTablesCreator().createTables();
    }
    
    private static void generateTestData() {
	
	
    }
    
    private static void showConsoleMenu() {
	new ConsoleMenu().show();
    }

}
