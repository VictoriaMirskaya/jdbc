package ua.com.foxminded.dao;

import java.util.List;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TestDataGenerator {
    
    public void createTestData() {
//	DataReader dataReader = new FilesDataReader();
//	List<Student> students = dataReader.loadStudents();
//	List<Group> groups = dataReader.loadGroups();
//	List<Course> courses = dataReader.loadCourses();
//	createGroupsData(groups);
//	createCoursesData(courses);
//	createStudentsData(students, groups);
//	assignStudentsToCourses(students, courses);
    }

    private enum GroupTestData{
	
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

	GroupTestData(String title) {
	       this.title = title;
	   }

	   public String getTitle() {
	       return title;
	   }

	   @Override
	   public String toString() {
	       return "Group{" +
	               "title='" + title + '\'' +
	               '}';
	   }
    }
    
    private enum CourseTestData{
	
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

	CourseTestData(String title) {
	       this.title = title;
	   }

	   public String getTitle() {
	       return title;
	   }

	   @Override
	   public String toString() {
	       return "Course{" +
	               "title='" + title + '\'' +
	               '}';
	   }
    }
    
    private enum FirstNameTestData{
	
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

	FirstNameTestData(String title) {
	       this.title = title;
	   }

	   public String getTitle() {
	       return title;
	   }

	   @Override
	   public String toString() {
	       return "First name{" +
	               "title='" + title + '\'' +
	               '}';
	   }
    }
    
    private enum LastNameTestData{
	
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

	LastNameTestData(String title) {
	       this.title = title;
	   }

	   public String getTitle() {
	       return title;
	   }

	   @Override
	   public String toString() {
	       return "First name{" +
	               "title='" + title + '\'' +
	               '}';
	   }
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
