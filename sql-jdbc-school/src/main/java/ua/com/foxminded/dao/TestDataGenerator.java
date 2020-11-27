package ua.com.foxminded.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TestDataGenerator {
    
    private final Random random = new Random();
      
    public void generateTestData(Dao<Course> courseDao, Dao<Group> groupDao, Dao<Student> studentDao) {
	generateGroups(groupDao);
	generateCourses(courseDao);
	generateStudents(studentDao);
	assignStudentsToGroup(studentDao, groupDao);	
	assignStudentsToCourses(studentDao, courseDao);		
    }

    private void generateGroups(Dao<Group> groupDao) {
	List<GroupNames> groupsNames = Arrays.asList(GroupNames.values());
	List<Group> groups = new ArrayList<>();
	for (GroupNames groupNames : groupsNames) {
	    groups.add(new Group(groupNames.getTitle()));
	}
	groupDao.addAll(groups);
    }

    private void generateCourses(Dao<Course> courseDao) {
	List<CourseNames> coursesNames = Arrays.asList(CourseNames.values());
	List<Course> courses = new ArrayList<>();
	for (CourseNames courseNames : coursesNames) {
	    courses.add(new Course(courseNames.getTitle()));
	}
	courseDao.addAll(courses);
    }
    
    private void generateStudents(Dao<Student> studentDao){
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
     
    private void assignStudentsToGroup(Dao<Student> studentDao, Dao<Group> groupDao) {
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
	((StudentDao)studentDao).assignStudentsToGroup(students);
    }

    private void assignStudentsToCourses(Dao<Student> studentDao, Dao<Course> courseDao) {
	List<Student> students = studentDao.selectAll();
	List<Course> courses = courseDao.selectAll();
	int courseQuantity;
	int index;
	List<Course> studentCources;
	for (Student student : students) {
	    courseQuantity = calculateCourseQuantity();
	    studentCources = new ArrayList<>();
	    for(int i = 1; i <= courseQuantity; i ++) {
		index = generateRandomCourseIndex();
		if (!studentCources.contains(courses.get(index))) {
		    studentCources.add(courses.get(index));
		}
	    }
	    student.setCources(studentCources);
	}
	((StudentDao)studentDao).assignStudentsToCourses(students);
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
