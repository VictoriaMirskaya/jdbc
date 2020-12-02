package ua.com.foxminded.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ua.com.foxminded.UserMessages;
import ua.com.foxminded.dao.Dao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class ConsoleMenu {

    public void show(Dao<Course> courseDao, Dao<Group> groupDao, Dao<Student> studentDao) throws SQLException, IOException {
	try (Scanner scanner = new Scanner(System.in)) {
	    boolean quitApplication = false;
	    do {
		printMenuItems();
		String selectItem = scanner.next();
		quitApplication = selectItem.equals("q");
		if (!quitApplication) {
		    printParametersAndResults(scanner, selectItem, courseDao, groupDao, studentDao);
		}
	    } while (!quitApplication);
	} catch (RuntimeException e) {
	    System.out.println(UserMessages.SYSTEM_ERROR);
	}
    }

    private static void printMenuItems() {
	System.out.println(UserMessages.DELIMITER);
	System.out.println("Select one of the operations (enter the corresponding menu letter):");
	System.out.println( 
	          "a. Find all groups with less or equals student count\n"
		+ "b. Find all students related to course with given name\n"  
		+ "c. Add new student\n"
		+ "d. Delete student by STUDENT_ID\n" 
		+ "e. Add a student to the course (from a list)\n"
		+ "f. Remove the student from one of his or her courses\n"
	        + "Quit application? (q)");
	System.out.println(UserMessages.DELIMITER);
    }
    
    private static void printParametersAndResults(Scanner scanner, String selectItem, Dao<Course> courseDao, Dao<Group> groupDao, Dao<Student> studentDao) throws SQLException, IOException {
	if (selectItem.equals("a")) {
	    System.out.println("Enter count of student:");
	    System.out.println(((GroupDao)groupDao).findGroupsWhithLessOrEqualsStudentCount(scanner.nextInt()));
	} else if (selectItem.equals("b")) {
	    System.out.println("Enter course's name:");
	    System.out.println(((StudentDao)studentDao).findStudentsRelatedToCourseWithGivenName(scanner.next()));
	} else if (selectItem.equals("c")) {
	    System.out.println("Enter first name:");
	    String firstName = scanner.next();
	    System.out.println("Enter last name:"); 
	    String lastName = scanner.next();
	    List<Student> students = new ArrayList<>();
	    students.add(new Student(firstName, lastName));
	    studentDao.addAll(students);
	    System.out.println("New student created!");    
	} else if (selectItem.equals("d")) {
	    System.out.println("Enter student's id:");
	    studentDao.deleteById(scanner.nextInt());
	    System.out.println("Student deleted!");
	} else if (selectItem.equals("e") || selectItem.equals("f")) {
	    System.out.println("Choose student's id:");
	    System.out.println(studentDao.selectAll());
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(courseDao.selectAll());
	    int courseId = scanner.nextInt();
	    if (selectItem.equals("e")) {
		((StudentDao)studentDao).addStudentToCourse(studentId, courseId);
		System.out.println("Student added to the course!");
	    } else {
		((StudentDao)studentDao).removeStudentFromCourse(studentId, courseId);
		System.out.println("Student removed from the course!");
	    }
	}
    }
    
}
