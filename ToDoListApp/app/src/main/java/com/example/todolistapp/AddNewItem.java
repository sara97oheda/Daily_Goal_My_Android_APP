package com.example.todolistapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistapp.Alarm.AlerRecevier;

import java.util.Calendar;

public class AddNewItem extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.sara.tolistappfinal.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.sara.tolistappfinal.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.sara.tolistappfinal.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.example.sara.tolistappfinal.EXTRA_PRIORITY";
    public static final String EXTRA_TIME =
            "com.example.sara.tolistappfinal.EXTRA_TIME";
    public static final String EXTRA_DATE=
            "com.example.sara.tolistappfinal.EXTRA_DATE";
    public static final String EXTRA_ALARM =
            "com.example.sara.tolistappfinal.EXTRA_ALARM";

    private TextView titleTextView;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewDate, textViewTime;
    private NumberPicker numberPickerPriority;
    private ImageView cancel;
    private ImageView done;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);



        editTextTitle =findViewById(R.id.edit_txt_task_name);
        editTextDescription = findViewById(R.id.edit_txt_description);
        textViewDate = findViewById(R.id.txt_view_date);
        textViewTime = findViewById(R.id.txt_view_time);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        titleTextView = findViewById(R.id.title);
        cancel = findViewById(R.id.cancel_btn);
        done = findViewById(R.id.done_btn);

        setDate();
        setTime();

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            titleTextView.setText("EDIT TASK ");
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            textViewTime.setText(intent.getStringExtra(EXTRA_TIME));
            textViewDate.setText(intent.getStringExtra(EXTRA_DATE));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        } else {
            titleTextView.setText("ADD A NEW TASK");
        }

    }


    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = textViewDate.getText().toString();
        String time = textViewTime.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DATE, date);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_TIME, time);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    private void setDate(){
        final Calendar calendar = Calendar.getInstance();
        textViewDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddNewItem.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = month +"/"+ day +"/"+ year;
                textViewDate.setText(date);

            }
        };

    }

    private void setTime(){
        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        AddNewItem.this,
                        R.style.Theme_AppCompat_DayNight_Dialog,
                        timeSetListener,
                        hour, minute,
                        true);
                dialog.show();
            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);


                startAlarm(calendar);

                String time = hour +":" + minute;
                textViewTime.setText(time);
            }
        };
    }

    private void  startAlarm(Calendar calendar) {


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlerRecevier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);


        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    public void saveChange(View view) {
        saveNote();
    }

    public void cancelProcesse(View view) {
        Intent data = new Intent();
        int id = -3;
        data.putExtra(EXTRA_ID, id);
        setResult(RESULT_OK, data);
        finish();


    }
}

