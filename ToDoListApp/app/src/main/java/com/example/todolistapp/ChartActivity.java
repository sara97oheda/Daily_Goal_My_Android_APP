package com.example.todolistapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import RepostiryViewModel.TaskViewModel;
import androidx.annotation.NonNull;

public class ChartActivity extends AppCompatActivity {
        private TaskViewModel viewModel;
        private BarChart barChart;
        private PieChart pieChart;
        private int finshedTask;
        private int unFinshedTask;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chart);

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


            viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

            finshedTask = viewModel.unfinshedTask();
            unFinshedTask = viewModel.finshedTask();

            barChart = (BarChart) findViewById(R.id.bar);
            pieChart = (PieChart) findViewById(R.id.graph);

            showPieChart();
        }

        @Override
        protected void onStart() {
            super.onStart();

            viewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
            finshedTask = viewModel.unfinshedTask();
            unFinshedTask = viewModel.finshedTask();

            showPieChart();

            Toast.makeText(ChartActivity.this, finshedTask+"/ "+unFinshedTask, Toast.LENGTH_LONG).show();
        }

        public void showPieChart() {
            Entry e = new Entry(1, 2, pieChart);
            List<PieEntry> entries = new ArrayList<>();

            entries.add(new PieEntry(finshedTask, "Unfinshed Task"));
            entries.add(new PieEntry(unFinshedTask, "Finshed Task"));


            PieDataSet set = new PieDataSet(entries,String.valueOf( R.string.task_preformance));
            set.setColors(new int[]{R.color.priorty2, R.color.colorPrimary}, ChartActivity.this);
            pieChart.animateXY(1000, 1000, Easing.EaseInOutSine);
            PieData data = new PieData(set);
            pieChart.setData(data);
            pieChart.animate();

            // data.addEntry(e, 2);
            set.notifyDataSetChanged();
            pieChart.invalidate(); // refresh
        }

        public void showBarChart() {
            Entry e = new Entry(1, 2, barChart);
            List<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(0f, finshedTask));
            entries.add(new BarEntry(1f, unFinshedTask));

            BarDataSet set = new BarDataSet(entries, "Bar Data Set");

            BarData data = new BarData(set);
            data.setBarWidth(0.9f); // set custom bar width
            barChart.setData(data);
            barChart.animateXY(1500, 1500, Easing.EaseInOutCirc);
            set.setColors(new int[]{R.color.priorty2, R.color.colorPrimary}, ChartActivity.this);
            barChart.setFitBars(true); // make the x-axis fit exactly all bars
            barChart.setVisibility(View.VISIBLE);// refresh
            barChart.setGridBackgroundColor(R.color.backGroundColor);
            barChart.animate();
            data.addEntry(e, 1);
            data.notifyDataChanged();
            barChart.invalidate();
        }

        public void viewBarChart(View view) {
            pieChart.setVisibility(View.INVISIBLE);
            showBarChart();
        }

        public void viewPieChart(View view) {
            barChart.setVisibility(View.INVISIBLE);
            pieChart.setVisibility(View.VISIBLE);
            showPieChart();
        }

        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_activity:
                        Intent category = new Intent(ChartActivity.this, TaskCategore.class);
                        startActivity(category);
                        return true;
                    case R.id.yearly_task_activity:
                        Intent goals = new Intent(ChartActivity.this, Homepage.class);
                        startActivity(goals);
                        return true;
                    case R.id.statstic_activity:


                }
                return false;
            }
        };


    }
