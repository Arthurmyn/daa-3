// src/main/java/algorithms/KruskalMST.java
package algorithms;

import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;
import metrics.MetricsTracker;
import metrics.CountingUF;

public class KruskalMST {
    private static final double EPS = 1.0E-12;
    private double weight;
    private final Queue<Edge> mst = new Queue<>();
    private final MetricsTracker mt;

    public KruskalMST(EdgeWeightedGraph G, MetricsTracker mt){
        this.mt = mt;
        Edge[] edges = new Edge[G.E()];
        int t=0; for (Edge e: G.edges()) edges[t++]=e;
        Arrays.sort(edges, (a,b)->{ mt.incCompare(); return Double.compare(a.weight(), b.weight()); });
        CountingUF uf = new CountingUF(G.V(), mt);
        for (int i=0; i<G.E() && mst.size()<G.V()-1; i++){
            Edge e = edges[i];
            int v = e.either(), w = e.other(v);
            if (uf.find(v)!=uf.find(w)){ uf.union(v,w); mst.enqueue(e); weight+=e.weight(); }
        }
        assert check(G);
    }
    public KruskalMST(EdgeWeightedGraph G){ this(G, new MetricsTracker()); }
    public Iterable<Edge> edges(){ return mst; }
    public double weight(){ return weight; }

    private boolean check(EdgeWeightedGraph G){
        double total=0.0; for (Edge e: edges()) total+=e.weight();
        if (Math.abs(total-weight())>EPS) return false;
        CountingUF uf = new CountingUF(G.V(), new MetricsTracker());
        for (Edge e: edges()){ int v=e.either(), w=e.other(v); if(uf.find(v)==uf.find(w)) return false; uf.union(v,w); }
        for (Edge e: G.edges()){ int v=e.either(), w=e.other(v); if(uf.find(v)!=uf.find(w)) return false; }
        for (Edge e: edges()){
            uf = new CountingUF(G.V(), new MetricsTracker());
            for (Edge f: mst){ int x=f.either(), y=f.other(x); if(f!=e) uf.union(x,y); }
            for (Edge f: G.edges()){ int x=f.either(), y=f.other(x); if(uf.find(x)!=uf.find(y) && f.weight()<e.weight()) return false; }
        }
        return true;
    }
}