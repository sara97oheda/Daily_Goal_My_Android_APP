package GraphEntityDao;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "graph_entity")
public class GraphItems {

    @PrimaryKey(autoGenerate = true)
    public int graphItemId;

    String graphName;

    int graphSize;

    public GraphItems(String graphName, int graphSize) {
        this.graphName = graphName;
        this.graphSize = graphSize;
    }

    public int getGraphItemId() {
        return graphItemId;
    }

    public void setGraphItemId(int graphItemId) {
        this.graphItemId = graphItemId;
    }

    public String getCategoryName() {
        return graphName;
    }

    public void setCategoryName(String categoryName) {
        this.graphName = categoryName;
    }

    public int getGraphSize() {
        return graphSize;
    }

    public void setGraphSize(int graphSize) {
        this.graphSize = graphSize;
    }
}
