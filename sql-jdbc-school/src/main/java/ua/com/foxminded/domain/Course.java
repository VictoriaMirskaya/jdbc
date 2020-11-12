package ua.com.foxminded.domain;

public class Course {

    private int id;
    private String name;
    private String description;
   
    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public Course(String name) {
	this.name = name;
    }
    
    public Course(String name, String description) {
	this(name);
	this.description = description;
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
    
}
