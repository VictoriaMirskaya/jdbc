package ua.com.foxminded.domain;

import java.util.List;

public class Group {

    private int id;
    private String name;
    private List<Student> students;

    public Group(int id, String name) {
	this.id = id;
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public List<Student> getStudents() {
	return students;
    }

    public void setStudents(List<Student> students) {
	this.students = students;
    }

}
