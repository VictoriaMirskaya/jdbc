package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;
import ua.com.foxminded.utils.TestTools;

class GroupDaoTest {

    GroupDao groupDao = new GroupDao();
    StudentDao studentDao = new StudentDao();
    TestTools testTools = new TestTools();
    
    @BeforeEach
    void init() throws IOException, SQLException {
	testTools.createTables();
    }

    @Test
    void selectAll_ShouldReturnListGroups_WhenGroupsExist() throws SQLException, IOException {	
	groupDao.addAll(testTools.generateGroups(5));
	assertIterableEquals(testTools.generateGroups(5), groupDao.selectAll());	
    }
    
    @Test
    void selectAll_ShouldReturnEmptyList_WhenGroupsNotExist() throws SQLException, IOException {	
	assertIterableEquals(new ArrayList<Group>(), groupDao.selectAll());	
    }

    @Test
    void findById_ShouldReturnGroupById_WhenGroupExist() throws SQLException, IOException {
	groupDao.addAll(testTools.generateGroups(5));
	Group expected = new Group("MO-15");
	expected.setId(1);
	assertEquals(expected, groupDao.findById(1));	
    }
    
    @Test
    void findById_ShouldReturnNull_WhenGroupNotExist() throws SQLException, IOException {
	assertNull(groupDao.findById(15));	
    }
    
    @Test
    void deleteById_ShouldDeleteGroupById() throws SQLException, IOException {
	groupDao.addAll(testTools.generateGroups(5));
	int countBeforeDelete = groupDao.selectAll().size(); 
	assertNotNull(groupDao.findById(1));
	groupDao.deleteById(1);
	assertNull(groupDao.findById(1));
	int countAfterDelete = groupDao.selectAll().size(); 
	assertEquals(countBeforeDelete - 1, countAfterDelete);
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturnListGroups_WhenGroupsExist() throws SQLException, IOException {
	groupDao.addAll(testTools.generateGroups(1));
	studentDao.addAll(testTools.generateStudents(20));
	List<Group> groups = groupDao.selectAll();
	List<Student> students = studentDao.selectAll();
	for (Student student : students) {
	    student.setGroup(groups.get(0));
	}
	studentDao.assignStudentsToGroup(students);
	assertEquals(groups, groupDao.findGroupsWhithLessOrEqualsStudentCount(20));
    }
    
    @Test
    void findGroupsWhithLessOrEqualsStudentCount_ShouldReturnEmpty_WhenGroupsNotExist() throws SQLException, IOException {
	assertEquals(new ArrayList<Group>(), groupDao.findGroupsWhithLessOrEqualsStudentCount(20));
    }

}
