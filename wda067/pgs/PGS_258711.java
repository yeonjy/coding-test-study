import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
프로그래머스 / 도넛과 막대 그래프 / Level 2
https://school.programmers.co.kr/learn/courses/30/lessons/258711
 */
class PGS_258711 {

    private static List<List<Integer>> adjList;
    private static boolean[] visited;
    private static int doughnut, stick, eight, generatedNode;

    //노드와 에지의 개수로 그래프 모양 판단
    public int[] solution(int[][] edges) {

        int N = 0;  //노드의 수
        for (int[] edge : edges) {
            N = Math.max(N, Math.max(edge[0], edge[1]));
        }

        adjList = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            adjList.add(new ArrayList<>());
        }

        int[] inDegree = new int[N + 1];
        int[] outDegree = new int[N + 1];
        visited = new boolean[N + 1];

        //인접 리스트 구현 및 진입 & 진출 차수 계산
        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            inDegree[edge[1]]++;
            outDegree[edge[0]]++;
        }

        //생성된 노드 찾기
        for (int i = 1; i <= N; i++) {
            //생성된 노드의 진입 차수는 존재 하지 않고 그래프 수의 합은 2 이상이어야 함
            if (inDegree[i] == 0 && outDegree[i] >= 2) {
                generatedNode = i;
                break;
            }
        }

        //생성된 노드부터 탐색 시작
        for (Integer next : adjList.get(generatedNode)) {
            bfs(next);
        }

        return new int[]{generatedNode, doughnut, stick, eight};
    }

    private static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        int nodeCount = 0;
        int edgeCount = 0;

        q.add(start);
        visited[start] = true;

        while (!q.isEmpty()) {
            Integer cur = q.poll();
            nodeCount++;

            for (Integer next : adjList.get(cur)) {
                edgeCount++;
                if (!visited[next]) {
                    visited[next] = true;
                    q.add(next);
                }
            }
        }

        //노드와 에지의 개수로 그래프 판별
        if (edgeCount == nodeCount) {
            doughnut++;
        } else if (edgeCount == nodeCount - 1) {
            stick++;
        } else if (edgeCount == nodeCount + 1) {
            eight++;
        }
    }

    //그래프의 특징으로 그래프 판단
    public int[] solution2(int[][] edges) {

        int N = 0;  //노드의 수
        for (int[] edge : edges) {
            N = Math.max(N, Math.max(edge[0], edge[1]));
        }

        int[] inDegree = new int[N + 1];
        int[] outDegree = new int[N + 1];

        for (int[] edge : edges) {
            inDegree[edge[1]]++;
            outDegree[edge[0]]++;
        }

        int generatedNode = 0;
        int stick = 0;
        int eight = 0;

        for (int i = 1; i <= N; i++) {
            if (outDegree[i] >= 2) {  //진출 차수가 2 이상이고
                if (inDegree[i] == 0) {  //진입 차수가 0이면 생성된 노드
                    generatedNode = i;
                } else {  //진출 차수가 2인 노드가 존재하면 8자 그래프
                    eight++;
                }
            } else if (outDegree[i] == 0 && inDegree[i] >= 1) {  //진출 차수가 0이고, 진입 차수가 1개 이상이면 막대 그래프
                stick++;
            }
        }

        int doughnut = outDegree[generatedNode] - eight - stick;

        return new int[]{generatedNode, doughnut, stick, eight};
    }
}
