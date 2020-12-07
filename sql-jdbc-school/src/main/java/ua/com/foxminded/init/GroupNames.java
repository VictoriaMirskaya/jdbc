package ua.com.foxminded.init;

public enum GroupNames {

    MO15("MO-15"), 
    M020("MO-20"), 
    EC15("EC-15"), 
    EC20("EC-20"), 
    IT15("IT-15"), 
    IT20("IT-20"), 
    QA15("QA-15"),
    QA20("QA-20"), 
    JS15("JS-15"), 
    JS20("JS-20");

    private String title;

    GroupNames(String title) {
	this.title = title;
    }

    public String getTitle() {
	return title;
    }
}
