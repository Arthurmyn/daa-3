// src/main/java/metrics/CountingIndexMinPQ.java
package metrics;

import edu.princeton.cs.algs4.IndexMinPQ;

public class CountingIndexMinPQ<Key extends Comparable<Key>> extends IndexMinPQ<Key> {
    private final MetricsTracker mt;
    public CountingIndexMinPQ(int maxN, MetricsTracker mt){ super(maxN); this.mt = mt; }
    @Override public void insert(int i, Key key){ mt.incPQ(); super.insert(i,key); }
    @Override public int delMin(){ mt.incPQ(); return super.delMin(); }
    @Override public void decreaseKey(int i, Key key){ mt.incPQ(); super.decreaseKey(i,key); }
    @Override public boolean contains(int i){ mt.incPQ(); return super.contains(i); }
    @Override public boolean isEmpty(){ mt.incPQ(); return super.isEmpty(); }
}