// src/main/java/metrics/MetricsTracker.java
package metrics;

public final class MetricsTracker {
    private long comparisons, finds, unions, pqOps;
    public void incCompare(){ comparisons++; }
    public void incFind(){ finds++; }
    public void incUnion(){ unions++; }
    public void incPQ(){ pqOps++; }
    public long comparisons(){ return comparisons; }
    public long finds(){ return finds; }
    public long unions(){ return unions; }
    public long pqOps(){ return pqOps; }
    public long opCount(){ return comparisons + finds + unions + pqOps; }
}