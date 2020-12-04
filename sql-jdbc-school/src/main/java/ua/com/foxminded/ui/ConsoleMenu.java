package ua.com.foxminded.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ua.com.foxminded.UserMessages;
import ua.com.foxminded.dao.CourseDao;
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
    
    private void printParametersAndResults(Scanner scanner, String selectItem, Dao<Course> courseDao, Dao<Group> groupDao, Dao<Student> studentDao) throws SQLException, IOException {
	if (selectItem.equals("a")) {
	    System.out.println("Enter count of student (each group contain from 10 to 30 students):");
	    System.out.println(findGroupsWhithLessOrEqualsStudentCount(groupDao, scanner.nextInt()));
	} else if (selectItem.equals("b")) {
	    System.out.println("Enter course's name:");
	    System.out.println(findStudentsRelatedToCourseWithGivenName(courseDao, studentDao, scanner.next()));
	} else if (selectItem.equals("c")) {
	    System.out.println("Enter first name:");
	    String firstName = scanner.next();
	    System.out.println("Enter last name:"); 
	    String lastName = scanner.next();
	    System.out.println(addNewStudent(studentDao, firstName, lastName));    
	} else if (selectItem.equals("d")) {
	    System.out.println("Enter student's id:");
	    System.out.println(deleteStudentById(studentDao, scanner.nextInt()));
	} else if (selectItem.equals("e")) {
	    System.out.println("Choose student's id:");
	    System.out.println(studentDao.selectAll());
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(courseDao.selectAll());
	    int courseId = scanner.nextInt();
	    System.out.println(addStudentToCourse(courseDao, studentDao, studentId, courseId));
	} else if (selectItem.equals("f")) {
	    System.out.println("Choose student's id:");
	    System.out.println(studentDao.selectAll());
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(courseDao.selectAll());
	    int courseId = scanner.nextInt();
	    System.out.println(removeStudentFromCourse(courseDao, studentDao, studentId, courseId));
	}
    }

    private String findGroupsWhithLessOrEqualsStudentCount(Dao<Group> groupDao, int count) throws SQLException, IOException {
	if(count < 10 || count > 30) {
	    return "Enter correct value: from 10 to 30.";
	}
	List<Group> groups = ((GroupDao) groupDao).findGroupsWhithLessOrEqualsStudentCount(count);
	if(groups.isEmpty()) {
	    return "There are no groups with a given number of students";
	} else {
	    return groups.toString(); 
	}	
    }
    
    private String findStudentsRelatedToCourseWithGivenName(Dao<Course> courseDao, Dao<Student> studentDao, String courseName) throws SQLException, IOException {
	if(((CourseDao)courseDao).findByName(courseName) == null) {
	    return "There are no courses with a given name.";
	}
	List<Student> students = ((StudentDao)studentDao).findStudentsRelatedToCourseWithGivenName(courseName);
	if(students.isEmpty()) {
	    return "There are no students on course with a given name.";
	} else {
	    return students.toString();
	}
    }
    
    private String addNewStudent(Dao<Student> studentDao, String firstName, String lastName) throws SQLException, IOException {
	List<Student> students = new ArrayList<>();
	students.add(new Student(firstName, lastName));
	studentDao.addAll(students);	
	return "New student created!";
    }
    
    private String deleteStudentById(Dao<Student> studentDao, int studentId) throws SQLException, IOException {
	if(((StudentDao)studentDao).findById(studentId) == null) {
	    return "There are no student with a given student id.";
	}
	studentDao.deleteById(studentId);	
	return "Student deleted!";
    }
    
    private String addStudentToCourse(Dao<Course> courseDao, Dao<Student> studentDao, int studentId, int courseId) throws SQLException, IOException {
	if(((CourseDao)courseDao).findById(courseId) == null) {
	    return "There are no courses with a given course id.";
	}
	if(((StudentDao)studentDao).findById(studentId) == null) {
	    return "There are no student with a given student id.";
	}
	((StudentDao)studentDao).addStudentToCourse(studentId, courseId);
	return "Student added to the course!";
    }
    
    private String removeStudentFromCourse(Dao<Course> courseDao, Dao<Student> studentDao, int studentId, int courseId) throws SQLException, IOException {
	if(((CourseDao)courseDao).findById(courseId) == null) {
	    return "There are no courses with a given course id.";
	}
	if(((StudentDao)studentDao).findById(studentId) == null) {
	    return "There are no student with a given student id.";
	}
	((StudentDao)studentDao).removeStudentFromCourse(studentId, courseId);
	return "Student removed from the course!";
    }   
    
}
