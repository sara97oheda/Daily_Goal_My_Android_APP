package com.example.todolistapp.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todolistapp.R;
import com.ramotion.foldingcell.FoldingCell;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import GoalEntityDao.GoalTask;
import androidx.annotation.NonNull;

/**
 * Created by sara on 07/03/2019.
 */

public class GoalAdapter  extends RecyclerView.Adapter<GoalAdapter.GoalHolder>{
    private OnItemClickListener listener;
    private OnItemLongClickListener Longlistener;
    public int foldingCellPosition = -1;
    public int open = 1;

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;


    private List<GoalTask> goalTasks = new ArrayList<>();

    @NonNull
    @Override
    public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.yearly_goal_item, parent, false);
        return new GoalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHolder holder, int position) {

        GoalTask currentGoal = goalTasks.get(position);
//            holder.fc.unfold(true);
            holder.textViewTitle.setText(String.valueOf(currentGoal.getYear()));
            holder.textViewGoalItems.setText(currentGoal.getGoalDescription());
    }

    @Override
    public int getItemCount() {
        return goalTasks.size();
    }

    public void setGoalTasks(List<GoalTask> goalTasks) {
        this.goalTasks = goalTasks;
        notifyDataSetChanged();
    }

    class GoalHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewGoalItems;
        private FoldingCell fc;


        public GoalHolder(final View itemView) {

            super(itemView);
            View v = itemView;
            textViewTitle = itemView.findViewById(R.id.cell_year_txt);
            textViewGoalItems = itemView.findViewById(R.id.cell_goals_items);
            fc = itemView.findViewById(R.id.folding_cell);


            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String yearText = textViewTitle.getText().toString();
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(goalTasks.get(position), position, fc);
                    }
                }
            });

            fc.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String yearText = textViewTitle.getText().toString();
                    int position = getAdapterPosition();
                    if (Longlistener != null && position != RecyclerView.NO_POSITION) {
                        Longlistener.onItemLongClick(goalTasks.get(position), yearText, view);
                    }
                    return false;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(GoalTask goalTask, int position, View v);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(GoalTask goalTask, String category, View view);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLOngClickListener(OnItemLongClickListener listener) {this.Longlistener = listener;}
}
