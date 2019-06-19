package CategoryEntityDao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by sara on 21/02/2019.
 */

@Dao
public interface CategoryDao {

    @Insert
    void insertCategory(CategoryEntity category);

    @Update
    void updateCategory(CategoryEntity category);

    @Delete
    void deleteCategory(CategoryEntity category);

    @Query("DELETE FROM Category")
    void deleteAllCategory();

    @Query("SELECT * FROM Category order by categoryId")
    LiveData<List<CategoryEntity>> getAllCategory();

    @Query("UPDATE Category SET password=:password WHERE categoryId=:id ")
    void setCategoryPassword(String password, int id);

}
