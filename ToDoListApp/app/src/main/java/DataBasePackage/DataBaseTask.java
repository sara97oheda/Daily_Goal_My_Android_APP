package DataBasePackage;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import CategoryEntityDao.CategoryDao;
import CategoryEntityDao.CategoryEntity;
import GoalEntityDao.GoalDao;
import GoalEntityDao.GoalTask;
import GraphEntityDao.GraphDao;
import GraphEntityDao.GraphItems;
import TaskEntityDao.TaskDao;
import TaskEntityDao.TaskToDo;
import androidx.annotation.NonNull;

/**
 * Created by sara on 08/02/2019.
 */

@Database(entities = {TaskToDo.class, GoalTask.class, CategoryEntity.class, GraphItems.class}, version = 14)
public abstract class DataBaseTask extends RoomDatabase {

    private static DataBaseTask instance;

    public abstract TaskDao taskDao();

    public abstract GoalDao goalDao();

    public abstract CategoryDao categoryDao();

    public abstract GraphDao graphDao();

    public static synchronized DataBaseTask getInstance(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataBaseTask.class, "ToDoTask_DataBase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBacks)
                    .build();
        }

        return instance;

    }


    private static RoomDatabase.Callback roomCallBacks = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulatedbAsyncTask(instance).execute();
        }
    };
    private static class PopulatedbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;
        private GoalDao goalDao;
        private  CategoryDao categoryDao;
        private  GraphDao graphDao;

        private PopulatedbAsyncTask(DataBaseTask database) {
            taskDao = database.taskDao();
            goalDao = database.goalDao();
            categoryDao = database.categoryDao();
            graphDao = database.graphDao();


        }

        @Override
        protected Void doInBackground(Void... voids) {

            taskDao.insertTaskToDo(new TaskToDo("Study", "Study Android", "12/2/2019","11:00",2, false,"study"));
            taskDao.insertTaskToDo(new TaskToDo("Study", "Study Android", "12/2/2019","11:00",7, false,"study"));



            return null;
        }
    }

}
