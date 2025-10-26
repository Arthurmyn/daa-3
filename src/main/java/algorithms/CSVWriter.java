package algorithms;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CSVWriter {
    public static void writeComparison(String path, List<Map<String, Object>> results) throws IOException {
        try (FileWriter w = new FileWriter(path, false);) {
            w.write("Graph,Vertices,Edges,Prim_time_ms,Kruskal_time_ms,Prim_cost,Kruskal_cost,Prim_comparisons,Kruskal_comparisons,Kruskal_unions\n");
            for (Map<String, Object> r : results) {
                Map<String, Object> stats = (Map<String, Object>) r.get("input_stats");
                Map<String, Object> prim = (Map<String, Object>) r.get("prim");
                Map<String, Object> kruskal = (Map<String, Object>) r.get("kruskal");
                w.write(r.get("graph_id") + "," +
                        stats.get("vertices") + "," +
                        stats.get("edges") + "," +
                        prim.get("execution_time_ms") + "," +
                        kruskal.get("execution_time_ms") + "," +
                        prim.get("total_cost") + "," +
                        kruskal.get("total_cost") + "," +
                        prim.get("comparison_count") + "," +
                        kruskal.get("comparison_count") + "," +
                        kruskal.get("union_count") + "\n");
            }
        }
    }
}