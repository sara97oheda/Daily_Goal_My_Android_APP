package com.example.todolistapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistapp.Adapters.RecyclerAdapter;

import java.util.Calendar;
import java.util.List;

import CategoryEntityDao.CategoryEntity;
import RepostiryViewModel.TaskViewModel;
import TaskEntityDao.TaskToDo;
import androidx.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    public static final int CANCELED_NOTE_REQUEST = 3;

    private TaskViewModel viewModel;
    private TextView categoryName;
    private TextView currentDate;
    private String type;
    private View itemIsChecked;
    private int taskId;
    private boolean complete;
    int taskListSize;
    private String password;
    private boolean checked;
    private  int bgColor;
    private static int doneTask=0;
    private int total;
    private int workerTasks;
    private TaskToDo activeTask;
    public static final String DONE_TASKS =
            "com.example.sara.tolistappfinal.SEND_CATEGORY";

    public static final String TOTAL =
            "com.example.sara.tolistappfinal.ID";

    public static final String COMPLETE =
            "com.example.sara.tolistappfinal.COMPLETE";

    public static final String DONE_TYPE =
            "com.example.sara.tolistappfinal.SEND_TYPE";

    public static final String CATEGORY_PASSWORD =
            "com.example.sara.tolistappfinal.PASSWORD";

    public static final String CATEGORY_EXIT =
            "com.example.sara.tolistappfinal.TITLE";

    public static final String CATEGORY_ID =
            "com.example.sara.tolistappfinal.ID";

    public static final String CATEGORY_BGC =
            "com.example.sara.tolistappfinal.BGC";

    public static final int GET_CATEGORY = 6;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentDate();



        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_task);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewItem.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);

            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        categoryName = findViewById(R.id.txt_categry);


        final RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        type = intent.getStringExtra(DONE_TYPE);
        taskId = intent.getIntExtra(CATEGORY_ID, -1);
        password = intent.getStringExtra(CATEGORY_PASSWORD);
        bgColor = intent.getIntExtra(CATEGORY_BGC, -2);

        // Toast.makeText(this, password, Toast.LENGTH_SHORT).show();
        categoryName.setText(type);
        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        viewModel.getTasksByType(type).observe(this, new Observer<List<TaskToDo>>() {
            @Override
            public void onChanged(@Nullable List<TaskToDo> toDoTasks) {
                adapter.setTasks(toDoTasks);
                taskListSize=toDoTasks.size();

            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                total = viewModel.countTaskByCategory(type);

                Toast.makeText(MainActivity.this, "task deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TaskToDo taskToDo) {
                Intent intent = new Intent(MainActivity.this, AddNewItem.class);
                intent.putExtra(AddNewItem.EXTRA_ID, taskToDo.getId());
                intent.putExtra(AddNewItem.EXTRA_TITLE, taskToDo.getTaskName());
                intent.putExtra(AddNewItem.EXTRA_TIME, taskToDo.getTime());
                intent.putExtra(AddNewItem.EXTRA_DATE, taskToDo.getDate());
                intent.putExtra(AddNewItem.EXTRA_DESCRIPTION, taskToDo.getDescription());
                intent.putExtra(AddNewItem.EXTRA_PRIORITY, taskToDo.getPriorty());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }

            @Override
            public boolean clickedTask(TaskToDo toDo) {
                activeTask = toDo;
                return false;
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK ) {
            int id = data.getIntExtra(AddNewItem.EXTRA_ID, -1);

            if (id == -3) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddNewItem.EXTRA_TITLE);
            String description = data.getStringExtra(AddNewItem.EXTRA_DESCRIPTION);
            String time = data.getStringExtra(AddNewItem.EXTRA_TIME);
            String date = data.getStringExtra(AddNewItem.EXTRA_DATE);
            int priority = data.getIntExtra(AddNewItem.EXTRA_PRIORITY, 1);

            TaskToDo taskToDo = new TaskToDo(title, description, date, time, priority, false, type);
            viewModel.insert(taskToDo);

            Toast.makeText(this, "Note saved ", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddNewItem.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddNewItem.EXTRA_TITLE);
            String description = data.getStringExtra(AddNewItem.EXTRA_DESCRIPTION);
            String time = data.getStringExtra(AddNewItem.EXTRA_TIME);
            String date = data.getStringExtra(AddNewItem.EXTRA_DATE);
            int priority = data.getIntExtra(AddNewItem.EXTRA_PRIORITY, 1);

            TaskToDo taskToDo = new TaskToDo(title, description, date, time, priority, false, type);
            taskToDo.setId(id);
            viewModel.update(taskToDo);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        } else  {
            Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show();
        }

    }

    public void currentDate(){
        currentDate = findViewById(R.id.current_date);
        Calendar calendar = Calendar.getInstance();
        String current_date = java.text.DateFormat.getDateInstance(android.icu.text.DateFormat.FULL).format(calendar.getTime());

        currentDate.setText(current_date);
    }

    public void taskIsDone(View view) {
        CategoryEntity entity = null;
        workerTasks = viewModel.unfinshedTaskByCategory(type);

        boolean checked = ((CheckBox) view).isChecked();

        TaskToDo taskToDo = new TaskToDo(activeTask.getTaskName(), activeTask.getDescription(),
                activeTask.getDate(), activeTask.getTime(),
                activeTask.getPriorty(), checked, activeTask.getCategory());
        taskToDo.setId(activeTask.getId());
        viewModel.update(taskToDo);

        entity  = new CategoryEntity(type, password, bgColor, total, complete );
        entity.setCategoryId(taskId);
        viewModel.update(entity);
    }


    public void closeTasks(View view) {
        Intent intent = new Intent();

        workerTasks = viewModel.unfinshedTaskByCategory(type);
        int doneTask = viewModel.finshedTaskByCategory(type);
        total = viewModel.countTaskByCategory(type);

        boolean exit = true;
        CategoryEntity entity  = new CategoryEntity(type, password, bgColor, total, complete );
        entity.setCategoryId(taskId);

        if (doneTask==total && total != 0){
            complete = true;
        }
        intent.putExtra(CATEGORY_EXIT, exit);
        intent.putExtra(TOTAL, taskListSize);
        intent.putExtra(DONE_TASKS, doneTask);
        intent.putExtra(COMPLETE, complete);
        intent.putExtra(DONE_TYPE, type);

        setResult(RESULT_OK, intent);
        finish();


    }

    @Override
    protected void onStop() {
        super.onStop();
        complete = false;
        int doneTasks = viewModel.finshedTaskByCategory(type);
        total = viewModel.countTaskByCategory(type);


        if (doneTasks==total && total != 0){
            complete = true;
        }
        CategoryEntity entity  = new CategoryEntity(type, password, bgColor, total, complete );
        entity.setCategoryId(taskId);
        viewModel.update(entity);
        // Toast.makeText(this, "Note saved total="+total+" complete="+complete+"pasword= "+password, Toast.LENGTH_SHORT).show();

    }
}
