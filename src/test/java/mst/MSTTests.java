// src/test/java/mst/MSTTests.java
package mst;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import algorithms.Edge;
import algorithms.EdgeWeightedGraph;

import algorithms.mst.Runner;
import algorithms.mst.MSTResult;

public class MSTTests {

    private List<String> nodes(int n){
        List<String> a=new ArrayList<>(n);
        for(int i=0;i<n;i++) a.add(String.valueOf(i));
        return a;
    }

    private EdgeWeightedGraph graph(int n, int[][] edges){
        EdgeWeightedGraph G = new EdgeWeightedGraph(n);
        for(int[] t: edges){
            G.addEdge(new Edge(t[0], t[1], t[2]/1000.0));
        }
        return G;
    }

    private Map<String,Integer> indexByName(List<String> nodes){
        Map<String,Integer> m=new HashMap<>();
        for(int i=0;i<nodes.size();i++) m.put(nodes.get(i), i);
        return m;
    }

    private int[][] edgesToPairs(List<Map<String,Object>> edges, Map<String,Integer> idx){
        int[][] arr = new int[edges.size()][2];
        for(int i=0;i<edges.size();i++){
            Object fv = edges.get(i).get("from");
            Object tv = edges.get(i).get("to");
            int v = (fv instanceof Number) ? ((Number)fv).intValue() : idx.get(String.valueOf(fv));
            int w = (tv instanceof Number) ? ((Number)tv).intValue() : idx.get(String.valueOf(tv));
            arr[i][0]=v; arr[i][1]=w;
        }
        return arr;
    }

    private boolean isAcyclic(int n, int[][] pairs){
        int[] parent = new int[n];
        Arrays.fill(parent,-1);
        java.util.function.IntUnaryOperator find = new java.util.function.IntUnaryOperator(){
            public int applyAsInt(int x){
                if(parent[x]<0) return x;
                parent[x]=applyAsInt(parent[x]);
                return parent[x];
            }
        };
        java.util.function.BiConsumer<Integer,Integer> unite = (a,b)->{
            int pa=find.applyAsInt(a), pb=find.applyAsInt(b);
            if(pa!=pb) parent[pa]=pb;
        };
        for(int[] e: pairs){
            int a=find.applyAsInt(e[0]), b=find.applyAsInt(e[1]);
            if(a==b) return false;
            unite.accept(a,b);
        }
        return true;
    }

    private boolean isConnected(int n, int[][] pairs){
        List<List<Integer>> adj = new ArrayList<>();
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
        for(int[] e: pairs){
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }
        boolean[] vis = new boolean[n];
        Deque<Integer> dq=new ArrayDeque<>();
        dq.add(0); vis[0]=true;
        while(!dq.isEmpty()){
            int v=dq.poll();
            for(int w: adj.get(v)) if(!vis[w]){ vis[w]=true; dq.add(w); }
        }
        for(boolean b: vis) if(!b) return false;
        return true;
    }

    @Test
    @DisplayName("Связный граф: Prim == Kruskal, |E|=V-1, ацикличность, связность, время ≥ 0, воспроизводимость")
    void corePropertiesOnConnectedGraph(){
        int n=6;
        int[][] es = {
                {0,1, 10}, {0,2, 20}, {1,2, 5},
                {1,3, 15}, {2,3, 40}, {2,4, 25},
                {3,4, 30}, {3,5, 18}, {4,5, 16}
        };
        EdgeWeightedGraph G = graph(n, es);
        List<String> nodes = nodes(n);
        Map<String,Integer> idx = indexByName(nodes);

        MSTResult prim = Runner.runPrim(G, nodes);
        MSTResult kruskal = Runner.runKruskal(G, nodes);

        assertEquals(prim.totalCost, kruskal.totalCost, 1e-9);
        assertEquals(n-1, prim.mstEdges.size());
        assertEquals(n-1, kruskal.mstEdges.size());

        int[][] pairs = edgesToPairs(prim.mstEdges, idx);
        assertTrue(isAcyclic(n, pairs));
        assertTrue(isConnected(n, pairs));

        assertTrue(prim.executionTimeMs >= 0);
        assertTrue(kruskal.executionTimeMs >= 0);

        MSTResult prim2 = Runner.runPrim(G, nodes);
        assertEquals(prim.totalCost, prim2.totalCost, 1e-9);
    }

    @Test
    @DisplayName("Несвязный граф: алгоритмы не возвращают V-1 рёбер или бросают исключение")
    void disconnectedGraphHandled(){
        int n=5;
        int[][] es = { {0,1, 10}, {2,3, 7} };
        EdgeWeightedGraph G = graph(n, es);
        List<String> nodes = nodes(n);

        try{
            MSTResult r = Runner.runPrim(G, nodes);
            assertNotEquals(n-1, r.mstEdges.size());
        }catch(Exception e){ assertTrue(true); }

        try{
            MSTResult r = Runner.runKruskal(G, nodes);
            assertNotEquals(n-1, r.mstEdges.size());
        }catch(Exception e){ assertTrue(true); }
    }

    @Test
    void totalCostEqualsSumOfEdges() {
        int n=5;
        int[][] es={{0,1,10},{1,2,20},{2,3,30},{3,4,40},{0,4,50}};
        var G = graph(n, es);
        var nodes = nodes(n);

        MSTResult prim = Runner.runPrim(G, nodes);

        double sum = prim.mstEdges.stream()
                .mapToDouble(e -> ((Number)e.get("weight")).doubleValue())
                .sum();

        assertEquals(sum, prim.totalCost, 1e-9);
    }

    @Test
    void timesAreNonNegativeForBoth() {
        int n=6;
        int[][] es={{0,1,10},{1,2,5},{2,3,7},{3,4,9},{4,5,6},{0,5,8},{1,4,11}};
        var G = graph(n, es);
        var nodes = nodes(n);

        var prim = Runner.runPrim(G, nodes);
        var kruskal = Runner.runKruskal(G, nodes);

        assertTrue(prim.executionTimeMs >= 0);
        assertTrue(kruskal.executionTimeMs >= 0);
    }
}