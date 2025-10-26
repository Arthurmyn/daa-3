// src/main/java/algorithms/Runner.java
package algorithms;

import metrics.MetricsTracker;

public class Runner {
    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(5);
        G.addEdge(new Edge(0,1,4));
        G.addEdge(new Edge(0,2,2));
        G.addEdge(new Edge(1,2,1));
        G.addEdge(new Edge(1,3,5));
        G.addEdge(new Edge(2,3,8));
        G.addEdge(new Edge(3,4,6));

        MetricsTracker m1 = new MetricsTracker();
        KruskalMST k = new KruskalMST(G, m1);
        System.out.println("Kruskal weight=" + k.weight() + " ops=" + m1.opCount());

        MetricsTracker m2 = new MetricsTracker();
        PrimMST p = new PrimMST(G, m2);
        System.out.println("Prim weight=" + p.weight() + " ops=" + m2.opCount());
    }
}