package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.dao.TestDataGenerator.CourseNames;
import ua.com.foxminded.dao.TestDataGenerator.FirstNames;
import ua.com.foxminded.dao.TestDataGenerator.GroupNames;
import ua.com.foxminded.dao.TestDataGenerator.LastNames;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

class SchoolServiceTest extends AbstractTest{

    SchoolService schoolService = new SchoolService();

    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldThrowIOException_WhenInputIncorrectValue() {
	assertThrows(IOException.class, () -> schoolService.findGroupsWhithLessOrEqualsStudentCount(new GroupDao(), 5));
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturn_EmptyList_WhenGroupsNotFound() throws SQLException, IOException {
	generateData();	
	assertEquals(new ArrayList<Group>(), schoolService.findGroupsWhithLessOrEqualsStudentCount(new GroupDao(), 11));	
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturn_ListGoups_WhenGroupsFound() throws SQLException, IOException {
	generateData();
	GroupDao groupDao = new GroupDao();
	assertEquals(groupDao.selectAll(), schoolService.findGroupsWhithLessOrEqualsStudentCount(groupDao, 20));		
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldThrowIOException_WhenInputIncorrectValue() throws SQLException, IOException {
	generateData();	
	assertThrows(IOException.class, () -> schoolService.findStudentsRelatedToCourseWithGivenName(new CourseDao(), new StudentDao(), "TestName"));
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturnEmptyListStudents_WhenStudentsNotFound() throws SQLException, IOException {
	generateData();	
	assertEquals(new ArrayList<Student>(), schoolService.findStudentsRelatedToCourseWithGivenName(new CourseDao(), new StudentDao(), "Biology"));
    }
    
    @Test
    void findStudentsRelatedToCourseWithGivenName_ShouldReturn_ListStudents_WhenStudentsFound() throws SQLException, IOException {
	generateData();	
	assertEquals(new StudentDao().selectAll(), schoolService.findStudentsRelatedToCourseWithGivenName(new CourseDao(), new StudentDao(), "Mathematics"));
    }
    
    @Test
    void addNewStudent_ShouldAddedStudentToDB() throws SQLException, IOException {
	StudentDao studentDao = new StudentDao(); 
	assertTrue(schoolService.addNewStudent(studentDao, "TestFirstName", "TestLastName"));
	assertNotNull(studentDao.findByFirstNameLastName("TestFirstName", "TestLastName"));
    }
    
    @Test
    void deleteStudentById_ShouldThrowIOException_WhenStudentNotFound() {
	assertThrows(IOException.class, () -> schoolService.deleteStudentById(new StudentDao(), 1000));
    }
    
    @Test
    void deleteStudentById_ShouldDeleteStudentFromDB() throws SQLException, IOException {
	generateData();	
	StudentDao studentDao = new StudentDao(); 
	assertNotNull(studentDao.findById(1));	
	assertTrue(schoolService.deleteStudentById(new StudentDao(), 1));
	assertNull(studentDao.findById(1));	
    }

    @Test
    void addStudentToCourse_ShouldThrowIOException_WhenInputIncorrectCourseId() throws SQLException, IOException {
	generateData();
	assertThrows(IOException.class, () -> schoolService.addStudentToCourse(new CourseDao(), new StudentDao(), 1, 100));
    }
    
    @Test
    void addStudentToCourse_ShouldThrowIOException_WhenInputIncorrectStudentId() throws SQLException, IOException {
	generateData();
	assertThrows(IOException.class, () -> schoolService.addStudentToCourse(new CourseDao(), new StudentDao(), 5000, 1));
    }
    
    @Test
    void addStudentToCourse_ShouldAddStudentToCourse() throws SQLException, IOException {
	generateData();
	StudentDao studentDao = new StudentDao();
	CourseDao courseDao = new CourseDao();
	assertTrue(schoolService.addStudentToCourse(courseDao, studentDao, 1, 2));
	assertTrue(studentDao.IsStudentOnCourse(1, 2));
    }
 
    @Test
    void removeStudentFromCourse_ShouldThrowIOException_WhenInputIncorrectCourseId() throws SQLException, IOException {
	generateData();
	assertThrows(IOException.class, () -> schoolService.removeStudentFromCourse(new CourseDao(), new StudentDao(), 1, 100));
    }
    
    @Test
    void removeStudentFromCourse_ShouldThrowIOException_WhenInputIncorrectStudentId() throws SQLException, IOException {
	generateData();
	assertThrows(IOException.class, () -> schoolService.removeStudentFromCourse(new CourseDao(), new StudentDao(), 5000, 1));
    }
    
	
    @Test
    void removeStudentFromCourse_ShouldRemoveStudentFromCourse() throws SQLException, IOException {
	generateData();
	StudentDao studentDao = new StudentDao();
	CourseDao courseDao = new CourseDao();
	assertTrue(schoolService.removeStudentFromCourse(courseDao, studentDao, 1, 1));
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
