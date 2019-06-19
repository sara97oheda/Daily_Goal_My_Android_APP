package com.example.todolistapp.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.todolistapp.R;

import java.util.ArrayList;
import java.util.List;
import TaskEntityDao.TaskToDo;

/**
 * Created by sara on 08/02/2019.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder> {

    private List<TaskToDo> tasks = new ArrayList<>();
    private OnItemClickListener listener;


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.to_do_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position)
    {
        TaskToDo currentTask = tasks.get(position);

        holder.checkItem.setText(currentTask.getTaskName());
        holder.priorty.setText(String.valueOf(currentTask.getPriorty()));
        holder.time.setText(currentTask.getTime());
        holder.date.setText(currentTask.getDate());
        holder.description.setText(currentTask.getDescription());

        if (currentTask.isDone()){
            holder.checkItem.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<TaskToDo> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public TaskToDo getTaskAt(int position) {return tasks.get(position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkItem;
        private TextView priorty;
        private TextView time;
        private TextView date;
        private TextView description;

        public TaskViewHolder(View itemView) {
            super(itemView);

            checkItem = itemView.findViewById(R.id.checkBox);
            priorty = itemView.findViewById(R.id.text_view_priorty);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.text_view_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(tasks.get(position));

                    }
                }
            });
            checkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = getAdapterPosition();
                        listener.clickedTask(tasks.get(position));
                }
            } );
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TaskToDo taskToDo);
        boolean clickedTask(TaskToDo toDo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
