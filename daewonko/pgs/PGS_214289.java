package daewonko.pgs;

import java.util.*;

public class PGS_214289 {


    static class Node implements Comparable<Node> {
        int v, cost;

        Node(int v, int cost) {
            this.v = v;
            this.cost = cost;
        }

        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        List<List<Node>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] road : roads) {
            graph.get(road[0]).add(new Node(road[1], 1));
            graph.get(road[1]).add(new Node(road[0], 1));
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        bfs(destination, graph, dist);

        int[] answer = new int[sources.length];
        for (int i = 0; i < sources.length; i++)
            answer[i] = dist[sources[i]] == Integer.MAX_VALUE ? -1 : dist[sources[i]];
        return answer;
    }

    static void bfs(int start, List<List<Node>> graph, int[] dist) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.cost > dist[cur.v]) continue;

            for (Node next : graph.get(cur.v)) {
                int newDist = cur.cost + next.cost;
                if (newDist < dist[next.v]) {
                    dist[next.v] = newDist;
                    pq.offer(new Node(next.v, newDist));
                }
            }
        }
    }
}


