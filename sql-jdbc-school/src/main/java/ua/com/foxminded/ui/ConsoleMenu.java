package ua.com.foxminded.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import ua.com.foxminded.UserMessages;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.SchoolService;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class ConsoleMenu {

    public void show(CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) throws SQLException, IOException {
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

    private void printMenuItems() {
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
    
    private void printParametersAndResults(Scanner scanner, String selectItem, CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) throws SQLException, IOException {
	SchoolService schoolService = new SchoolService();
	if (selectItem.equals("a")) {
	    System.out.println("Enter count of student (each group contain from 10 to 30 students):");
	    List<Group> groups = schoolService.findGroupsWhithLessOrEqualsStudentCount(groupDao, scanner.nextInt());
	    if (groups.isEmpty()) {
		System.out.println("There are no groups with a given number of students.");
	    } else {
		System.out.println(groups.toString());
	    }	   
	} else if (selectItem.equals("b")) {
	    System.out.println("Enter course's name:");
	    List<Student> students = schoolService.findStudentsRelatedToCourseWithGivenName(courseDao, studentDao,
		    scanner.next());
	    if (students.isEmpty()) {
		System.out.println("There are no students on course with a given name.");
	    } else {
		System.out.println(students.toString());
	    }
	} else if (selectItem.equals("c")) {
	    System.out.println("Enter first name:");
	    String firstName = scanner.next();
	    System.out.println("Enter last name:"); 
	    String lastName = scanner.next();
	    if (schoolService.addNewStudent(studentDao, firstName, lastName)) {
		System.out.println("New student created!");
	    }
	} else if (selectItem.equals("d")) {
	    System.out.println("Enter student's id:");
	    if (schoolService.deleteStudentById(studentDao, scanner.nextInt())) {
		System.out.println("Student deleted!");
	    }
	} else if (selectItem.equals("e")) {
	    System.out.println("Choose student's id:");
	    System.out.println(studentDao.selectAll());
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(courseDao.selectAll());
	    int courseId = scanner.nextInt();
	    if (schoolService.addStudentToCourse(courseDao, studentDao, studentId, courseId)) {
		System.out.println("Student added to the course!");
	    }
	} else if (selectItem.equals("f")) {
	    System.out.println("Choose student's id:");
	    System.out.println(studentDao.selectAll());
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(courseDao.selectAll());
	    int courseId = scanner.nextInt();
	    if (schoolService.removeStudentFromCourse(courseDao, studentDao, studentId, courseId)) {
		System.out.println("Student removed from the course!");
	    }
	}
    }
    
}
