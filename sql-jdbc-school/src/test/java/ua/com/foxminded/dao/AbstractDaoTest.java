package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractDaoTest {
    
    @BeforeEach
    public void createTables() throws IOException, SQLException {
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement()) {
	    statement.execute("DROP TABLE IF EXISTS students_courses");
	    statement.execute("DROP TABLE IF EXISTS students");
	    statement.execute("DROP TABLE IF EXISTS groups");
            statement.execute("DROP TABLE IF EXISTS courses");
            statement.execute("CREATE TABLE groups("
        	    	    	+ "group_id INT GENERATED ALWAYS AS IDENTITY, " 
        	    	    	+ "group_name VARCHAR(255) NOT NULL, "
        	    	    	+ "PRIMARY KEY(group_id))");
            statement.execute("CREATE TABLE courses("
        	                + "course_id INT GENERATED ALWAYS AS IDENTITY, "
        	                + "course_name VARCHAR(255) NOT NULL, "
        	                + "course_description VARCHAR(255), "
        	                + "PRIMARY KEY(course_id))");
            statement.execute("CREATE TABLE students("
	    			+ "student_id INT GENERATED ALWAYS AS IDENTITY, " 
	    			+ "group_id INT, "
	    			+ "first_name VARCHAR(255) NOT NULL, "
	    			+ "last_name VARCHAR(255) NOT NULL, "
	    			+ "PRIMARY KEY(student_id), "
	    			+ "CONSTRAINT fk_group FOREIGN KEY(group_id) REFERENCES groups(group_id) ON DELETE CASCADE)");
            statement.execute("CREATE TABLE students_courses("
        	    		+ "student_id INT, "
        	    		+ "course_id INT, "
	    			+ "PRIMARY KEY(course_id, student_id), "
	    			+ "CONSTRAINT fk_student FOREIGN KEY(student_id) REFERENCES students(student_id) ON DELETE CASCADE, "
	    			+ "CONSTRAINT fk_course FOREIGN KEY(course_id) REFERENCES courses(course_id) ON DELETE CASCADE)");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
