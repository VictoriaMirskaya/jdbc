package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ua.com.foxminded.domain.Group;

public class GroupDao implements Dao<Group> {
    
    @Override
    public Group findElement(String condition, int parameterValue) {
	return null;	
    }
    
    @Override
    public Group findElement(String condition, String parameterValue) {
	return null;
    }

    @Override
    public List<Group> findList(String condition, int parameterValue) {
	List<Group> groups = new ArrayList<>();
	if (condition.equals("Find all groups with less or equals student count")) {
	    findGroupsWhithLessOrEqualsStudentCount(groups, parameterValue);
	}
	return groups;
    }

    @Override
    public List<Group> findList(String condition, String parameterValue) {
	List<Group> groups = new ArrayList<>();
	return groups;
    }
    
    private void findGroupsWhithLessOrEqualsStudentCount(List<Group> groups, int count) {
	String sql = "SELECT students.group_id, groups.group_name FROM students "
		+ "JOIN groups ON students.group_id = groups.group_id "
		+ "GROUP BY (students.group_id, groups.group_name) " 
		+ "HAVING count(students.student_id) <=" + count;
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    while (rs.next()) {
		groups.add(new Group(rs.getInt("group_id"), rs.getString("group_name")));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addElement(Group group) {
	String sql = "INSERT INTO groups (group_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, group.getName());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void addList(List<Group> groups) {
	String sql = "INSERT INTO groups (group_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Group group : groups) {
		statement.setString(1, group.getName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void deleteElement(Group group) {
	String sql = "DELETE FROM groups WHERE group_id = " + group.getId();
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void deleteList(List<Group> t) {
	// TODO Auto-generated method stub
	
    }

}
