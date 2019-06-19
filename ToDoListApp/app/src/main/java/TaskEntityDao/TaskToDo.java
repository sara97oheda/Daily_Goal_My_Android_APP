package TaskEntityDao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sara on 08/02/2019.
 */

@Entity(tableName = "Task_to_do")

public class TaskToDo {
    @PrimaryKey(autoGenerate = true)
    int id;

    String taskName;

    String description;

    String date;

    String time;

    int priorty;

    boolean done;

    String category;



    public TaskToDo(String taskName, String description, String date, String time, int priorty, boolean done, String category) {
        this.taskName = taskName;
        this.description = description;
        this.date = date;
        this.time = time;
        this.priorty = priorty;
        this.done = done;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPriorty() {
        return priorty;
    }

    public void setPriorty(int priorty) {
        this.priorty = priorty;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
