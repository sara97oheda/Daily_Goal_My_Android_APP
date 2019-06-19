package RepostiryViewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import CategoryEntityDao.CategoryDao;
import CategoryEntityDao.CategoryEntity;
import DataBasePackage.DataBaseTask;
import GoalEntityDao.GoalDao;
import GoalEntityDao.GoalTask;
import GraphEntityDao.GraphDao;
import GraphEntityDao.GraphItems;
import TaskEntityDao.TaskDao;
import TaskEntityDao.TaskToDo;

/**
 * Created by sara on 08/02/2019.
 */

public class Repositry {

    private TaskDao taskDao;
    public int total;

    public static int unfinshedTotal;
    public static int finshedTotal;

    public static int unfinshedTotalCtegory;
    public static int finshedTotalCategory;

    public static int totalTasks;

    private String graphTitle;
    private LiveData<List<TaskToDo>> allToDoTasks;
    private LiveData<List<TaskToDo>> typeToDoTasks;


    private CategoryDao categoryDao;
    private LiveData<List<CategoryEntity>> allCategoryEntity;

    private GraphDao graphDao;


    private GoalDao goalDao;
    private LiveData<List<GoalTask>> allGolTask;


    public Repositry(Application application)
    {
        DataBaseTask dataBase = DataBaseTask.getInstance(application);
        taskDao = dataBase.taskDao();
        allToDoTasks = taskDao.getAllToDoTasks();


        categoryDao = dataBase.categoryDao();
        allCategoryEntity = categoryDao.getAllCategory();

        graphDao = dataBase.graphDao();

        goalDao = dataBase.goalDao();
        allGolTask = goalDao.getAllGoals();
    }

    public void insert(TaskToDo task){
        new InsertToDoTaskAysncTask(taskDao).execute(task);
    }

    public void update(TaskToDo task){
        new UpdateToDoTaskAysncTask(taskDao).execute(task);
    }

    public void delete(TaskToDo task){
        new DeleteToDoTaskAysncTask(taskDao).execute(task);
    }

    public void deletAllToDoTaks(String category){
        new DeleteAllToDoTaskAysncTask(taskDao).execute(category);
    }

    public LiveData<List<TaskToDo>> getAllToDoTasks() {
        return allToDoTasks;
    }

    public LiveData<List<TaskToDo>>getTaskByType(String categoryType){
        typeToDoTasks = taskDao.getAllToDoTasksByType(categoryType);
        return typeToDoTasks;}

    public int GetTotalTask(String categoryType){
        new CountAllToDoTaskAysncTask(taskDao).execute(categoryType);
        return totalTasks;}

    public int unFinshedTask(){
        new unFinshedTaskAysncTask(taskDao).execute();
        return unfinshedTotal;}

    public int finshedTask(){
        new FinshedTaskAysncTask(taskDao).execute();
        return finshedTotal;}

    public int unFinshedTaskByCatehory(String category){
        new unFinshedByCatehoryTaskAysncTask(taskDao).execute(category);
        return unfinshedTotalCtegory;}

    public int finshedTaskByCatehory(String category){
        new FinshedByCatehoryTaskAysncTask(taskDao).execute(category);
        return finshedTotalCategory;}

    ////////////////////////MANAGING THE CATEGORY ENTITY///////////////////////
    public void insert(CategoryEntity categoryEntity){
        new InsertCategoryEntityAysncTask(categoryDao).execute(categoryEntity);

    }

    public void update(CategoryEntity categoryEntity){
        new UpdateCategoryEntityAysncTask(categoryDao).execute(categoryEntity);
    }

    public void delete(CategoryEntity categoryEntity){
        new DeleteCategoryEntityAysncTask(categoryDao).execute(categoryEntity);
    }

    public void deletAllCategory(){
        new DeleteAllCategoryEntityAysncTask(categoryDao).execute();
    }

    public LiveData<List<CategoryEntity>> getAllCategoryEntity() {
        return allCategoryEntity;
    }


    ////////////////////////MANAGING THE Graph ENTITY///////////////////////

    public void insert(GraphItems graphItems){
        new InsertGraphEntityAysncTask(graphDao).execute(graphItems);

    }

    public void update(GraphItems graphItems){
        new UpdateGraphEntityAysncTask(graphDao).execute(graphItems);
    }

    public void delete(GraphItems graphItems) {
        new DeleteGraphEntityAysncTask(graphDao).execute(graphItems);
    }

    public String getGraphName(int id){
        graphTitle = graphDao.getGraphTitle(id);
        return graphTitle;}

    public int getGraphSize(int id){
        total = graphDao.GraphSize(id);
        return total;}

    /////////////////////////////////YEARLY TASK ASYNK CALLBACK/////////////////////////////

    public void insert(GoalTask goalTask){
        new InsertYARLYGOALSAysncTask(goalDao).execute(goalTask);
    }

    public void update(GoalTask goalTask){
        new UpdateYARLYGOALSAysncTask(goalDao).execute(goalTask);
    }

    public void delete(GoalTask goalTask){
        new DeleteYARLYGOALSAysncTask(goalDao).execute(goalTask);
    }
   /* public void deletAllCategory(){
        new DeleteAllCategoryEntityAysncTask(categoryDao).execute();
    }*/

    public LiveData<List<GoalTask>> getAllYearlyGoals() {
        return allGolTask;
    }

    ///////////////////////////asyncTask For Tasks/////////////////////////////////////////

    private static class unFinshedTaskAysncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;

        private unFinshedTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            int total = taskDao.unFinshedTask();
            Repositry.unfinshedTotal=total;
            return null;
        }
    }

    private static class FinshedTaskAysncTask extends AsyncTask<Void, Void, Void> {
        private TaskDao taskDao;

        private FinshedTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            int total = taskDao.finshedTask();
            Repositry.finshedTotal=total;
            return null;
        }
    }

    private static class FinshedByCatehoryTaskAysncTask extends AsyncTask<String, Void, Void> {
        private TaskDao taskDao;

        private FinshedByCatehoryTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            int total = taskDao.finshedTaskByCategory(strings[0]);
            Repositry.finshedTotalCategory=total;
            return null;
        }
    }
    private static class unFinshedByCatehoryTaskAysncTask extends AsyncTask<String, Void, Void> {
        private TaskDao taskDao;

        private unFinshedByCatehoryTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }
        @Override
        protected Void doInBackground(String... strings) {
            int total = taskDao.unFinshedTaskByCatehory(strings[0]);
            Repositry.unfinshedTotalCtegory=total;
            return null;
        }
    }

    private static class InsertToDoTaskAysncTask extends AsyncTask<TaskToDo, Void, Void> {
        private TaskDao taskDao;

        private InsertToDoTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskToDo... taskToDos) {
            taskDao.insertTaskToDo(taskToDos[0]);
            return null;
        }
    }

    private static class UpdateToDoTaskAysncTask extends AsyncTask<TaskToDo, Void, Void> {
        private TaskDao taskDao;

        private UpdateToDoTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskToDo... taskToDos) {
            taskDao.updateTaskToDo(taskToDos[0]);
            return null;
        }
    }

    private static class DeleteToDoTaskAysncTask extends AsyncTask<TaskToDo, Void, Void> {

        private TaskDao taskDao;

        private DeleteToDoTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskToDo... taskToDos) {
            taskDao.deleteTaskToDo(taskToDos[0]);
            return null;
        }
    }
    private static class DeleteAllToDoTaskAysncTask extends AsyncTask<String, Void, Void> {
        private TaskDao taskDao;

        private DeleteAllToDoTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            taskDao.deleteAllTask(strings[0]);
            return null;
        }
    }

    private static class CountAllToDoTaskAysncTask extends AsyncTask<String, Void, Void> {
        private TaskDao taskDao;

        private CountAllToDoTaskAysncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            int total= taskDao.countItemCategory(strings[0]);
            Repositry.totalTasks = total;
            return null;
        }
    }


    ///////////////////////////asyncTask For Category/////////////////////////////////////////


    private static class InsertCategoryEntityAysncTask extends AsyncTask<CategoryEntity, Void, Void> {
        private CategoryDao categoryDao;

        private InsertCategoryEntityAysncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categoryEntities) {
            categoryDao.insertCategory(categoryEntities[0]);
            return null;
        }
    }

    private static class UpdateCategoryEntityAysncTask extends AsyncTask<CategoryEntity, Void, Void> {
        private CategoryDao categoryDao;

        private UpdateCategoryEntityAysncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categoryEntities) {
            categoryDao.updateCategory(categoryEntities[0]);
            return null;
        }
    }

    private static class DeleteCategoryEntityAysncTask extends AsyncTask<CategoryEntity, Void, Void> {

        private CategoryDao categoryDao;

        private DeleteCategoryEntityAysncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categoryEntities) {
            categoryDao.deleteCategory(categoryEntities[0]);
            return null;
        }
    }
    private static class DeleteAllCategoryEntityAysncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDao;

        private DeleteAllCategoryEntityAysncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.deleteAllCategory();
            return null;
        }
    }

    private static class getCategoryTaskAysncTask extends AsyncTask<String, Void, Void> {

        private TaskDao taskDao;
        String categoryType;

        private getCategoryTaskAysncTask(TaskDao taskDao, String categoryType) {
            this.taskDao = taskDao;
            this.categoryType = categoryType;
        }


        @Override
        protected Void doInBackground(String... strings) {
            taskDao.getAllToDoTasksByType(strings[0]);
            return null;
        }
    }

    ///////////////////////////asyncTask For Graph/////////////////////////////////////////

    private static class InsertGraphEntityAysncTask extends AsyncTask<GraphItems, Void, Void> {
        private GraphDao graphDao;

        private InsertGraphEntityAysncTask(GraphDao graphDao) {
            this.graphDao = graphDao;
        }

        @Override
        protected Void doInBackground(GraphItems... graphItems) {
            graphDao.insertGraph(graphItems[0]);
            return null;
        }
    }

    private static class UpdateGraphEntityAysncTask extends AsyncTask<GraphItems, Void, Void> {
        private GraphDao graphDao;

        private UpdateGraphEntityAysncTask(GraphDao graphDao) {
            this.graphDao = graphDao;
        }

        @Override
        protected Void doInBackground(GraphItems... graphItems) {
            graphDao.UpdateGraph(graphItems[0]);
            return null;
        }
    }

    private static class DeleteGraphEntityAysncTask extends AsyncTask<GraphItems, Void, Void> {

        private GraphDao graphDao;

        private DeleteGraphEntityAysncTask(GraphDao graphDao) {
            this.graphDao = graphDao;
            ;
        }

        @Override
        protected Void doInBackground(GraphItems... graphItems) {
            graphDao.deleteGraph(graphItems[0]);
            return null;
        }
    }

/////////////////////////////////YARLY GOALS////////////////////////////////////////////////

    private static class InsertYARLYGOALSAysncTask extends AsyncTask<GoalTask, Void, Void> {
        private GoalDao goalDao;

        private InsertYARLYGOALSAysncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(GoalTask... goalTasks) {
            goalDao.insertGoal(goalTasks[0]);
            return null;
        }
    }

    private static class UpdateYARLYGOALSAysncTask extends AsyncTask<GoalTask, Void, Void> {
        private GoalDao goalDao;

        private UpdateYARLYGOALSAysncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(GoalTask... goalTasks) {
            goalDao.updteGoal(goalTasks[0]);
            return null;
        }
    }

    private static class DeleteYARLYGOALSAysncTask extends AsyncTask<GoalTask, Void, Void> {
        private GoalDao goalDao;

        private DeleteYARLYGOALSAysncTask(GoalDao goalDao) {
            {this.goalDao = goalDao;}
        }

        @Override
        protected Void doInBackground(GoalTask... goalTasks) {
            goalDao.deleteGoal(goalTasks[0]);
            return null;
        }
    }


}
