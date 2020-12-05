package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

class GroupDaoTest {

    GroupDao groupDao = new GroupDao();
    StudentDao studentDao = new StudentDao();
    TestTools testTools = new TestTools();
    
    @BeforeEach
    void init() throws IOException, SQLException {
	testTools.createTables();
	groupDao.addAll(testTools.generateGroups(5));
    }

    @Test
    void selectAll_ShouldReturnListGroups() throws SQLException, IOException {	
	assertIterableEquals(testTools.generateGroups(5), groupDao.selectAll());	
    }

    @Test
    void findById_ShouldReturnCourseById() throws SQLException, IOException {
	Group expected = new Group("MO-15");
	expected.setId(1);
	assertEquals(expected, groupDao.findById(1));	
    }
    
    @Test
    void deleteById_ShouldDeleteGroupById() throws SQLException, IOException {
	int countBeforeDelete = groupDao.selectAll().size(); 
	assertNotNull(groupDao.findById(1));
	groupDao.deleteById(1);
	assertNull(groupDao.findById(1));
	int countAfterDelete = groupDao.selectAll().size(); 
	assertEquals(countBeforeDelete - 1, countAfterDelete);
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturnListGroups() throws SQLException, IOException {
	studentDao.addAll(testTools.generateStudents(20));
	List<Group> groups = groupDao.selectAll();
	List<Student> students = studentDao.selectAll();
	for (Student student : students) {
	    student.setGroup(groups.get(0));
	}
	studentDao.assignStudentsToGroup(students);
	assertEquals(groups.get(0), groupDao.findById(1));
    }

}
