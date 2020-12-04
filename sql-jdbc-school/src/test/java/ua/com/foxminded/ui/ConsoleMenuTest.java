package ua.com.foxminded.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.AbstractDaoTest;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.dao.TestDataGenerator.CourseNames;
import ua.com.foxminded.dao.TestDataGenerator.FirstNames;
import ua.com.foxminded.dao.TestDataGenerator.GroupNames;
import ua.com.foxminded.dao.TestDataGenerator.LastNames;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

class ConsoleMenuTest extends AbstractDaoTest {

    static ConsoleMenu consoleMenu = new ConsoleMenu();

    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturn_IncorrectValueMessage_WhenInputIncorrectValue() throws SQLException, IOException {
	assertEquals("Enter correct value: from 10 to 30.", consoleMenu.findGroupsWhithLessOrEqualsStudentCount(new GroupDao(), 5));	
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturn_NoGoupsMessage_WhenGroupsNotFound() throws SQLException, IOException {
	generateData();	
	assertEquals("There are no groups with a given number of students.", consoleMenu.findGroupsWhithLessOrEqualsStudentCount(new GroupDao(), 11));	
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturn_ListGoups_WhenGroupsFound() throws SQLException, IOException {
	generateData();
	assertEquals("[\n{id=1, name=MO-15}]", consoleMenu.findGroupsWhithLessOrEqualsStudentCount(new GroupDao(), 20).toString());		
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturn_IncorrectValueMessage_WhenInputIncorrectValue() throws SQLException, IOException {
	generateData();	
	assertEquals("There are no courses with a given name.", consoleMenu.findStudentsRelatedToCourseWithGivenName(new CourseDao(), new StudentDao(), "TestName"));
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturn_NoStudentsMessage_WhenStudentsNotFound() throws SQLException, IOException {
	generateData();	
	assertEquals("There are no students on course with a given name.", consoleMenu.findStudentsRelatedToCourseWithGivenName(new CourseDao(), new StudentDao(), "Biology"));
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturn_ListStudents_WhenStudentsFound() throws SQLException, IOException {
	generateData();	
	String expected = "[\n" + 
		"{id=1, firstName=Adam, lastName=Anderson}, \n" + 
		"{id=2, firstName=Felix, lastName=Brooks}, \n" + 
		"{id=3, firstName=Henry, lastName=Clark}, \n" + 
		"{id=4, firstName=Jack, lastName=Collins}, \n" + 
		"{id=5, firstName=Jacob, lastName=Davis}, \n" + 
		"{id=6, firstName=Leo, lastName=Edwards}, \n" + 
		"{id=7, firstName=Luke, lastName=Evans}, \n" + 
		"{id=8, firstName=Michael, lastName=Fisher}, \n" + 
		"{id=9, firstName=Nathan, lastName=Foster}, \n" + 
		"{id=10, firstName=Thomas, lastName=Garcia}, \n" + 
		"{id=11, firstName=William, lastName=Green}, \n" + 
		"{id=12, firstName=Alexandra, lastName=Harris}, \n" + 
		"{id=13, firstName=Emily, lastName=Howard}, \n" + 
		"{id=14, firstName=Mia, lastName=Lewis}, \n" + 
		"{id=15, firstName=Naomi, lastName=Martin}, \n" + 
		"{id=16, firstName=Kira, lastName=Mitchell}, \n" + 
		"{id=17, firstName=Iris, lastName=Phillips}, \n" + 
		"{id=18, firstName=Victoria, lastName=Williams}, \n" + 
		"{id=19, firstName=Vivian, lastName=Wilson}, \n" + 
		"{id=20, firstName=Olivia, lastName=House}]";
	assertEquals(expected, consoleMenu.findStudentsRelatedToCourseWithGivenName(new CourseDao(), new StudentDao(), "Mathematics"));
    }
    
    @Test
    void addNewStudent_ShouldAddedStudentToDB() throws SQLException, IOException {
	StudentDao studentDao = new StudentDao(); 
	assertEquals("New student created!", consoleMenu.addNewStudent(studentDao, "TestFirstName", "TestLastName"));
	assertNotNull(studentDao.findByFirstNameLastName("TestFirstName", "TestLastName"));
    }
    
    @Test
    void deleteStudentById_ShouldReturn_NoStudentMessege_WhenStudentNotFound() throws SQLException, IOException {
	assertEquals("There are no student with a given student id.", consoleMenu.deleteStudentById(new StudentDao(), 1000));
    }
    
    @Test
    void deleteStudentById_ShouldDeleteStudentFromDB() throws SQLException, IOException {
	generateData();	
	StudentDao studentDao = new StudentDao(); 
	assertNotNull(studentDao.findById(1));	
	assertEquals("Student deleted!", consoleMenu.deleteStudentById(new StudentDao(), 1));
	assertNull(studentDao.findById(1));	
    }

    @Test
    void addStudentToCourse_ShouldReturn_IncorrectCourseIdMessage_WhenInputIncorrectCourseId() throws SQLException, IOException {
	generateData();
	assertEquals("There are no courses with a given course id.", consoleMenu.addStudentToCourse(new CourseDao(), new StudentDao(), 1, 100));
    }
    
    @Test
    void addStudentToCourse_ShouldReturn_IncorrectStudentIdMessage_WhenInputIncorrectStudentId() throws SQLException, IOException {
	generateData();
	assertEquals("There are no student with a given student id.", consoleMenu.addStudentToCourse(new CourseDao(), new StudentDao(), 5000, 1));
    }
    
    @Test
    void addStudentToCourse_ShouldAddStudentToCourse() throws SQLException, IOException {
	generateData();
	StudentDao studentDao = new StudentDao();
	CourseDao courseDao = new CourseDao();
	assertEquals("Student added to the course!", consoleMenu.addStudentToCourse(courseDao, studentDao, 1, 2));
	assertTrue(studentDao.IsStudentOnCourse(1, 2));
    }
 
    @Test
    void removeStudentFromCourse_ShouldReturn_IncorrectCourseIdMessage_WhenInputIncorrectCourseId() throws SQLException, IOException {
	generateData();
	assertEquals("There are no courses with a given course id.", consoleMenu.removeStudentFromCourse(new CourseDao(), new StudentDao(), 1, 100));
    }
    
    @Test
    void removeStudentFromCourse_ShouldReturn_IncorrectStudentIdMessage_WhenInputIncorrectStudentId() throws SQLException, IOException {
	generateData();
	assertEquals("There are no student with a given student id.", consoleMenu.removeStudentFromCourse(new CourseDao(), new StudentDao(), 5000, 1));
    }
    
	
    @Test
    void removeStudentFromCourse_ShouldRemoveStudentFromCourse() throws SQLException, IOException {
	generateData();
	StudentDao studentDao = new StudentDao();
	CourseDao courseDao = new CourseDao();
	assertEquals("Student removed from the course!", consoleMenu.removeStudentFromCourse(courseDao, studentDao, 1, 1));
	assertFalse(studentDao.IsStudentOnCourse(1, 1));
    }   
    
    private void generateData() throws SQLException, IOException {
	GroupDao groupDao = new GroupDao();
	CourseDao courseDao = new CourseDao();
	StudentDao studentDao = new StudentDao();
	groupDao.addAll(generateGroups(1));
	courseDao.addAll(generateCourses(2));
	studentDao.addAll(generateStudents(20));
	List<Group> groups = groupDao.selectAll();
	List<Course> courses = courseDao.selectAll();
	List<Student> students = studentDao.selectAll();
	for (Student student : students) {
	    student.setGroup(groups.get(0));
	}
	studentDao.assignStudentsToGroup(students);
	List<Course> studentCources;
	for (Student student : students) {
	    studentCources = new ArrayList<>();
	    studentCources.add(courses.get(0));
	    student.setCources(studentCources);
	}
	studentDao.assignStudentsToCourses(students);
    }
    
    private List<Group> generateGroups(int count) {
	List<GroupNames> groupsNames = Arrays.asList(GroupNames.values());
	List<Group> groups = new ArrayList<>();
	for (int i = 0; i < count; i++) {
	    groups.add(new Group(groupsNames.get(i).getTitle()));
	}
	return groups;
    }

    private List<Student> generateStudents(int count) {
	List<FirstNames> firstNames = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
	    firstNames.addAll(Arrays.asList(FirstNames.values()));
	}
	List<LastNames> lastNames = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
	    lastNames.addAll(Arrays.asList(LastNames.values()));
	}
	List<Student> students = new ArrayList<>();
	for (int i = 1; i <= count; i++) {
	    students.add(new Student(firstNames.get(i - 1).getTitle(), lastNames.get(i - 1).getTitle()));
	}
	return students;
    }

    private List<Course> generateCourses(int count) {
	List<CourseNames> coursesNames = Arrays.asList(CourseNames.values());
	List<Course> courses = new ArrayList<>();
	for (int i = 0; i < count; i++) {
	    courses.add(new Course(coursesNames.get(i).getTitle()));
	}
	return courses;
    }
    
    

}
