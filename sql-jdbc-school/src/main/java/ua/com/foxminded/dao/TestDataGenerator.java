package ua.com.foxminded.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TestDataGenerator {
    
    public static void generateTestData() {
	generateGroups();
	generateCourses();
	generateStudents();
	assignStudentsToCourses();
    }

    private static void generateGroups() {
	for (GroupNames groupName : Arrays.asList(GroupNames.values())) {
	    new GroupDao().save(new Group(groupName.getTitle()));
	}
    }

    private static void generateCourses() {
	for (CourseNames courseName : Arrays.asList(CourseNames.values())) {
	    new CourseDao().save(new Course(courseName.getTitle()));
	}
    }
    
    private static void generateStudents(){
	List<FirstNames> firstNamesExamples = Arrays.asList(FirstNames.values());	
	List<FirstNames> firstNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {
	    Collections.shuffle(firstNamesExamples);
	    firstNames.addAll(firstNamesExamples);
	}
	List<LastNames> lastNamesExamples = Arrays.asList(LastNames.values());	
	List<LastNames> lastNames = new ArrayList<>();
	for(int i = 0; i < 10; i++) {
	    Collections.shuffle(lastNamesExamples);
	    lastNames.addAll(lastNamesExamples);
	}
	List<Student> students = new ArrayList<>();
	
//	* 200 students. Take 20 first names and 20 last names and randomly combine them to generate students.
//	* Randomly assign students to groups. Each group could contain from 10 to 30 students. It is possible that some groups will be without students or students without groups
    }

    private static void assignStudentsToCourses() {
//	* Create relation MANY-TO-MANY between tables STUDENTS and COURSES. Randomly assign from 1 to 3 courses for each student	
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
