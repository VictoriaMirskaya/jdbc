package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import ua.com.foxminded.domain.Course;

public class CourseDao implements Dao<Course> {

    @Override
    public List<Course> selectAll() {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public void addAll(List<Course> courses) {
	final String sql = "INSERT INTO courses (course_name) VALUES (?)";
	try (Connection connection = DBCPDataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(sql)) {
	    for (Course course : courses) {
		if (course.getId() == 0) {
		    statement.setString(1, course.getName());
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

}
