package com.example.todolistapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistapp.Adapters.CategoryAdpter;
import com.example.todolistapp.Dailogs.OpenCategoryDailog;
import com.example.todolistapp.Dailogs.SetPasswordDialog;
import com.example.todolistapp.Dailogs.UnsetPasswordDailog;
import com.example.todolistapp.Dailogs.categoryDailog;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.List;

import CategoryEntityDao.CategoryEntity;
import RepostiryViewModel.TaskViewModel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaskCategore extends AppCompatActivity
        implements categoryDailog.ExampleDialogListener,
        SetPasswordDialog.SetPasswordDialogListener,
        OpenCategoryDailog.OpenDailogListner,
        UnsetPasswordDailog.unSetPasswordDialogListener {


    public static final int OPEN_CATEGORY = 2;

    private RecyclerView recyclerCategory;
    private TaskViewModel viewModel;
    private String title;
    private String type;
    private TextView categoryItem;
    private boolean complete;
    private String name;
    private String typeCat;
    private int finshTasks;
    private int id;
    private int TotalTasks;
    private int currentId;
    private Animation frombottom;
    private String myPass;
    private int numberOftask;
    private  boolean doneFlag;
    private int colorbg;
    private int sendBackGroundColor;
    private int setBgcolor;
    private TextView totalItems;
    private String catPassword;
    private View imageLock;
    private String[] categoryTotal = new String[10];
    private int[] totalNumber;
    private int i = 0;
    private CategoryAdpter adapter;
    private BottomNavigationView navigation;
    private ImageView splashImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_categore);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //splashImage = findViewById(R.id.splach_bg);

        // startUpSplashScreen();



        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_gategore);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        categoryItem = findViewById(R.id.item_category);
        CardSliderLayoutManager sliderLayoutManager = new CardSliderLayoutManager(this);


        recyclerCategory = findViewById(R.id.recycler_category);
        recyclerCategory.setLayoutManager(sliderLayoutManager);
        recyclerCategory.setHasFixedSize(true);
        new CardSnapHelper().attachToRecyclerView(recyclerCategory);


        adapter = new CategoryAdpter();
        recyclerCategory.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        viewModel.getAllCategory().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> categoryEntities) {
                adapter.setCategoryEntity(categoryEntities);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                CategoryEntity entity;
                entity = adapter.getcategoryEntityAt(viewHolder.getAdapterPosition());
                viewModel.delete(adapter.getcategoryEntityAt(viewHolder.getAdapterPosition()));
                viewModel.deleteAllTask(entity.getCategoryTitle());

                Toast.makeText(TaskCategore.this, "Category deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerCategory);

        adapter.setOnItemClickListener(new CategoryAdpter.OnItemClickListener() {
            @Override
            public void onItemClick(CategoryEntity categoryEntity, String type) {
                typeCat = type;
                sendBackGroundColor =categoryEntity.getBackgColo();
                currentId = categoryEntity.getCategoryId();
                if (categoryEntity.getPassword() == null) {
                    Intent intent = new Intent(TaskCategore.this, MainActivity.class);
                    intent.putExtra(MainActivity.DONE_TYPE, typeCat);
                    intent.putExtra(MainActivity.CATEGORY_ID, currentId);
                    intent.putExtra(MainActivity.CATEGORY_BGC, sendBackGroundColor);
                    intent.putExtra(MainActivity.CATEGORY_PASSWORD, categoryEntity.getPassword());
                    startActivityForResult(intent, OPEN_CATEGORY);

                } else {
                    catPassword = categoryEntity.getPassword();
                    openCategoryDialog();

                }

            }
        });


        adapter.setOnItemLOngClickListener(new CategoryAdpter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(CategoryEntity categoryEntity, String category, View itemView) {
                name = category;
                imageLock = itemView;
                id = categoryEntity.getCategoryId();
                myPass = categoryEntity.getPassword();
                numberOftask = categoryEntity.getNuberOfTask();
                doneFlag = categoryEntity.isDoneFlag();
                colorbg = categoryEntity.getBackgColo();
                registerForContextMenu(itemView);
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.main_activity:
                    return true;

                case R.id.yearly_task_activity:
                    Intent goals = new Intent(TaskCategore.this, Homepage.class);
                    startActivity(goals);
                    return true;

                case R.id.statstic_activity:
                    Intent charts = new Intent(TaskCategore.this, ChartActivity.class);
                    startActivity(charts);
                    return true;
            }
            return false;
        }
    };

    public void openDialog() {
        categoryDailog dialog = new categoryDailog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void openPasswordDialog() {
        SetPasswordDialog dialog = new SetPasswordDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void openCategoryDialog() {
        OpenCategoryDailog dialog = new OpenCategoryDailog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void openUnsetPassDialog() {
        UnsetPasswordDailog dialog = new UnsetPasswordDailog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public String applyTexts(String _title, int color) {
        title = _title;
        categoryTotal[i++] = _title;

        if (title != " ") {
            if (color == 1) {
                setBgcolor = ContextCompat.getColor(TaskCategore.this, R.color.item1);
            }
            if (color == 2) {
                setBgcolor = ContextCompat.getColor(TaskCategore.this, R.color.item2);
            }
            if (color == 3) {
                setBgcolor = ContextCompat.getColor(TaskCategore.this, R.color.item3);
            }
            CategoryEntity categoryEntity = new CategoryEntity(title, null, setBgcolor, 0, false);
            viewModel.insert(categoryEntity);
            Toast.makeText(this, "Category saved", Toast.LENGTH_SHORT).show();

        }
        return _title;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        getMenuInflater().inflate(R.menu.option_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1:
                openPasswordDialog();

                return true;
            case R.id.option_2:
                openUnsetPassDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }


    }

    @Override
    public String applyPassword(String password) {
        CategoryEntity categoryEntity = new CategoryEntity(name, password, colorbg, numberOftask, doneFlag);
        categoryEntity.setCategoryId(id);
        viewModel.update(categoryEntity);
        // Toast.makeText(this, "catPassword set", Toast.LENGTH_SHORT).show();
        return password;
    }

    @Override
    public String interCategory(String password) {
        if (catPassword.equals(password)) {
            Intent intent = new Intent(TaskCategore.this, MainActivity.class);
            intent.putExtra(MainActivity.DONE_TYPE, typeCat);
            intent.putExtra(MainActivity.CATEGORY_ID, currentId);
            intent.putExtra(MainActivity.CATEGORY_BGC, sendBackGroundColor);
            intent.putExtra(MainActivity.CATEGORY_PASSWORD, password);
            startActivityForResult(intent, OPEN_CATEGORY);
            catPassword = null;
        }
        return null;
    }

    @Override
    public String unSetPassword(String unsePassword, String ennterPass) {
        if (myPass.equals(ennterPass)) {
            CategoryEntity categoryEntity = new CategoryEntity(name, unsePassword, colorbg, numberOftask, doneFlag);
            categoryEntity.setCategoryId(id);
            viewModel.update(categoryEntity);
            Toast.makeText(this, "Password Unset secsusefly", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(TaskCategore.this, "Passwrd Uncorrect", Toast.LENGTH_LONG).show();
        return unsePassword;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_CATEGORY && resultCode == RESULT_OK) {
            boolean exit = data.getBooleanExtra(MainActivity.CATEGORY_EXIT, false);
            if (exit){
                Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show();
                return;
            }
            TotalTasks = data.getIntExtra(MainActivity.TOTAL, 0);
            finshTasks = data.getIntExtra(MainActivity.DONE_TASKS,0);
            complete = data.getBooleanExtra(MainActivity.COMPLETE, false);
            type = data.getStringExtra(MainActivity.DONE_TYPE);

        }else  {
            Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show();
        }
    }


    public void startUpSplashScreen(){

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottim);
        recyclerCategory = findViewById(R.id.recycler_category);
        FloatingActionButton buttonAddNote = findViewById(R.id.btn_add_gategore);
        TextView title = findViewById(R.id.title_category);
        //  ImageView imageView = findViewById(R.id.splach_bg);


        // imageView.animate().translationY(-1300).setDuration(1000).setStartDelay(600);
        navigation.startAnimation(frombottom);
        recyclerCategory.startAnimation(frombottom);
        buttonAddNote.startAnimation(frombottom);
        title.startAnimation(frombottom);
    }


}

