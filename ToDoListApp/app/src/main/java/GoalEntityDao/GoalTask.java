package GoalEntityDao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "goal_task")
public class GoalTask {

    @PrimaryKey(autoGenerate = true)
    int goalId;

    String goalName;

    String goalDescription;

    int year;


    public GoalTask(String goalName, String goalDescription, int year){
        this.goalName = goalName;
        this.goalDescription = goalDescription;
        this.year =year;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        goalDescription = goalDescription;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
