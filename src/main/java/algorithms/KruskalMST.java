package algorithms;

public class KruskalMST {
    private int[] parent;
    private int[] rank;
    private int[] sz;
    public KruskalMST(int N){
        parent = new int[N];
        rank = new int[N];
        sz = new int[N];
        for(int i = 0; i < N; i++){
            parent[i] = i;
            sz[i] = 1;
        }
    }

}