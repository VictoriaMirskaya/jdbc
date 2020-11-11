package ua.com.foxminded.domain;

public class Student {

    private int groupId;
    private String firstName;
    private String lastName;

    public Student(String firstName, String lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
    }

    public int getGroupId() {
	return groupId;
    }

    public void setGroupId(int groupId) {
	this.groupId = groupId;
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

}
