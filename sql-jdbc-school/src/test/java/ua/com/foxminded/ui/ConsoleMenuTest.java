package ua.com.foxminded.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.com.foxminded.dao.AbstractTest;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.SchoolService;
import ua.com.foxminded.dao.StudentDao;

class ConsoleMenuTest extends AbstractTest{

    CourseDao courseDao = new CourseDao();
    GroupDao groupDao = new GroupDao();
    StudentDao studentDao = new StudentDao();
    ConsoleMenu consoleMenu;
    SchoolService mockSchoolServise;
    
    @BeforeEach
    void init() {
	consoleMenu = new ConsoleMenu();
	mockSchoolServise = Mockito.mock(SchoolService.class);   
	consoleMenu.schoolService = mockSchoolServise; 
    }
    
    @Test
    void produceMenuItems_ShouldReturn_MenuItems() {
	String expected = ""
		+ "----------------------------------------------------\n"
		+ "Select one of the operations (enter the corresponding menu letter):\n" 
		+ "a. Find all groups with less or equals student count\n"
		+ "b. Find all students related to course with given name\n"
		+ "c. Add new student\n" 
		+ "d. Delete student by STUDENT_ID\n"
		+ "e. Add a student to the course (from a list)\n"
		+ "f. Remove the student from one of his or her courses\n"
		+ "Quit application? (q)\n"
		+ "----------------------------------------------------\n";
	assertEquals(expected, consoleMenu.produceMenuItems());
    }
   
    @Test
    void printParametersAndResults_ShouldCallCurrentServiceMethod_WhenSelectedItemA() throws SQLException, IOException {	
	consoleMenu.printParametersAndResults(new Scanner("15"), "a", courseDao, groupDao, studentDao);
	Mockito.verify(mockSchoolServise).findGroupsWhithLessOrEqualsStudentCount(groupDao, 15);
		
    }
    
    @Test
    void printParametersAndResults_ShouldCallCurrentServiceMethod_WhenSelectedItemB() throws SQLException, IOException {
	consoleMenu.printParametersAndResults(new Scanner("Art"), "b", courseDao, groupDao, studentDao);
	Mockito.verify(mockSchoolServise).findStudentsRelatedToCourseWithGivenName(courseDao, studentDao, "Art");
		
    }
    
    @Test
    void printParametersAndResults_ShouldCallCurrentServiceMethod_WhenSelectedItemC() throws SQLException, IOException {
	consoleMenu.printParametersAndResults(new Scanner("TestFirstName TestLastName"), "c", courseDao, groupDao, studentDao);
	Mockito.verify(mockSchoolServise).addNewStudent(studentDao, "TestFirstName", "TestLastName");
		
    }
    
    @Test
    void printParametersAndResults_ShouldCallCurrentServiceMethod_WhenSelectedItemD() throws SQLException, IOException {
	consoleMenu.printParametersAndResults(new Scanner("1"), "d", courseDao, groupDao, studentDao);
	Mockito.verify(mockSchoolServise).deleteStudentById(studentDao, 1);
		
    }
    
    @Test
    void printParametersAndResults_ShouldCallCurrentServiceMethod_WhenSelectedItemE() throws SQLException, IOException {
	consoleMenu.printParametersAndResults(new Scanner("1 1"), "e", courseDao, groupDao, studentDao);
	Mockito.verify(mockSchoolServise).addStudentToCourse(courseDao, studentDao, 1, 1);
		
    }
    
    @Test
    void printParametersAndResults_ShouldCallCurrentServiceMethod_WhenSelectedItemF() throws SQLException, IOException {
	consoleMenu.printParametersAndResults(new Scanner("1 1"), "f", courseDao, groupDao, studentDao);
	Mockito.verify(mockSchoolServise).removeStudentFromCourse(courseDao, studentDao, 1, 1);		
    }
    
}
