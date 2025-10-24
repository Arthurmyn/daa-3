package  metrics;
public final class Metrics {
    private static long comps, finds, unions, pqOps;
    public static void reset(){ comps=finds=unions=pqOps=0; }
    public static void incCompare(){ comps++; }
    public static void incFind(){ finds++; }
    public static void incUnion(){ unions++; }
    public static void incPQ(){ pqOps++; }
    public static long comparisons(){ return comps; }
    public static long finds(){ return finds; }
    public static long unions(){ return unions; }
    public static long pqOps(){ return pqOps; }
    public static long total(){ return comps+finds+unions+pqOps; }
}