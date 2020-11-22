package ua.com.foxminded.ui;

import java.util.Scanner;
import ua.com.foxminded.dao.Dao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.domain.StudentCourse;

public class ConsoleMenu {

    public void show(Dao<Course> courseDao, Dao<Group> groupDao, Dao<Student> studentDao, Dao<StudentCourse> studentCourseDao) {
	try (Scanner scanner = new Scanner(System.in)) {
	    boolean quitApplication = false;
	    do {
		printMenuItems();
		String selectItem = scanner.next(); 
		quitApplication = selectItem.equals("q");
		if(!quitApplication) {
		   printParametersAndResults(scanner, selectItem);
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
    
    private static void printParametersAndResults(Scanner scanner, String selectItem) {
	if (selectItem.equals("a")) {
	    System.out.println("Enter count of student:");
	    int count = scanner.nextInt();
	    findGroups(count);
	} else if (selectItem.equals("b")) {
	    System.out.println("Enter course's name:");
	    String courseName = scanner.next();
	    findStudents(courseName);
	} else if (selectItem.equals("c")) {
	    System.out.println("Enter first name:");
	    String firstName = scanner.next();
	    System.out.println("Enter last name:"); 
	    String lastName = scanner.next();
	    addNewStudent(firstName, lastName);
	} else if (selectItem.equals("d")) {
	    System.out.println("Enter student's id:");
	    int studentId = scanner.nextInt();
	    deleteStudent(studentId);
	} else if (selectItem.equals("e")||selectItem.equals("f")) {
	    System.out.println("Choose student's id:");
	    System.out.println(""
	    	+ "1 Ann Green\n"
	    	+ "2 Brandon West\n"
	    	+ "3 Tom Fox\n");
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(""
	    	+ "1 math\n"
	    	+ "2 it\n"
	    	+ "3 art\n");
	    int courseId = scanner.nextInt(); 
	    if(selectItem.equals("e")) {
		addStudentToCourse(studentId, courseId);
	    }else {
		removeStudentFromCourse(studentId, courseId);
	    }
	}
    }
    
    private static void findGroups(int count) {
	System.out.println("We will soon find all this groups, believe us!\n");
    }

    private static void findStudents(String courseName) {
	System.out.println("We will soon find all this students, believe us!\n");
    }

    private static void addNewStudent(String firstName, String lastName) {
	System.out.println("We will soon add this student, believe us!\n");
    }

    private static void deleteStudent(int StudentId) {
	System.out.println("We will soon delete this student, believe us!\n");
    }

    private static void addStudentToCourse(int studentId, int courseId) {
	System.out.println("We will soon add this student to this course, believe us!\n");
    }

    private static void removeStudentFromCourse(int studentId, int courseId) {
	System.out.println("We will soon remove this student from this course, believe us!\n");
    }
    
}
