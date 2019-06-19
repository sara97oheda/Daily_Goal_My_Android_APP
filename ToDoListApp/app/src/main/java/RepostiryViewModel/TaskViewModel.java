package RepostiryViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import CategoryEntityDao.CategoryEntity;
import GoalEntityDao.GoalTask;
import GraphEntityDao.GraphItems;
import TaskEntityDao.TaskToDo;
import androidx.annotation.NonNull;

/**
 * Created by sara on 08/02/2019.
 */

public class TaskViewModel extends AndroidViewModel {


    private Repositry taskRepository;
    private LiveData<List<TaskToDo>> allTasks;
    private LiveData<List<TaskToDo>> TasksByType;
    private int total;

    private Repositry categoryRepository;
    private LiveData<List<CategoryEntity>> allCategory;

    private Repositry graphRepository;

    private Repositry yarlyGoalRepositry;
    private LiveData<List<GoalTask>> allYarlyGoals;


    public TaskViewModel(@NonNull Application application) {
        super(application);

        taskRepository = new Repositry(application);
        allTasks = taskRepository.getAllToDoTasks();

        categoryRepository = new Repositry(application);
        allCategory = categoryRepository.getAllCategoryEntity();

        graphRepository = new Repositry(application);

        yarlyGoalRepositry = new Repositry(application);
        allYarlyGoals = yarlyGoalRepositry.getAllYearlyGoals();

    }

    public void insert(TaskToDo task) {
        taskRepository.insert(task);
    }

    public void update(TaskToDo task) {
        taskRepository.update(task);
    }

    public void delete(TaskToDo task) {
        taskRepository.delete(task);
    }

    public void deleteAllTask(String category) {
        taskRepository.deletAllToDoTaks(category);
    }

    public LiveData<List<TaskToDo>> getAllTasks() {
        return allTasks;
    }

    public int unfinshedTask(){
        int total = taskRepository.unFinshedTask();
        return total;
    }
    public int finshedTask(){
        int total = taskRepository.finshedTask();
        return total;
    }
    public int unfinshedTaskByCategory(String category){
        int total = taskRepository.unFinshedTaskByCatehory(category);
        return total;
    }
    public int finshedTaskByCategory(String category){
        int total = taskRepository.finshedTaskByCatehory(category);
        return total;
    }
    public int countTaskByCategory(String category){
        int total = taskRepository.GetTotalTask(category);
        return total;
    }
    ///////////////////HANDLIING CATEGORY/////////////////

    public void insert(CategoryEntity categoryEntity) {
        categoryRepository.insert(categoryEntity);
    }

    public void update(CategoryEntity categoryEntity) {
        categoryRepository.update(categoryEntity);
    }

    public void delete(CategoryEntity categoryEntity) {
        categoryRepository.delete(categoryEntity);
    }

    public void deleteAllCategory() {
        categoryRepository.deletAllCategory();
    }

    public LiveData<List<CategoryEntity>> getAllCategory() {
        return allCategory;
    }

    public LiveData<List<TaskToDo>> getTasksByType(String type) {
        TasksByType = taskRepository.getTaskByType(type);
        return TasksByType;
    }
    public int getToataTaskCategory(String category){
        total = taskRepository.GetTotalTask(category);

        return total;
    }

    //////////////////////////Handeling Graph////////////////////////////////
    public void insert(GraphItems graphItems) {
        graphRepository.insert(graphItems);
    }

    public void update(GraphItems graphItems) {
        graphRepository.update(graphItems);
    }

    public void delete(GraphItems graphItems) {
        graphRepository.delete(graphItems);
    }

    public String getGrphTitle(int id){
        String title = graphRepository.getGraphName(id);
        return title;
    }

    public int getGrphSize(int id){
        int total = graphRepository.getGraphSize(id);
        return  total;
    }

    //////////////////////handling yarly Goals///////////////////////////////
    public void insert(GoalTask goalTask) {
        yarlyGoalRepositry.insert(goalTask);
    }

    public void update(GoalTask goalTask)  {
        yarlyGoalRepositry.update(goalTask);
    }

    public void delete(GoalTask goalTask)  {
        yarlyGoalRepositry.delete(goalTask);
    }

   /* public void deleteAllCategory() {
        categoryRepository.deletAllCategory();
    }*/

    public LiveData<List<GoalTask>> getAllYarlyGoals() {
        return allYarlyGoals;
    }
}
