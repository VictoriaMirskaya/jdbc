package ua.com.foxminded.servise;

public enum CourseNames {

    MATHEMATICS("Mathematics"), 
    BIOLOGY("Biology"), 
    ENGLISH("English"), 
    IT("Information Technology"),
    DATABASE("Database"), 
    HISTORY("History"), ECONOMY("Economy"), 
    ART("Art"), 
    DESIGN("Design"),
    ARCHITECTURE("Architecture");

    private String title;

    CourseNames(String title) {
	this.title = title;
    }

    public String getTitle() {
	return title;
    }

}
