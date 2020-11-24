package ua.com.foxminded.domain;

import java.util.List;

public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private Group group;
    private List<Course> cources;

    public Student(String firstName, String lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
    }
    
    public Student(int id, String firstName, String lastName) {
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public Group getGroup() {
	return group;
    }

    public void setGroup(Group group) {
	this.group = group;
    }

    public List<Course> getCources() {
	return cources;
    }

    public void setCources(List<Course> cources) {
	this.cources = cources;
    }

}
