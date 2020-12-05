package ua.com.foxminded.domain;

public enum FirstNames {

    ADAM("Adam"), FELIX("Felix"), HENRY("Henry"), JACK("Jack"), JACOB("Jacob"), LEO("Leo"), LUKE("Luke"),
    MICHAEL("Michael"), NATHAN("Nathan"), THOMAS("Thomas"), WILLIAM("William"), ALEXANDRA("Alexandra"), EMILY("Emily"),
    MIA("Mia"), NAOMI("Naomi"), KIRA("Kira"), IRIS("Iris"), VICTORIA("Victoria"), VIVIAN("Vivian"), OLIVIA("Olivia");

    private String title;

    FirstNames(String title) {
	this.title = title;
    }

    public String getTitle() {
	return title;
    }

}
