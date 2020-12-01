package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Student;

class StudentDaoTest {

    private static Dao<Student> studentDao = new StudentDao();

    @Test
    void selectAllShouldReturnListStudents() throws SQLException {
	List<FirstNames> firstNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {	    
	    firstNames.addAll(Arrays.asList(FirstNames.values()));
	}
	List<LastNames> lastNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {
	    lastNames.addAll(Arrays.asList(LastNames.values()));
	}
	List<Student> expected = new ArrayList<>();
	for(int i = 1; i <= 200; i++) {
	    Student student = new Student(firstNames.get(i-1).getTitle(), lastNames.get(i-1).getTitle());
	    student.setId(i);
	    expected.add(student);
	}	
	List<Student> actual = studentDao.selectAll();
	assertIterableEquals(expected, actual);
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
