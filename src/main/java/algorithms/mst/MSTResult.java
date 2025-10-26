// src/main/java/mst/MSTResult.java
package algorithms.mst;

import java.util.List;
import java.util.Map;

public class MSTResult {
    public int vertices;
    public int edges;
    public List<Map<String, Object>> mstEdges;
    public double totalCost;
    public double executionTimeMs;
    public long unionCount;
    public long comparisonCount;

    public MSTResult(int vertices, int edges,
                     List<Map<String, Object>> mstEdges,
                     double totalCost,
                     double executionTimeMs,
                     long unionCount,
                     long comparisonCount) {
        this.vertices = vertices;
        this.edges = edges;
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.executionTimeMs = executionTimeMs;
        this.unionCount = unionCount;
        this.comparisonCount = comparisonCount;
    }
}