package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.servise.CourseNames;
import ua.com.foxminded.servise.FirstNames;
import ua.com.foxminded.servise.GroupNames;
import ua.com.foxminded.servise.LastNames;

public  class TestTools {
    
    public void createTables() throws IOException, SQLException {
	new SQLTablesCreator().createTables();
    }

    public void generateData(CourseDao courseDao, GroupDao groupDao, StudentDao studentDao) throws SQLException, IOException {
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
	    student.setCourses(studentCources);
	}
	studentDao.assignStudentsToCourses(students);
    }
    
    public List<Group> generateGroups(int count) {
	List<GroupNames> groupsNames = Arrays.asList(GroupNames.values());
	List<Group> groups = new ArrayList<>();
	Group group;
	for (int i = 1; i <= count; i++) {
	    group = new Group(groupsNames.get(i-1).getTitle());
	    group.setId(i);
	    groups.add(group);
	}
	return groups;
    }
    
    public List<Course> generateCourses(int count) {
	List<CourseNames> coursesNames = Arrays.asList(CourseNames.values());
	List<Course> courses = new ArrayList<>();
	Course course;
	for (int i = 1; i <= count; i++) {
	    course = new Course(coursesNames.get(i-1).getTitle());
	    course.setId(i);
	    courses.add(course);
	}
	return courses;
    }

    public List<Student> generateStudents(int count) {
	List<FirstNames> firstNames = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
	    firstNames.addAll(Arrays.asList(FirstNames.values()));
	}
	List<LastNames> lastNames = new ArrayList<>();
	for (int i = 0; i < 10; i++) {
	    lastNames.addAll(Arrays.asList(LastNames.values()));
	}
	List<Student> students = new ArrayList<>();
	Student student;
	for (int i = 1; i <= count; i++) {
	    student = new Student(firstNames.get(i - 1).getTitle(), lastNames.get(i - 1).getTitle());
	    student.setId(i);
	    students.add(student);
	}
	return students;
    }   
    
}
