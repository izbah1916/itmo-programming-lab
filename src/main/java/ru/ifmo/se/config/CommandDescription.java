package ru.ifmo.se.config;

/**
 * Configuration class holding constant strings for command descriptions.
 */
public class CommandDescription {
    public static final String HELP = "display help for available commands";
    public static final String INFO = "output information about the collection to the standard output (type, initialization date, number of elements, etc.)";
    public static final String SHOW = "display all elements of the collection in string representation";
    public static final String ADD = "add a new element to the collection";
    public static final String UPDATE = "update the value of an element in the collection whose id matches the specified one";
    public static final String REMOVE_BY_ID = "remove an element from the collection by its id";
    public static final String CLEAR = "clear the collection";
    public static final String SAVE = "save the collection to a file";
    public static final String EXECUTE_SCRIPT = "read and execute the script from the specified file. The script contains commands in the same format as the user enters interactively.";
    public static final String EXIT = "exit the program (without saving to a file)";
    public static final String HEAD = "display the first element of the collection";
    public static final String REMOVE_HEAD = "display and remove the first element of the collection";
    public static final String ADD_IF_MAX = "add a new element to the collection if its value exceeds that of the largest element in the collection";
    public static final String REMOVE_ALL_BY_POPULATION_DENSITY = "remove all elements from the collection where the populationDensity field equals the specified value";
    public static final String FILTER_LESS_THAN_POPULATION_DENSITY = "display elements where the populationDensity field is less than the specified value";
    public static final String PRINT_FIELD_DESCENDING_GOVERNMENT = "display the values of the government field of all elements in descending order";
}
