package ua.com.foxminded.dao;

import java.util.List;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TestDataCreator {

    public void createTestData() {
	DataReader dataReader = new FilesDataReader();
	List<Student> students = dataReader.loadStudents();
	List<Group> groups = dataReader.loadGroups();
	List<Course> courses = dataReader.loadCourses();
	createGroupsData(groups);
	createCoursesData(courses);
	createStudentsData(students, groups);
	assignStudentsToCourses(students, courses);
    }

    private void createGroupsData(List<Group> groups) {
//	* 10 groups with randomly generated names. The name should contain 2 characters, hyphen, 2 numbers
    }

    private void createCoursesData(List<Course> courses) {
//	* Create 10 courses (math, biology, etc)	
    }

    private void createStudentsData(List<Student> students, List<Group> groups) {
//	* 200 students. Take 20 first names and 20 last names and randomly combine them to generate students.
//	* Randomly assign students to groups. Each group could contain from 10 to 30 students. It is possible that some groups will be without students or students without groups	
    }

    private void assignStudentsToCourses(List<Student> students, List<Course> courses) {
//	* Create relation MANY-TO-MANY between tables STUDENTS and COURSES. Randomly assign from 1 to 3 courses for each student	
    }

}
