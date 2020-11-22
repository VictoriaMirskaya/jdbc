package ua.com.foxminded.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.domain.StudentCourse;

public class TestDataGenerator {
    
    private final Random random = new Random();
      
    public void generateTestData(Dao<Course> courseDao, Dao<Group> groupDao, Dao<Student> studentDao, Dao<StudentCourse> studentCourseDao) {
	List<Group> groups = generateGroups(groupDao);
	List<Course> courses = generateCourses(courseDao);
	List<Student> students = generateStudents(studentDao, groups);
	assignStudentsToCourses(studentCourseDao, students, courses);
    }

    private List<Group> generateGroups(Dao<Group> groupDao) {
	List<GroupNames> groupsNames = Arrays.asList(GroupNames.values());
	List<Group> groups = new ArrayList<>();	
	for(int i = 1; i <= groupsNames.size(); i++) {
	    groups.add(new Group(i, groupsNames.get(i-1).getTitle()));
	}
	groupDao.saveList(groups);
	return groups;
    }

    private List<Course> generateCourses(Dao<Course> courseDao) {
	List<CourseNames> coursesNames = Arrays.asList(CourseNames.values());
	List<Course> courses = new ArrayList<>();
	for (int i = 1; i <= coursesNames.size(); i++) {
	    courses.add(new Course(i, coursesNames.get(i-1).getTitle()));
	}
	courseDao.saveList(courses);
	return courses;
    }
    
    private List<Student> generateStudents(Dao<Student> studentDao, List<Group> groups){
	List<Student> students = createStudents();
	assignStudentsToGroup(students, groups);
	studentDao.saveList(students);
	return students;
    }
    
    private List<Student> createStudents() {
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
	for(int i = 0; i < 200; i++) {
	    students.add(new Student(i+1, firstNames.get(i).getTitle(), lastNames.get(i).getTitle()));
	}
	return students;
    }
    
    private void assignStudentsToGroup(List<Student> students, List<Group> groups) {
	int capasity = calculateGroupCapasity();
	int fullness = 0;
	int index = 0;
	for (Student student : students) {
	    if(fullness > capasity) {
		index++; 
		if(index > 9) {
		    break;
		}
		capasity = calculateGroupCapasity();
	    }
	    student.setGroup(groups.get(index));
	}
    }

    private void assignStudentsToCourses(Dao<StudentCourse> studentCourseDao, List<Student> students, List<Course> courses) {
	int courseQuantity;
	List<StudentCourse> studentCourse = new ArrayList<>();
	for (Student student : students) {
	    courseQuantity = calculateCourseQuantity();
	    for(int i = 1; i <= courseQuantity; i ++) {
		studentCourse.add(new StudentCourse(student.getId(), courses.get(generateRandomCourseIndex()).getId()));
	    }
	}
	studentCourseDao.saveList(studentCourse);
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
    
    private enum GroupNames{
	
	MO15 ("MO-15"),
	M020 ("MO-20"),
	EC15 ("EC-15"),
	EC20 ("EC-20"),
	IT15 ("IT-15"),
	IT20 ("IT-20"),
	QA15 ("QA-15"),
	QA20 ("QA-20"),
	JS15 ("JS-15"),
	JS20 ("JS-20");
	
	private String title;

	GroupNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}
    }
    
    private enum CourseNames{
	
	MATHEMATICS ("Mathematics"),
	BIOLOGY ("Biology"),
	ENGLISH ("English"),
	IT ("Information Technology"),
	DATABASE ("Database"),
	HISTORY ("History"),
	ECONOMY ("Economy"),
	ART ("Art"),
	DESIGN ("Design"),
	ARCHITECTURE ("Architecture");
	
	private String title;

	CourseNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}

    }
    
    private enum FirstNames{
	
	ADAM ("Adam"),
	FELIX ("Felix"),
	HENRY ("Henry"),
	JACK ("Jack"),
	JACOB ("Jacob"),
	LEO ("Leo"),
	LUKE ("Luke"),
	MICHAEL ("Michael"),
	NATHAN ("Nathan"),
	THOMAS ("Thomas"),
	WILLIAM ("William"),
	ALEXANDRA ("Alexandra"),
	EMILY ("Emily"),
	MIA ("Mia"),
	NAOMI ("Naomi"),
	KIRA ("Kira"),
	IRIS ("Iris"),
	VICTORIA ("Victoria"),
	VIVIAN ("Vivian"),
	OLIVIA ("Olivia"); 
	
	private String title;

	FirstNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}

    }
    
    private enum LastNames{
	
	ANDERSON ("Anderson"),
	BROOKS ("Brooks"),
	CLARK ("Clark"),
	COLLINS ("Collins"),
	DAVIS ("Davis"),
	EDWARDS ("Edwards"),
	EVANS ("Evans"),
	FISHER ("Fisher"),
	FOSTER ("Foster"),
	GARCIA ("Garcia"),
	GREEN ("Green"),
	HARRIS ("Harris"),
	HOWARD ("Howard"),
	LEWIS ("Lewis"),
	MARTIN ("Martin"),
	MITCHELL ("Mitchell"),
	PHILLIPS ("Phillips"),
	WILLIAMS ("Williams"),
	WILSON ("Wilson"),
	HOUSE ("House");
	
	private String title;

	LastNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}
    }

}
