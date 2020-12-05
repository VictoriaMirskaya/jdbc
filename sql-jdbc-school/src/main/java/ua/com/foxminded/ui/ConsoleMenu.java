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

    SchoolService schoolService = new SchoolService();
    
    public void show(CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) throws SQLException, IOException {
	try (Scanner scanner = new Scanner(System.in)) {
	    boolean quitApplication = false;
	    do {
		System.out.println(produceMenuItems());
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

    public String produceMenuItems() {
	StringBuilder result = new StringBuilder();
	result.append(UserMessages.DELIMITER).append(UserMessages.NEW_LINE);
	result.append("Select one of the operations (enter the corresponding menu letter):").append(UserMessages.NEW_LINE);
	result.append("a. Find all groups with less or equals student count").append(UserMessages.NEW_LINE);
	result.append("b. Find all students related to course with given name").append(UserMessages.NEW_LINE);
	result.append("c. Add new student").append(UserMessages.NEW_LINE);
	result.append("d. Delete student by STUDENT_ID").append(UserMessages.NEW_LINE);
	result.append("e. Add a student to the course (from a list)").append(UserMessages.NEW_LINE);
	result.append("f. Remove the student from one of his or her courses").append(UserMessages.NEW_LINE);
	result.append("Quit application? (q)").append(UserMessages.NEW_LINE);
	result.append(UserMessages.DELIMITER).append(UserMessages.NEW_LINE);
	return result.toString();
    }
    
    public void printParametersAndResults(Scanner scanner, String selectItem, CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) throws SQLException, IOException {
	if (selectItem.equals("a")) {
	    System.out.println("Enter count of student (each group contain from 10 to 30 students):");
	    List<Group> groups = generateResultItemA(groupDao, scanner.nextInt());
	    if (groups.isEmpty()) {
		System.out.println("There are no groups with a given number of students.");
	    } else {
		System.out.println(groups.toString());
	    }
	} else if (selectItem.equals("b")) {
	    System.out.println("Enter course's name:");
	    List<Student> students = generateResultItemB(courseDao, studentDao, scanner.next());
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
	    if (generateResultItemC(studentDao, firstName, lastName)) {
		System.out.println("New student created!");
	    }
	} else if (selectItem.equals("d")) {
	    System.out.println("Enter student's id:");
	    if (generateResultItemD(studentDao, scanner.nextInt())) {
		System.out.println("Student deleted!");
	    }
	} else if (selectItem.equals("e")) {
	    System.out.println("Choose student's id:");
	    System.out.println(studentDao.selectAll());
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(courseDao.selectAll());
	    int courseId = scanner.nextInt();
	    if (generateResultItemE(courseDao, studentDao, studentId, courseId)) {
		System.out.println("Student added to the course!");
	    }
	} else if (selectItem.equals("f")) {
	    System.out.println("Choose student's id:");
	    System.out.println(studentDao.selectAll());
	    int studentId = scanner.nextInt();
	    System.out.println("Choose course's id:");
	    System.out.println(courseDao.selectAll());
	    int courseId = scanner.nextInt();
	    if (generateResultItemF(courseDao, studentDao, studentId, courseId)) {
		System.out.println("Student removed from the course!");
	    }
	}
    }
    
    private List<Group> generateResultItemA(GroupDao groupDao, int count) throws SQLException, IOException{
	return schoolService.findGroupsWhithLessOrEqualsStudentCount(groupDao, count);
    }
    
    private List<Student> generateResultItemB(CourseDao courseDao, StudentDao studentDao, String courseName) throws SQLException, IOException {
	return schoolService.findStudentsRelatedToCourseWithGivenName(courseDao, studentDao, courseName);
    }
   
    private boolean generateResultItemC(StudentDao studentDao, String firstName, String lastName) throws SQLException, IOException {
	return schoolService.addNewStudent(studentDao, firstName, lastName);
    }
    
    private boolean generateResultItemD(StudentDao studentDao, int studentId) throws SQLException, IOException {
	return schoolService.deleteStudentById(studentDao, studentId);
    }
    
    private boolean generateResultItemE(CourseDao courseDao, StudentDao studentDao, int studentId, int courseId) throws SQLException, IOException {
	return schoolService.addStudentToCourse(courseDao, studentDao, studentId, courseId);
    }
    
    private boolean generateResultItemF(CourseDao courseDao, StudentDao studentDao, int studentId, int courseId) throws SQLException, IOException {
	return schoolService.removeStudentFromCourse(courseDao, studentDao, studentId, courseId);
    }
    
}
