package ua.com.foxminded.dao;

import java.util.List;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public interface DataReader {
    
    List<Student> loadStudents();
    
    List<Course> loadCourses();
    
    List<Group> loadGroups();

}
