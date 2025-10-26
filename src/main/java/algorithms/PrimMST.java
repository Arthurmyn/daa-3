// src/main/java/algorithms/PrimMST.java
package algorithms;

import edu.princeton.cs.algs4.Queue;
import metrics.MetricsTracker;
import metrics.CountingIndexMinPQ;
import metrics.CountingUF;

public class PrimMST {
    private static final double EPS = 1.0E-12;
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private CountingIndexMinPQ<Double> pq;
    private final MetricsTracker mt;

    public PrimMST(EdgeWeightedGraph G, MetricsTracker mt){
        this.mt = mt;
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new CountingIndexMinPQ<>(G.V(), mt);
        for (int v=0; v<G.V(); v++) distTo[v]=Double.POSITIVE_INFINITY;
        for (int v=0; v<G.V(); v++) if(!marked[v]) prim(G,v);
        assert check(G);
    }
    public PrimMST(EdgeWeightedGraph G){ this(G, new MetricsTracker()); }

    private void prim(EdgeWeightedGraph G, int s){
        distTo[s]=0.0; pq.insert(s, distTo[s]);
        while(!pq.isEmpty()){ int v=pq.delMin(); scan(G,v); }
    }
    private void scan(EdgeWeightedGraph G, int v){
        marked[v]=true;
        for (Edge e: G.adj(v)){
            int w=e.other(v);
            if (marked[w]) continue;
            if (e.weight()<distTo[w]){ mt.incCompare(); distTo[w]=e.weight(); edgeTo[w]=e; if(pq.contains(w)) pq.decreaseKey(w, distTo[w]); else pq.insert(w, distTo[w]); }
            else mt.incCompare();
        }
    }
    public Iterable<Edge> edges(){
        Queue<Edge> mst = new Queue<>();
        for (int v=0; v<edgeTo.length; v++){ Edge e=edgeTo[v]; if(e!=null) mst.enqueue(e); }
        return mst;
    }
    public double weight(){ double w=0.0; for(Edge e: edges()) w+=e.weight(); return w; }

    private boolean check(EdgeWeightedGraph G){
        double tw=0.0; for (Edge e: edges()) tw+=e.weight();
        if (Math.abs(tw-weight())>EPS) return false;
        CountingUF uf = new CountingUF(G.V(), new MetricsTracker());
        for (Edge e: edges()){ int v=e.either(), w=e.other(v); if(uf.find(v)==uf.find(w)) return false; uf.union(v,w); }
        for (Edge e: G.edges()){ int v=e.either(), w=e.other(v); if(uf.find(v)!=uf.find(w)) return false; }
        for (Edge e: edges()){
            uf = new CountingUF(G.V(), new MetricsTracker());
            for (Edge f: edges()){ int x=f.either(), y=f.other(x); if(f!=e) uf.union(x,y); }
            for (Edge f: G.edges()){ int x=f.either(), y=f.other(x); if(uf.find(x)!=uf.find(y) && f.weight()<e.weight()) return false; }
        }
        return true;
    }
}