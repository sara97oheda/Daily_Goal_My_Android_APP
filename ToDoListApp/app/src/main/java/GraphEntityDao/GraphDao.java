package GraphEntityDao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface GraphDao {
    @Insert()
    void insertGraph(GraphItems graphItems);

    @Update()
    void UpdateGraph(GraphItems graphItems);

    @Delete()
    void deleteGraph(GraphItems graphItems);

    @Query("SELECT graphName FROM graph_entity WHERE graphItemId=:graphItemId")
    String getGraphTitle(int graphItemId);

    @Query("SELECT graphSize FROM graph_entity WHERE graphItemId=:graphItemId")
    int GraphSize(int graphItemId );


}

