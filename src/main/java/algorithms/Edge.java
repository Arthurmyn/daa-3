// src/main/java/algorithms/Edge.java
package algorithms;

public class Edge implements Comparable<Edge> {
    private final int v, w;
    private final double weight;
    public Edge(int v, int w, double weight){ this.v=v; this.w=w; this.weight=weight; }
    public double weight(){ return weight; }
    public int either(){ return v; }
    public int other(int x){ if (x==v) return w; if (x==w) return v; throw new IllegalArgumentException(); }
    @Override public int compareTo(Edge o){ return Double.compare(this.weight, o.weight); }
    @Override public String toString(){ return v + "-" + w + " " + weight; }
}