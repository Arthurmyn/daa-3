// src/main/java/metrics/CountingUF.java
package metrics;

import edu.princeton.cs.algs4.UF;

public class CountingUF extends UF {
    private final MetricsTracker mt;
    public CountingUF(int n, MetricsTracker mt){ super(n); this.mt = mt; }
    @Override public int find(int p){ mt.incFind(); return super.find(p); }
    @Override public void union(int p,int q){ mt.incUnion(); super.union(p,q); }
}