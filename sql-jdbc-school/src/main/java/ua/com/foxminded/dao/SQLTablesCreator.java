package ua.com.foxminded.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLTablesCreator implements TablesCreator {

    private static String url = "jdbc:postgresql://localhost:5432/school";
    private static String user = "postgres";
    private static String password = "310589";

    @Override
    public void createTables() {
	try (Connection connection = DriverManager.getConnection(url, user, password);
		BufferedReader sqlFile = new BufferedReader(new FileReader(
			"D:\\sql-jdbc-school\\sql-jdbc-school\\src\\main\\resources\\CreateTablesScript.sql"));
		Scanner scanner = new Scanner(sqlFile);
		Statement statement = connection.createStatement()) {
	    String line = "";
	    while (scanner.hasNextLine()) {
		line = scanner.nextLine();
		if (line.endsWith(";")) {
		    line = line.substring(0, line.length() - 1);
		}
		statement.executeUpdate(line);
	    }
	} catch (SQLException | IOException e) {
	    e.printStackTrace();
	}
    }

}
