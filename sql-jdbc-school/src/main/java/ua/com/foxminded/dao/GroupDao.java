package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ua.com.foxminded.domain.Group;

public class GroupDao implements Dao<Group> {

    @Override
    public void findElement(Group t) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void findList(List<Group> t) {
	// TODO Auto-generated method stub
	
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
