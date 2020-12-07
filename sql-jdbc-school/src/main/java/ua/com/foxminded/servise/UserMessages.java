package ua.com.foxminded.servise;

public final class UserMessages {

    public static final String DELIMITER = "----------------------------------------------------";
    public static final String NEW_LINE = "\n";
    public static final String SYSTEM_ERROR = "There's been a system error. The program cannot be executed.";
    public static final String ERROR_FILE_READING_MASK = "There's been a error while reading the files: \n%s";
    public static final String ERROR_SCRIPT_EXECUTION_MASK = "There's been a error while work with the script: \n%s"; 
    public static final String ERROR_GETTING_DATA_FROM_DATABASE =  "There's been a error while getting data from the database";  
    public static final String ERROR_ADDING_DATA_TO_DATABASE =  "There's been a error while adding data to the database";
    public static final String ERROR_DELETING_DATA_FROM_DATABASE =  "There's been a error while deleting data from the database";

    private UserMessages() {}
}