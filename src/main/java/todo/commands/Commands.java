package todo.commands;

public interface Commands {
    // main commands
    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;

    // login commands
    int LOGOUT = 0;
    int MY_LIST = 1;
    int MY_IN_PROGRESS_LIST = 2;
    int MY_FINISHED_LIST = 3;
    int ADD_TODO = 4;
    int CHANGE_TODO_STATUS = 5;
    int DELETE_TODO_BY_ID = 6;

    static void  printMainCommands() {
        System.out.println("Please input " + EXIT + " for EXIT");
        System.out.println("Please input " + LOGIN + " for LOGIN");
        System.out.println("Please input " + REGISTER + " for REGISTER");
    }
    static void printUserCommands() {
        System.out.println("Please input " + LOGOUT + " for LOGOUT");
        System.out.println("Please input " + MY_LIST + " for MY_LIST ");
        System.out.println("Please input " + MY_IN_PROGRESS_LIST + " for MY_IN_PROGRESS_LIST ");
        System.out.println("Please input " + MY_FINISHED_LIST + " for MY_FINISHED_LIST ");
        System.out.println("Please input " + ADD_TODO + " for ADD_TODO ");
        System.out.println("Please input " + CHANGE_TODO_STATUS + " for CHANGE_TODO_STATUS ");
        System.out.println("Please input " + DELETE_TODO_BY_ID + " for DELETE_TODO_BY_ID ");
    }
}
