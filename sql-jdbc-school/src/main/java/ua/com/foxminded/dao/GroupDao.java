package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ua.com.foxminded.AuthorizationData;
import ua.com.foxminded.domain.Group;

public class GroupDao implements Dao<Group> {

    @Override
    public void saveElement(Group t) {
	String sql = "INSERT INTO groups (group_name) VALUES (?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.setString(1, t.getName());
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void saveList(List<Group> t) {
	String sql = "INSERT INTO groups (group_name) VALUES (?)";
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Group group : t) {
		statement.setString(1, group.getName());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void delete(int id) {
	String sql = "DELETE FROM groups WHERE group_id = " + id;
	try (Connection connection = DriverManager.getConnection(AuthorizationData.URL, AuthorizationData.USER,
		AuthorizationData.PASSWORD); PreparedStatement statement = connection.prepareStatement(sql)) {
	    statement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

}
