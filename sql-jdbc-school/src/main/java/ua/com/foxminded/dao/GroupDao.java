package ua.com.foxminded.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.domain.Group;
import ua.com.foxminded.servise.UserMessages;

public class GroupDao implements Dao<Group> {
    
    @Override
    public List<Group> selectAll() throws SQLException, IOException {
	List<Group> groups = new ArrayList<>();
	final String sql = "SELECT g.group_id, g.group_name FROM groups g";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Group group;
	    while (rs.next()) {
		group = new Group(rs.getString("group_name"));
		group.setId(rs.getInt("group_id"));
		groups.add(group);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return groups;
    }
    
    @Override
    public void addAll(List<Group> groups) throws SQLException, IOException {
	final String sql = "INSERT INTO groups (group_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Group group : groups) {
		statement.setString(1, group.getName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_ADDING_DATA_TO_DATABASE);
	}
    }

    @Override
    public Group findById(int groupId) throws SQLException, IOException {
	Group group = null;
	final String sql = "SELECT g.group_id, g.group_name, FROM groups g "
		         + "WHERE g.group_id = " + groupId;
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    while (rs.next()) {
		group = new Group(rs.getString("group_name"));
		group.setId(rs.getInt("group_id"));
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return group;
    }
    
    @Override
    public void deleteById(int id) throws SQLException, IOException {
	final String sql = "DELETE FROM groups WHERE group_id = ?";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setInt(1, id);
	    statement.execute();
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_DELETING_DATA_FROM_DATABASE);
	}
    }
    
    public List<Group> findGroupsWhithLessOrEqualsStudentCount(int count) throws SQLException, IOException {
	List<Group> groups = new ArrayList<>();
	final String sql = "SELECT students.group_id, groups.group_name FROM students "
		+ "JOIN groups ON students.group_id = groups.group_id "
		+ "GROUP BY (students.group_id, groups.group_name) " 
		+ "HAVING count(students.student_id) <=" + count;
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Group group;
	    while (rs.next()) {
		group = new Group(rs.getString("group_name"));
		group.setId(rs.getInt("group_id"));
		groups.add(group);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	return groups;
    }

}
