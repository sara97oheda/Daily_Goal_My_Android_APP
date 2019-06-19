package GoalEntityDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GoalDao {

    @Insert
    void insertGoal(GoalTask goalTask);

    @Update
    void updteGoal(GoalTask goalTask);

    @Delete
    void deleteGoal(GoalTask goalTask);

    @Query("DELETE FROM goal_task")
    void deleteAllGoals();

    @Query("SELECT * FROM goal_task")
    LiveData<List<GoalTask>> getAllGoals();
}
