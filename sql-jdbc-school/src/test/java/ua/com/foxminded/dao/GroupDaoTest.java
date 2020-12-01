package ua.com.foxminded.dao;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.domain.Group;

class GroupDaoTest {

    private static Dao<Group> groupDao = new GroupDao();

    @Test
    void selectAllShouldReturnListGroups() throws SQLException {
	List<GroupNames> groupsNames = Arrays.asList(GroupNames.values());
	List<Group> expected = new ArrayList<>();
	Group group;
	for (int i = 1; i <= 10; i++) {
	    group = new Group(groupsNames.get(i - 1).getTitle());
	    group.setId(i);
	    expected.add(group);
	}
	List<Group> actual = groupDao.selectAll();
	assertIterableEquals(expected, actual);
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
