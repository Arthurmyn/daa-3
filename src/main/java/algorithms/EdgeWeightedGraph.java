// src/main/java/algorithms/EdgeWeightedGraph.java
package algorithms;

import java.util.*;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private final List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V){
        this.V=V; this.E=0;
        adj=(List<Edge>[]) new ArrayList[V];
        for(int i=0;i<V;i++) adj[i]=new ArrayList<>();
    }
    public void addEdge(Edge e){
        int v=e.either(), w=e.other(v);
        adj[v].add(e); adj[w].add(e); E++;
    }
    public Iterable<Edge> adj(int v){ return adj[v]; }
    public Iterable<Edge> edges(){
        List<Edge> list=new ArrayList<>();
        for(int v=0; v<V; v++) for(Edge e: adj[v]) if(e.other(v)>v) list.add(e);
        return list;
    }
    public int V(){ return V; }
    public int E(){ return E; }
}