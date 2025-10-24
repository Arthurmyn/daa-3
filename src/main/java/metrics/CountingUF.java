package metrics;
import edu.princeton.cs.algs4.UF;

public class CountingUF extends UF {
    public CountingUF(int n){ super(n); }
    @Override public int find(int p){ Metrics.incFind(); return super.find(p); }
    @Override public void union(int p,int q){ Metrics.incUnion(); super.union(p,q); }
}

