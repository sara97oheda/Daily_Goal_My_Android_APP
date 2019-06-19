package CategoryEntityDao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sara on 21/02/2019.
 */

@Entity(tableName = "Category")
public class CategoryEntity {

    @PrimaryKey(autoGenerate = true)
    public int categoryId;

    public String categoryTitle;

    public String password;

    public int backgColo;

    private int nuberOfTask = 0;

    private boolean doneFlag;


    public CategoryEntity(String categoryTitle, String password, int backgColo, int nuberOfTask, boolean doneFlag) {
        this.categoryTitle = categoryTitle;
        this.password = password;
        this.backgColo = backgColo;
        this.nuberOfTask = nuberOfTask;
        this.doneFlag = doneFlag;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBackgColo() {
        return backgColo;
    }

    public void setBackgColo(int backgColo) {
        this.backgColo = backgColo;
    }

    public int getNuberOfTask() {
        return nuberOfTask;
    }

    public void setNuberOfTask(int nuberOfTask) {
        this.nuberOfTask = nuberOfTask;
    }

    public boolean isDoneFlag() {
        return doneFlag;
    }

    public void setDoneFlag(boolean doneFlag) {
        this.doneFlag = doneFlag;
    }
}
