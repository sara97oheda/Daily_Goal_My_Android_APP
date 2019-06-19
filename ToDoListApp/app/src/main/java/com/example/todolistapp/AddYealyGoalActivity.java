package com.example.todolistapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddYealyGoalActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE =
            "com.codinginflow.architectureexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.codinginflow.architectureexample.EXTRA_DESCRIPTION";

    public static final String EXTRA_ID =
            "com.codinginflow.architectureexample.EXTRA_ID";
    private EditText editTextTitle;
    private EditText editTextItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_yealy_goal);

        editTextTitle = findViewById(R.id.edit_txt_goal_title);
        editTextItems = findViewById(R.id.edit_txt_goal_items);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            editTextTitle.setText(String.valueOf(intent.getIntExtra(EXTRA_TITLE, 2019)));
            editTextItems.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        }
    }




    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextItems.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);


        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


    public void saveChange(View view) {
        saveNote();
    }

    public void exit(View view) {
        Intent data = new Intent();


        int id =-3;
        data.putExtra(EXTRA_ID, id);


        setResult(RESULT_OK, data);
        finish();

    }
}
