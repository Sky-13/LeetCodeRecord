package algorithm.greedy;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PrimMST {
    /**
     * 邻接表中的边
     */
    static class Edge {
        // 终点
        int to;
        // 权值
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        };
    }

    /**
     * 优先队列中的状态
     * 表示：通过from -> to 这条边，
     * 以weight的代价将to加入生成树
     */
    static class State implements Comparable<State> {
        int from;
        int to;
        int weight;

        State(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    /**
     * 最小生成树结果
     */
    static class MSTResult {
        long totalWeight;
        List<State> edges;

        MSTResult(long totalWeight, List<State> edges) {
            this.totalWeight = totalWeight;
            this.edges = edges;
        }
    }

    /**
     * 添加无向边
     */
    static void addEdge(List<Edge>[] graph, int u, int v, int weight) {
        graph[u].add(new Edge(v, weight));
        graph[v].add(new Edge(u, weight));
    }

    /**
     * prim算法
     * @param graph 无向图的邻接表
     * @return  最小生成树结果
     * @throws IllegalArgumentException 图不连通时抛出异常
     */
    static MSTResult prim(List<Edge>[] graph) {
        // 图的顶点数
        int n = graph.length;
        // 记录某个节点是否被访问过
        boolean[] visited = new boolean[n];
        // 优先队列，快速求出生成树中结点与剩余结点的最小weight
        PriorityQueue<State> pq = new PriorityQueue<>();
        // 从顶点0开始
        pq.offer(new State(-1, 0, 0));
        // 记录总代价
        int totalWeight = 0;
        // 记录访问顶点总数
        int visitedCount = 0;
        // 记录最小生成树
        List<State> mstEdges = new ArrayList<>();

        while (!pq.isEmpty()) {
            State cur = pq.poll();
            // 下一目的顶点
            int u = cur.to;
            // 该顶点已经加入生成树，跳过重复状态
            if (visited[u]) {
                continue;
            }

            visited[u] = true;
            visitedCount++;

            // 起点没有对应的生成树边
            if (cur.from != -1) {
                mstEdges.add(cur);
                totalWeight += cur.weight;
            }
            // 将当前顶点连接的边加入优先队列
            for (Edge edge : graph[u]) {
                if (!visited[edge.to]) {
                    pq.offer(new State(u, edge.to, edge.weight));
                }
            }
        }
        if (visitedCount != n) {
            throw new IllegalArgumentException("图不连通，无法构造最小生成树");
        }

        return new MSTResult(totalWeight, mstEdges);
    }

    public static void main(String[] args) {
        int n = 5;

        @SuppressWarnings("unchecked")
        List<Edge>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        /**
         * 顶点编号：0，1，2，3，4
         */
        addEdge(graph, 0, 1, 2);
        addEdge(graph, 0, 3, 6);
        addEdge(graph, 1, 2, 3);
        addEdge(graph, 1, 3, 8);
        addEdge(graph, 1, 4, 5);
        addEdge(graph, 2, 4, 7);
        addEdge(graph, 3, 4, 9);

        MSTResult mstResult = prim(graph);

        System.out.println("最小生成树的边：");

        for (State edge : mstResult.edges) {
            System.out.println(
                    edge.from + " -- " + edge.to
                            + "，权值：" + edge.weight
            );
        }

        System.out.println("最小生成树总权值：" + mstResult.totalWeight);
    }
}
