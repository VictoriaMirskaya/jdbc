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
    public List<Group> selectAll() {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public void addAll(List<Group> groups) {
	final String sql = "INSERT INTO groups (group_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Group group : groups) {
		if (group.getId() == 0) {
		    statement.setString(1, group.getName());
		    statement.addBatch();
		}
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void deleteById(int id) {

    }
    
    public List<Group> findGroupsWhithLessOrEqualsStudentCount(int count) {
	List<Group> groups = new ArrayList<>();
	final String sql = "SELECT students.group_id, groups.group_name FROM students "
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
	return groups;
    }

}
