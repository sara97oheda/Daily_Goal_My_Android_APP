package com.example.todolistapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.todolistapp.Adapters.GoalAdapter;
import com.ramotion.foldingcell.FoldingCell;

import java.util.List;

import GoalEntityDao.GoalTask;
import RepostiryViewModel.TaskViewModel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Homepage extends AppCompatActivity {

    private int id;
    private String goalItems;
    private int yrarGoal;

    public static final int ADD_GOAL_REQUEST = 1;
    public static final int EDIT_GOAL_REQUEST = 32;
    public static final int EXIT_GOAL_REQUEST = 42;
    private TaskViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_goal);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, AddYealyGoalActivity.class);
                startActivityForResult(intent, ADD_GOAL_REQUEST);

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        RecyclerView recyclerView = findViewById(R.id.recycler_yearl_goal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GoalAdapter adapter = new GoalAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        viewModel.getAllYarlyGoals().observe(this, new Observer<List<GoalTask>>() {
            @Override
            public void onChanged(@Nullable List<GoalTask> goalTasks) {
                adapter.setGoalTasks(goalTasks);
            }
        });

        adapter.setOnItemClickListener(new GoalAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(GoalTask goalTask, int position, View v) {
                FoldingCell cell = findViewById(R.id.folding_cell);
                cell.initialize(1000, Color.GRAY, 2);
                cell.initialize(30, 1000, Color.GRAY, 2);

                ((FoldingCell) v).toggle(false);

            }
        });


        adapter.setOnItemLOngClickListener(new GoalAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(GoalTask goalTask, String year, View itemView) {

                id = goalTask.getGoalId();
                yrarGoal = goalTask.getYear();
                goalItems = goalTask.getGoalDescription();
                registerForContextMenu(itemView);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        getMenuInflater().inflate(R.menu.option_menu_goals, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:
                updateGoal();

                return true;
            case R.id.option_2:
                DeleteGoal();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    private void DeleteGoal() {
        GoalTask goalTask = new GoalTask("yearly goal", goalItems, yrarGoal);
        goalTask.setGoalId(id);
        viewModel.delete(goalTask);
    }

    private void updateGoal() {

        Intent intent = new Intent(Homepage.this, AddYealyGoalActivity.class);
        intent.putExtra(AddYealyGoalActivity.EXTRA_TITLE, yrarGoal);
        intent.putExtra(AddYealyGoalActivity.EXTRA_DESCRIPTION, goalItems);
        intent.putExtra(AddYealyGoalActivity.EXTRA_ID, id);

        startActivityForResult(intent, EDIT_GOAL_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GOAL_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddYealyGoalActivity.EXTRA_ID, -1);

            if (id == -3) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddYealyGoalActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddYealyGoalActivity.EXTRA_DESCRIPTION);
            int year = Integer.parseInt(title);
            GoalTask goalTask = new GoalTask(title, description, year);
            viewModel.insert(goalTask);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_GOAL_REQUEST && resultCode == RESULT_OK) {
            int idItem = data.getIntExtra(AddYealyGoalActivity.EXTRA_ID, -1);

            if (idItem == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String titleItem = data.getStringExtra(AddYealyGoalActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddYealyGoalActivity.EXTRA_DESCRIPTION);
            int year = Integer.parseInt(titleItem);

            GoalTask goalTask = new GoalTask("yearly goal", goalItems, yrarGoal);
            goalTask.setGoalId(idItem);
            viewModel.update(goalTask);

            Toast.makeText(this, "Yearly Goal updated", Toast.LENGTH_SHORT).show();

        } else  {
            Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.main_activity:
                    Intent category = new Intent(Homepage.this, TaskCategore.class);
                    startActivity(category);
                    return true;

                case R.id.yearly_task_activity:

                    return true;

                case R.id.statstic_activity:
                    Intent charts = new Intent(Homepage.this, ChartActivity.class);
                    startActivity(charts);
                    return true;

            }
            return false;
        }
    };



}
