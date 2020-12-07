package ua.com.foxminded.init;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class DataGenerator {
    
    private final Random random = new Random();
      
    public void generateTestData(CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) throws SQLException, IOException {
	generateGroups(groupDao);	
	generateCourses(courseDao);
	generateStudents(studentDao);	
	assignStudentsToGroup(studentDao, groupDao);	
	assignStudentsToCourses(studentDao, courseDao);	
    }

    private void generateGroups(GroupDao groupDao) throws SQLException, IOException {
	List<GroupNames> groupsNames = Arrays.asList(GroupNames.values());
	List<Group> groups = new ArrayList<>();
	for (GroupNames groupNames : groupsNames) {
	    groups.add(new Group(groupNames.getTitle()));
	}
	groupDao.addAll(groups);
    }

    private void generateCourses(CourseDao courseDao) throws SQLException, IOException {
	List<CourseNames> coursesNames = Arrays.asList(CourseNames.values());
	List<Course> courses = new ArrayList<>();
	for (CourseNames courseNames : coursesNames) {
	    courses.add(new Course(courseNames.getTitle()));
	}
	courseDao.addAll(courses);
    }
    
    private void generateStudents(StudentDao studentDao) throws SQLException, IOException {
	List<FirstNames> firstNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {	    
	    firstNames.addAll(Arrays.asList(FirstNames.values()));
	}
	List<LastNames> lastNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {
	    lastNames.addAll(Arrays.asList(LastNames.values()));
	}
	Collections.shuffle(firstNames);
	Collections.shuffle(lastNames);
	List<Student> students = new ArrayList<>();
	for(int i = 1; i <= 200; i++) {
	    students.add(new Student(firstNames.get(i-1).getTitle(), lastNames.get(i-1).getTitle()));
	}
	studentDao.addAll(students);		
    }
     
    private void assignStudentsToGroup(StudentDao studentDao, GroupDao groupDao) throws SQLException, IOException {
	List<Student> students = studentDao.selectAll();
	List<Group> groups = groupDao.selectAll();
	int capasity = calculateGroupCapasity();
	int fullness = 0;
	int index = 0;
	for (Student student : students) {
	    if(fullness > capasity) {
		index++; 
		if(index > 9) {
		    break;
		}
		fullness = 0;
		capasity = calculateGroupCapasity();
	    }
	    student.setGroup(groups.get(index));
	    fullness++;
	}
	studentDao.assignStudentsToGroup(students);
    }

    private void assignStudentsToCourses(StudentDao studentDao, CourseDao courseDao) throws SQLException, IOException {
	List<Student> students = studentDao.selectAll();
	List<Course> courses = courseDao.selectAll();
	int courseQuantity;
	int index;
	List<Course> studentCourses;
	for (Student student : students) {
	    courseQuantity = calculateCourseQuantity();
	    studentCourses = new ArrayList<>();
	    for(int i = 1; i <= courseQuantity; i ++) {
		index = generateRandomCourseIndex();
		if (!studentCourses.contains(courses.get(index))) {
		    studentCourses.add(courses.get(index));
		}
	    }
	    student.setCourses(studentCourses);
	}
	studentDao.assignStudentsToCourses(students);
    }

    private int calculateGroupCapasity() {
	return random.nextInt(21) + 10;
    }
    
    private int calculateCourseQuantity() {
	return random.nextInt(3) + 1;
    }
    
    private int generateRandomCourseIndex() {
	return random.nextInt(10);
    }

}
