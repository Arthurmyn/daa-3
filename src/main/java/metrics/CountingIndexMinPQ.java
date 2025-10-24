package metrics;
import edu.princeton.cs.algs4.IndexMinPQ;

public class CountingIndexMinPQ<Key extends Comparable<Key>> extends IndexMinPQ<Key> {
    public CountingIndexMinPQ(int maxN){ super(maxN); }
    @Override public void insert(int i, Key key){ Metrics.incPQ(); super.insert(i,key); }
    @Override public int delMin(){ Metrics.incPQ(); return super.delMin(); }
    @Override public void decreaseKey(int i, Key key){ Metrics.incPQ(); super.decreaseKey(i,key); }
    @Override public boolean contains(int i){ Metrics.incPQ(); return super.contains(i); }
    @Override public boolean isEmpty(){ Metrics.incPQ(); return super.isEmpty(); }
}