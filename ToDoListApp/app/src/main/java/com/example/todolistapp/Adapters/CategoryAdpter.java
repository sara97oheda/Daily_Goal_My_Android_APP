package com.example.todolistapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.todolistapp.R;
import java.util.ArrayList;
import java.util.List;
import CategoryEntityDao.CategoryEntity;


/**
 * Created by sara on 21/02/2019.
 */

public class CategoryAdpter extends RecyclerView.Adapter<CategoryAdpter.CategoryViewHolder> {

    private List<CategoryEntity> categoryEntity = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemLongClickListener Longlistener;
    String category;


    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);

        return new CategoryAdpter.CategoryViewHolder(itemView);}

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        CategoryEntity currentCategory = categoryEntity.get(position);

        holder.category.setText(currentCategory.getCategoryTitle());
        holder.category.setBackgroundColor(currentCategory.getBackgColo());
        holder.numberOfTasks.setText(String.valueOf(currentCategory.getNuberOfTask()));
        if (currentCategory.getPassword() != null){
            holder.lock.setImageResource(R.drawable.ic_lock);
        }else
            holder.lock.setImageResource(R.drawable.un_loack);

        if ((currentCategory.isDoneFlag())){
            holder.doneFlag.setImageResource(R.drawable.ic_check_circle);
        }
        else
            holder.doneFlag.setImageResource(R.drawable.ic_pause_circle);

    }

    @Override
    public int getItemCount() {
        return categoryEntity.size();
    }

    public void setCategoryEntity(List<CategoryEntity> categoryEntity) {
        this.categoryEntity = categoryEntity;
        notifyDataSetChanged();
    }


    public CategoryEntity getcategoryEntityAt(int position) {return categoryEntity.get(position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private TextView category;
        private ImageView lock;
        private ImageView doneFlag;
        private TextView numberOfTasks;

        public CategoryViewHolder(final View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.item_category);
            lock = itemView.findViewById(R.id.lock_view);
            doneFlag = itemView.findViewById(R.id.done_img);
            numberOfTasks = itemView.findViewById(R.id.total_tasks);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoryText = category.getText().toString();
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(categoryEntity.get(position), categoryText);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    String categoryText = category.getText().toString();
                    int position = getAdapterPosition();
                    if (Longlistener != null && position != RecyclerView.NO_POSITION) {
                        Longlistener.onItemLongClick(categoryEntity.get(position), categoryText, itemView);
                    }
                    return false;
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryEntity categoryEntity, String category);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(CategoryEntity categoryEntity, String category, View view);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLOngClickListener(OnItemLongClickListener listener) {this.Longlistener = listener;}

    public String getCategoryListener(String category){
        this.category = category;
        return this.category;
    }
}
