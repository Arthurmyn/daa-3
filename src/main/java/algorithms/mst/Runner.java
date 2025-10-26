package algorithms.mst;

import algorithms.*;
import metrics.MetricsTracker;
import java.util.*;
import java.io.*;

public final class Runner {

    private static String edgeList(Iterable<Edge> it) {
        StringBuilder s = new StringBuilder();
        for (Edge e : it) s.append(e.toString()).append(";");
        return s.toString();
    }

    public static MSTResult runPrim(EdgeWeightedGraph G, List<String> nodes) {
        long t0 = System.nanoTime();
        MetricsTracker m = new MetricsTracker();
        PrimMST pr = new PrimMST(G, m);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        List<Map<String,Object>> edges = new ArrayList<>();
        for (Edge e : pr.edges()) {
            int v = e.either(), w = e.other(v);
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("from", v);
            map.put("to", w);
            map.put("weight", e.weight());
            edges.add(map);
        }
        return new MSTResult(G.V(), G.E(), edges, pr.weight(), ms, m.unions(), m.comparisons());
    }

    public static MSTResult runKruskal(EdgeWeightedGraph G, List<String> nodes) {
        long t0 = System.nanoTime();
        MetricsTracker m = new MetricsTracker();
        KruskalMST kr = new KruskalMST(G, m);
        long ms = (System.nanoTime() - t0) / 1_000_000;

        List<Map<String,Object>> edges = new ArrayList<>();
        for (Edge e : kr.edges()) {
            int v = e.either(), w = e.other(v);
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("from", v);
            map.put("to", w);
            map.put("weight", e.weight());
            edges.add(map);
        }
        return new MSTResult(G.V(), G.E(), edges, kr.weight(), ms, m.unions(), m.comparisons());
    }

    public static void main(String[] args) throws Exception {
        String inputPath  = (args.length > 0) ? args[0] : "data/ass_3_input_small.json";
        String outputJson = (args.length > 1) ? args[1] : "reports/output.json";
        String outputCsv  = (args.length > 2) ? args[2] : "reports/result.csv";

        List<EdgeWeightedGraph> graphs = JSONHandler.readInputGraphs(inputPath);
        File outDir = new File("reports/images");
        outDir.mkdirs();

        List<Map<String,Object>> jsonResults = new ArrayList<>();
        List<JSONHandler.CsvRow> csvRows = new ArrayList<>();

        for (int i = 0; i < graphs.size(); i++) {
            EdgeWeightedGraph G = graphs.get(i);
            int id = i + 1;

            MSTResult k = runKruskal(G, null);
            MSTResult p = runPrim(G, null);

            try {
                GraphVizExporter.writeDot(G, "reports/images/graph_" + id + ".dot");
                GraphVizExporter.writeDotWithMST(G, k.mstEdges, "reports/images/mst_kruskal_" + id + ".dot");
                GraphVizExporter.writeDotWithMST(G, p.mstEdges, "reports/images/mst_prim_" + id + ".dot");
            } catch (Exception ex) {
                System.err.println("GraphViz export failed: " + ex.getMessage());
            }

            Map<String,Object> entry = new LinkedHashMap<>();
            entry.put("graph_id", id);
            entry.put("vertices", G.V());
            entry.put("edges", G.E());
            entry.put("kruskal_weight", k.totalCost);
            entry.put("prim_weight", p.totalCost);
            entry.put("kruskal_time_ms", k.executionTimeMs);
            entry.put("prim_time_ms", p.executionTimeMs);
            entry.put("kruskal_ops_unions", k.unionCount);
            entry.put("kruskal_ops_compares", k.comparisonCount);
            entry.put("prim_ops_unions", p.unionCount);
            entry.put("prim_ops_compares", p.comparisonCount);
            entry.put("kruskal_edges", k.mstEdges);
            entry.put("prim_edges", p.mstEdges);
            jsonResults.add(entry);

            csvRows.add(new JSONHandler.CsvRow(id, "Kruskal", G.V(), k.totalCost, (long)k.executionTimeMs, k.unionCount + k.comparisonCount));
            csvRows.add(new JSONHandler.CsvRow(id, "Prim",    G.V(), p.totalCost, (long)p.executionTimeMs, p.unionCount + p.comparisonCount));
        }

        JSONHandler.writeOutputJson(outputJson, jsonResults);
        JSONHandler.writeCsv(outputCsv, csvRows);
        System.out.println("Saved: " + outputJson + " ; " + outputCsv);
    }
}