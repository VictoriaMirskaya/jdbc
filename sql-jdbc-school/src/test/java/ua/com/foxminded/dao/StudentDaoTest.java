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
import ua.com.foxminded.domain.Student;

class StudentDaoTest {

    private static Dao<Student> studentDao = new StudentDao();
    private static List<Student> students;
    
    @BeforeAll
    public static void init() {
	List<FirstNames> firstNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {	    
	    firstNames.addAll(Arrays.asList(FirstNames.values()));
	}
	List<LastNames> lastNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {
	    lastNames.addAll(Arrays.asList(LastNames.values()));
	}
	students = new ArrayList<>();
	Student student;
	for(int i = 1; i <= 200; i++) {
	    student = new Student(firstNames.get(i-1).getTitle(), lastNames.get(i-1).getTitle());
	    student.setId(i);
	    students.add(student);
	}	
    }
    
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
   
    @Test
    void addAllShouldAddListStudents() throws SQLException, IOException {	
	studentDao.addAll(students);
	List<Student> actual = new ArrayList<>();
	final String sql = "SELECT * FROM students";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Student student;
	    while (rs.next()) {
		student = new Student(rs.getString("first_name"), rs.getString("last_name"));
		student.setId(rs.getInt("student_id"));
		actual.add(student);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	assertIterableEquals(students, actual);
    }
    
    @Test
    void selectAllShouldReturnListStudents() throws SQLException, IOException {
	final String sql = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Student student : students) {
		statement.setString(1, student.getFirstName());
		statement.setString(2, student.getLastName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
	List<Student> actual = studentDao.selectAll();
	assertIterableEquals(students, actual);
    }
    
    @Test
    void deleteByIdShouldDeleteStudentById() throws SQLException, IOException {
	String sql = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Student student : students) {
		statement.setString(1, student.getFirstName());
		statement.setString(2, student.getLastName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
	studentDao.deleteById(1);
	sql = "SELECT * FROM students";
	List<Student> actual = new ArrayList<>();
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Student student;
	    while (rs.next()) {
		student = new Student(rs.getString("first_name"), rs.getString("last_name"));
		student.setId(rs.getInt("student_id"));
		actual.add(student);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	assertEquals(students.size() - 1, actual.size());
    }
    
    private enum FirstNames {

	ADAM("Adam"), FELIX("Felix"), HENRY("Henry"), JACK("Jack"), JACOB("Jacob"), LEO("Leo"), LUKE("Luke"),
	MICHAEL("Michael"), NATHAN("Nathan"), THOMAS("Thomas"), WILLIAM("William"), ALEXANDRA("Alexandra"),
	EMILY("Emily"), MIA("Mia"), NAOMI("Naomi"), KIRA("Kira"), IRIS("Iris"), VICTORIA("Victoria"), VIVIAN("Vivian"),
	OLIVIA("Olivia");

	private String title;

	FirstNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}

    }

    private enum LastNames {

	ANDERSON("Anderson"), BROOKS("Brooks"), CLARK("Clark"), COLLINS("Collins"), DAVIS("Davis"), EDWARDS("Edwards"),
	EVANS("Evans"), FISHER("Fisher"), FOSTER("Foster"), GARCIA("Garcia"), GREEN("Green"), HARRIS("Harris"),
	HOWARD("Howard"), LEWIS("Lewis"), MARTIN("Martin"), MITCHELL("Mitchell"), PHILLIPS("Phillips"),
	WILLIAMS("Williams"), WILSON("Wilson"), HOUSE("House");

	private String title;

	LastNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}
    }


}
