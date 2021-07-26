package com.wq.algorithm.lesson5bag9p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TreeDependencyBag {

    private static int N = 1000;
    private static int nEdge = 0;
    private static int[] head;
    private static int[] next;
    private static int[] edge;
    private static int V = 0;
    private static int[][] dp; // dp[src][v] 体积是v的情况下，以src为根的子树最大价值
    private static int[] volumes;
    private static int[] values;

    // 添加边
    public static void add(int a, int b){
        edge[nEdge] = b;
        next[nEdge] = head[a];
        head[a] = nEdge++;
    }

    private static void dfs(int src){
        for(int e = head[src]; e!=-1; e = next[e]){ // 遍历从src出发的所有边
            int target = edge[e]; // 通过 边的编号 取得目标点
            dfs(target); // ① 对于所有k dp[target][k] 最优
            // ② 这里暂时不考虑 根节点的对状态产生的影响，但是下面是取子树上的物品
            // 因此，根节点必取，所以V要扣掉根节点的volumes[src]
            // ③ 每个物品只能取 0 或 1次，因此，倒着更新所有的dp[src][ (V-volumes[src]) ~ 0 ]
            for(int v = V - volumes[src]; v >= 0; v--){

                for(int kv = 0; kv <= v; kv++){ // ④ 以target为根的子树，视为组内有 [0~v]中物品的 小组，形成分组背包
                    // ⑤ 到这里为止，所有的 dp[src][v] 都是不包含 src 节点的，
                    // 即，dp[src][v - kv]相当于分配给除了 target子节点 之外的所有子节点v - kv容量所产生的价值
                    // target 遍历所有子树，target 与 其它所有子树的 组合 取最大值
                    dp[src][v] = Math.max(dp[src][v], dp[src][v - kv] + dp[target][kv]);
                }
            }
        }
        // ⑥ 算出所有的 dp[src][0~V] ==== 这一步才把src节点也算上（而且是必须算上，因为上面取了src子树的物品）
        for(int v = V; v>=volumes[src]; v--){
            dp[src][v] = dp[src][v - volumes[src]] + values[src];
        }
        for(int v = volumes[src] - 1; v>=0; v--){
            dp[src][v] = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line = bufferedReader.readLine();
        int nItem = Integer.valueOf(line.split(" ")[0]);
        V = Integer.valueOf(line.split(" ")[1]);
        head = new int[nItem + 1]; // 第69行 i是从1开始到nItem 所以数组都要多开1
        next = new int[nItem + 1];
        edge = new int[nItem + 1];

        volumes = new int[nItem + 1];
        values = new int[nItem + 1];

        dp = new int[nItem + 1][V + 1];
        int root = Integer.MIN_VALUE;
        int p;
        Arrays.fill(head, -1);
        for(int i = 1; i <= nItem; i++){
            line = bufferedReader.readLine();
            volumes[i] = Integer.valueOf(line.split(" ")[0]);
            values[i] = Integer.valueOf(line.split(" ")[1]);
            p = Integer.valueOf(line.split(" ")[2]);
            if(p == -1){
                root = i;
            }else{
                add(p,i);
            }
        }
        dfs(root);
        System.out.println(dp[root][V]);
    }
}
