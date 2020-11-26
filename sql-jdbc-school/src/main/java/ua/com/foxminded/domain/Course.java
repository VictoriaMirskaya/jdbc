package ua.com.foxminded.domain;

import java.util.List;

public class Course {

    private int id;
    private String name;
    private String description;
    private List<Student> students;

    public Course(String name) {
   	this.name = name;
    }
    
    public Course(int id, String name) {
	this.id = id;
	this.name = name;
    }

    public Course(int id, String name, String description) {
	this(id, name);
	this.description = description;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Student> getStudents() {
	return students;
    }

    public void setStudents(List<Student> students) {
	this.students = students;
    }

    @Override
    public String toString() {      
        return "{id=" + this.id + ", name=" + this.name +"}";
    }
    
}
