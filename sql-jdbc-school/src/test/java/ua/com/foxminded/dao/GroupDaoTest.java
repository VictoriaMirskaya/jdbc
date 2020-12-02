package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.UserMessages;
import ua.com.foxminded.domain.Group;

class GroupDaoTest {

    private static Dao<Group> groupDao = new GroupDao();
    private static List<Group> groups;

    @BeforeAll
    public static void init() {
	List<GroupNames> groupsNames = Arrays.asList(GroupNames.values());
	groups = new ArrayList<>();
	Group group;
	for (int i = 1; i <= 10; i++) {
	    group = new Group(groupsNames.get(i - 1).getTitle());
	    group.setId(i);
	    groups.add(group);
	}	
    }
    
    @BeforeEach
    public void createTables() throws IOException, SQLException {
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement()) {
	    statement.execute("DROP TABLE IF EXISTS groups");
	    statement.execute("CREATE TABLE groups("
	    		    + "group_id INT GENERATED ALWAYS AS IDENTITY, "
	    		    + "group_name VARCHAR(255) NOT NULL, "
	    		    + "PRIMARY KEY(group_id)) ");
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }   
    
    @Test
    void addAllShouldAddListGroups() throws SQLException, IOException {
	groupDao.addAll(groups);
	List<Group> actual = new ArrayList<>();
	final String sql = "SELECT * FROM groups";
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Group group;
	    while (rs.next()) {
		group = new Group(rs.getString("group_name"));
		group.setId(rs.getInt("group_id"));
		actual.add(group);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	assertIterableEquals(groups, actual);
    }
    
    @Test
    void selectAllShouldReturnListGroups() throws SQLException, IOException {
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
	List<Group> actual = groupDao.selectAll();
	assertIterableEquals(groups, actual);
    }
    
    @Test
    void deleteByIdShouldDeleteGroupById() throws SQLException, IOException {
	String sql = "INSERT INTO groups (group_name) VALUES (?)";
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
	groupDao.deleteById(1);
	sql = "SELECT * FROM groups";
	List<Group> actual = new ArrayList<>();
	try (Connection connection = DBCPDataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql)) {
	    Group group;
	    while (rs.next()) {
		group = new Group(rs.getString("group_name"));
		group.setId(rs.getInt("group_id"));
		actual.add(group);
	    }
	} catch (SQLException e) {
	    throw new SQLException(UserMessages.ERROR_GETTING_DATA_FROM_DATABASE);
	}
	assertEquals(groups.size() - 1, actual.size());
    }

    private enum GroupNames {

	MO15("MO-15"), M020("MO-20"), EC15("EC-15"), EC20("EC-20"), IT15("IT-15"), IT20("IT-20"), QA15("QA-15"),
	QA20("QA-20"), JS15("JS-15"), JS20("JS-20");

	private String title;

	GroupNames(String title) {
	    this.title = title;
	}

	public String getTitle() {
	    return title;
	}
    }

}
