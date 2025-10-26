package algorithms;

import java.io.*;
import java.util.*;

public final class GraphVizExporter {

    public static void writeDot(EdgeWeightedGraph G, String outDot) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outDot))) {
            pw.println("graph G {");
            pw.println("  overlap=false; splines=true; fontsize=10;");
            for (int v = 0; v < G.V(); v++) pw.printf("  %d [shape=circle];%n", v);
            for (Edge e : G.edges()) {
                int v = e.either(), w = e.other(v);
                pw.printf("  %d -- %d [label=\"%.3f\"];%n", v, w, e.weight());
            }
            pw.println("}");
        }
    }

    public static void writeDotWithMST(EdgeWeightedGraph G, Iterable<Edge> mst, String outDot) throws IOException {
        Set<String> inMst = new HashSet<>();
        for (Edge e : mst) {
            int v = e.either(), w = e.other(v);
            inMst.add(key(v, w));
        }
        writeColored(G, inMst, outDot);
    }

    public static void writeDotWithMST(EdgeWeightedGraph G, List<Map<String,Object>> mstEdges, String outDot) throws IOException {
        Set<String> inMst = new HashSet<>();
        for (Map<String,Object> m : mstEdges) {
            int v = ((Number)m.get("from")).intValue();
            int w = ((Number)m.get("to")).intValue();
            inMst.add(key(v, w));
        }
        writeColored(G, inMst, outDot);
    }

    private static void writeColored(EdgeWeightedGraph G, Set<String> inMst, String outDot) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(outDot))) {
            pw.println("graph G {");
            pw.println("  overlap=false; splines=true; fontsize=10;");
            for (int v = 0; v < G.V(); v++) pw.printf("  %d [shape=circle];%n", v);
            for (Edge e : G.edges()) {
                int v = e.either(), w = e.other(v);
                String style = inMst.contains(key(v, w)) ? ",color=red,penwidth=3" : "";
                pw.printf("  %d -- %d [label=\"%.3f\"%s];%n", v, w, e.weight(), style);
            }
            pw.println("}");
        }
    }

    private static String key(int a, int b) { return (a < b) ? a + "-" + b : b + "-" + a; }
}