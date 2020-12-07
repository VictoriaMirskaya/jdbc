package ua.com.foxminded.db.init;

public enum LastNames {

    ANDERSON("Anderson"), 
    BROOKS("Brooks"), 
    CLARK("Clark"), 
    COLLINS("Collins"), 
    DAVIS("Davis"), 
    EDWARDS("Edwards"),
    EVANS("Evans"), 
    FISHER("Fisher"), 
    FOSTER("Foster"), 
    GARCIA("Garcia"), 
    GREEN("Green"), 
    HARRIS("Harris"),
    HOWARD("Howard"), 
    LEWIS("Lewis"), 
    MARTIN("Martin"), 
    MITCHELL("Mitchell"), 
    PHILLIPS("Phillips"),
    WILLIAMS("Williams"), 
    WILSON("Wilson"), 
    HOUSE("House");

    private String title;

    LastNames(String title) {
	this.title = title;
    }

    public String getTitle() {
	return title;
    }
}