package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import ua.com.foxminded.AuthorizationData;
import ua.com.foxminded.domain.Group;

public class GroupDao implements Dao<Group> {

    @Override
    public Optional<Group> get(long id) {

	return null;
    }

    @Override
    public List<Group> getAll() {

	return null;
    }

    @Override
    public void save(Group t) {
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
    public void update(Group t, String[] params) {

    }

    @Override
    public void delete(Group t) {

    }

}
