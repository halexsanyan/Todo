package todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    private int id;
    private String name;
    private Date createdDate;
    private String deadline;
    private Status status;
    private int userID;

    public Todo(String name,String deadline, Status status, int usetID) {
        this.name = name;
       // this.createdDate = createdDate;
        this.deadline = deadline;
        this.status = status;
        this.userID = usetID;
    }
}

