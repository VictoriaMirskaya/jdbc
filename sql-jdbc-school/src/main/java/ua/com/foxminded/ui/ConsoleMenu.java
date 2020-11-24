package ua.com.foxminded.ui;

import java.util.List;
import java.util.Scanner;
import ua.com.foxminded.dao.Dao;
import ua.com.foxminded.dao.TaskQueryRunner;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class ConsoleMenu {

    public void show(Dao<Course> courseDao, Dao<Group> groupDao, Dao<Student> studentDao) {
	try (Scanner scanner = new Scanner(System.in)) {
	    boolean quitApplication = false;
	    do {
		printMenuItems();
		String selectItem = scanner.next(); 
		quitApplication = selectItem.equals("q");
		if(!quitApplication) {
		   printParametersAndResults(scanner, selectItem, studentDao);
		}
	    } while (!quitApplication);
	} catch (RuntimeException e) {
	    System.out.println("System error");
	}
    }

    private static void printMenuItems() {
	System.out.println("Select one of the operations (enter the corresponding menu letter):");
	System.out.println("" 
		+ "a. Find all groups with less or equals student count\n"
		+ "b. Find all students related to course with given name\n"  
		+ "c. Add new student\n"
		+ "d. Delete student by STUDENT_ID\n" 
		+ "e. Add a student to the course (from a list)\n"
		+ "f. Remove the student from one of his or her courses\n"
	        + "Quit application? (q)");
	System.out.println("----------------------------------------------------");
    }
    
    private static void printParametersAndResults(Scanner scanner, String selectItem, Dao<Student> studentDao) {
	if (selectItem.equals("a")) {
	    System.out.println("Enter count of student:");
	    int count = scanner.nextInt();
	    List<Group> groups = TaskQueryRunner.findGroups(count);
	    System.out.println(groups);
	} else if (selectItem.equals("b")) {
	    System.out.println("Enter course's name:");
	    String courseName = scanner.next();
	    List<Student> students = TaskQueryRunner.findStudents(courseName);
	    System.out.println(students);
	} else if (selectItem.equals("c")) {
	    System.out.println("Enter first name:");
	    String firstName = scanner.next();
	    System.out.println("Enter last name:"); 
	    String lastName = scanner.next();
	    TaskQueryRunner.addNewStudent(studentDao, firstName, lastName);
	    System.out.println("New student created!");    
	} else if (selectItem.equals("d")) {
	    System.out.println("Enter student's id:");
	    int studentId = scanner.nextInt();
	    if(TaskQueryRunner.deleteStudent(studentId)) {
		System.out.println("Student deleted!");
	    }
	} else if (selectItem.equals("e") || selectItem.equals("f")) {
	    System.out.println("Choose student's id:");
	    System.out.println("" + "1 Ann Green\n" + "2 Brandon West\n" + "3 Tom Fox\n");
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println("" + "1 math\n" + "2 it\n" + "3 art\n");
	    int courseId = scanner.nextInt();
	    if (selectItem.equals("e")) {
		if (TaskQueryRunner.addStudentToCourse(studentId, courseId)) {
		    System.out.println("Student added to the course!");
		}
	    } else {
		if (TaskQueryRunner.removeStudentFromCourse(studentId, courseId)) {
		    System.out.println("Student removed from the course!");
		}
	    }
	}
    }
    
}
