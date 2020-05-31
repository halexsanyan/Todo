package todo;

import todo.commands.Commands;
import todo.manager.UserManager;
import todo.manager.TodoManager;
import todo.model.Status;
import todo.model.Todo;
import todo.model.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class Main implements Commands {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final UserManager USER_MANAGER = new UserManager();
    private static final TodoManager TODO_MANAGER = new TodoManager();
    private static User currentUser = null;

    public static void main(String[] args) {

        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("Invalid command!");

            }
        }
    }

    private static void registerUser() {
        System.out.println("Please input: name, surname, emai, password");
        try {
            String userDataStr = SCANNER.nextLine();
            String[] userData = userDataStr.split(",");
            User userFromManager = USER_MANAGER.getUser(userData[2]);
            if (userFromManager == null) {
                User user = new User(userData[0], userData[1], userData[2], userData[3]);
                USER_MANAGER.addUser(user);
                System.out.println("User was successfully added!");
            } else {
                System.out.println("User already exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loginUser() {
        System.out.println("Please input: email, password");
        String userDataStr = SCANNER.nextLine();
        try {
            String[] userData = userDataStr.split(",");
            User user = USER_MANAGER.getUser(userData[0]);
            if (user != null && user.getPassword().equals(userData[1])) {
                currentUser = user;
                loginSuccess();
            } else {
                System.out.println("Wrong emal or password");
            }
        } catch (Exception e) {
            System.out.println("Try again");
            loginUser();
        }

    }

    private static void loginSuccess() {
        System.out.println("Welcome " + currentUser.getName() + " " + currentUser.getSurname());
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case MY_LIST:
                    printMyList();
                    break;
                case MY_IN_PROGRESS_LIST:
                    printMyProgresList();
                    break;
                case MY_FINISHED_LIST:
                    printMyFinishedList();
                    break;
                case ADD_TODO:
                    addTodo();
                    break;
                case CHANGE_TODO_STATUS:
                    changeTodoStatus();
                    break;
                case DELETE_TODO_BY_ID:
                    deleteTodoByID();
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }
    }

    private static void printMyList() {
        TODO_MANAGER.printTodoByUserId(currentUser.getId());
    }


    private static void printMyProgresList() {
        try {
            TODO_MANAGER.getProgresList(currentUser.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printMyFinishedList() {

        try {
            TODO_MANAGER.getFinishedList(currentUser.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addTodo() {
        System.out.println("Please input: name, deadline(yy.mm.dd hh:mm:ss)");
        try {
            String todoDataStr = SCANNER.nextLine();
            String[] todoData = todoDataStr.split(",");
            Todo todo = new Todo(todoData[0], todoData[1],
                    Status.TODO, currentUser.getId());
            TODO_MANAGER.addTodo(todo);
            System.out.println("Todo was successfully added!");
        } catch (Exception e) {
            System.out.println("Wrong input");
        }
    }


    private static void changeTodoStatus() {
        try {
            TODO_MANAGER.printTodoByUserId(currentUser.getId());
            System.out.println("Please input: id, status " + Arrays.toString(Status.values()));
            String todoDataStr = SCANNER.nextLine();
            String[] todoData = todoDataStr.split(",");
            TODO_MANAGER.cangeTodos(Integer.parseInt(todoData[0]), Status.valueOf(todoData[1].toUpperCase()));
            System.out.println("change completed successfully!");
        } catch (Exception e) {
            System.out.println("Try again");
            changeTodoStatus();
        }
    }

    private static void deleteTodoByID() {
        try {
            TODO_MANAGER.printTodoByUserId(currentUser.getId());
            System.out.println("Please input: id ");
            String todoDataStr = SCANNER.nextLine();
            TODO_MANAGER.deletTodoById(Integer.parseInt(todoDataStr));
            System.out.println("todo successfully deleted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

