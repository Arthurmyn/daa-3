// src/main/java/algorithms/JSONHandler.java
package algorithms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public final class JSONHandler {

    public static List<EdgeWeightedGraph> readInputGraphs(String path) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Map<String,Object> root = om.readValue(new File(path), new TypeReference<>() {});
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> graphs = (List<Map<String,Object>>) root.get("graphs");

        List<EdgeWeightedGraph> out = new ArrayList<>();
        for (Map<String,Object> g : graphs) {
            @SuppressWarnings("unchecked")
            List<String> nodes = (List<String>) g.get("nodes");
            Map<String,Integer> id = new HashMap<>();
            for (int i=0;i<nodes.size();i++) id.put(nodes.get(i), i);
            EdgeWeightedGraph G = new EdgeWeightedGraph(nodes.size());

            @SuppressWarnings("unchecked")
            List<Map<String,Object>> edges = (List<Map<String,Object>>) g.get("edges");
            for (Map<String,Object> e : edges) {
                int u = id.get(e.get("from").toString());
                int v = id.get(e.get("to").toString());
                double w = Double.parseDouble(e.get("weight").toString());
                G.addEdge(new Edge(u, v, w));
            }
            out.add(G);
        }
        return out;
    }

    public static void writeOutputJson(String path, List<Map<String,Object>> results) throws IOException {
        ObjectMapper om = new ObjectMapper();
        Files.createDirectories(Paths.get(path).getParent());
        om.writerWithDefaultPrettyPrinter()
                .writeValue(new File(path), Map.of("results", results));
    }

    public static void writeCsv(String path, List<CsvRow> rows) throws IOException {
        Files.createDirectories(Paths.get(path).getParent());
        StringBuilder sb = new StringBuilder("id,algorithm,vertices,total_mst_cost,execution_ms,operation_count\n");
        for (CsvRow r : rows) {
            sb.append(String.format(Locale.US, "%d,%s,%d,%.6f,%d,%d%n",
                    r.id, r.algorithm, r.vertices, r.totalMstCost, r.ms, r.ops));
        }
        Files.writeString(Paths.get(path), sb.toString());
    }

    public static final class CsvRow {
        public final int id;
        public final String algorithm;
        public final int vertices;
        public final double totalMstCost;
        public final long ms;
        public final long ops;
        public CsvRow(int id, String algorithm, int vertices, double totalMstCost, long ms, long ops) {
            this.id = id; this.algorithm = algorithm; this.vertices = vertices;
            this.totalMstCost = totalMstCost; this.ms = ms; this.ops = ops;
        }
    }
}