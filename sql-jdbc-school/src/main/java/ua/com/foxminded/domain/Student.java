package ua.com.foxminded.domain;

import java.util.List;

public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private Group group;
    private List<Course> courses;

    public Student(String firstName, String lastName) {
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

    public List<Course> getCourses() {
	return courses;
    }

    public void setCourses(List<Course> courses) {
	this.courses = courses;
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + id;
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Student other = (Student) obj;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (id != other.id)
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	return true;
    }

    @Override
    public String toString() {      
        return "\n{id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName +"}";
    }
}
