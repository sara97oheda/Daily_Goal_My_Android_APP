package TaskEntityDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTaskToDo(TaskToDo taskToDo);

    @Update
    void updateTaskToDo(TaskToDo taskToDo);

    @Delete
    void deleteTaskToDo(TaskToDo taskToDo);

    @Query("DELETE FROM Task_to_do  WHERE `category`=:category")
    void deleteAllTask(String category);

    @Query("SELECT * FROM Task_to_do order by priorty desc")
    LiveData<List<TaskToDo>> getAllToDoTasks();

    @Query("SELECT  count(*) as `counter` FROM `Task_to_do` WHERE `category`=:category")
    int countItemCategory(String category);

    @Query("SELECT * FROM Task_to_do Where category=:category order by priorty desc")
    LiveData<List<TaskToDo>> getAllToDoTasksByType(String category);


    @Query("SELECT  count(*) as `counter` FROM `Task_to_do` WHERE `done`=0")
    int unFinshedTask();

    @Query("SELECT  count(*) as `counter` FROM `Task_to_do` WHERE `done`=1")
    int finshedTask();

    @Query("SELECT  count(*) as `counter` FROM `Task_to_do` WHERE `done`=0 and `category`=:category")
    int unFinshedTaskByCatehory(String category);

    @Query("SELECT  count(*) as `counter` FROM `Task_to_do` WHERE `done`=1 and `category`=:category")
    int finshedTaskByCategory(String category);
}
