package todo.manager;


import todo.db.DBConnectionProvider;
import todo.model.Status;
import todo.model.Todo;
import todo.model.User;

import java.sql.*;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class DataManager {
    private Connection connection;

    public DataManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user(name, surname, email, password) Values (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }
    }

    public void addTodo(Todo todo) throws SQLException, ParseException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into todo(name, created_date, deadline, status, user_id ) Values (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, todo.getName());
        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setString(3, todo.getDeadline());
        preparedStatement.setString(4, todo.getStatus().name());
        preparedStatement.setInt(5, todo.getUserID());
        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            todo.setId(id);
        }
    }

    public List<Todo> getAllTodo() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo");
        List<Todo> myList = new LinkedList<Todo>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt("id"));
            todo.setName(resultSet.getString("name"));
            todo.setCreatedDate(resultSet.getTimestamp("created_date"));
            todo.setDeadline(resultSet.getString("deadline"));
            todo.setStatus(Status.valueOf(resultSet.getString("status")));
            todo.setUserID(resultSet.getInt("user_id"));
            myList.add(todo);
        }
        return myList;
    }

    public List<User> getUsers() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        List<User> users = new LinkedList<User>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            users.add(user);
        }
        return users;
    }

    public User getUser(String email) throws SQLException {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void printTodoByUserId(int id) {
        try {
            for (Todo todo : getAllTodo()) {
                if (todo.getUserID() == id) {
                    System.out.println(todo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getProgresList(int id) throws SQLException {
        for (Todo todo : getAllTodo()) {
            if (todo.getUserID() == id && todo.getStatus() == Status.IN_PROGRESS) {
                System.out.println(todo);
            }
        }
    }

    public void getFinishedList(int id) throws SQLException {
        for (Todo todo : getAllTodo()) {
            if (todo.getUserID() == id && todo.getStatus() == Status.FINISHED) {
                System.out.println(todo);
            }
        }
    }

    public void deletTodoById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    public void cangeTodos(int id, Status status) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todo SET status=? WHERE id=?");
        preparedStatement.setString(1, status.name());
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();
    }
}