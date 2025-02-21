package daewonko.boj;
import java.util.*;
import java.io.*;
public class BOJ_16236 {


        static int N, sharkSize = 2, eatCount = 0, time = 0;
        static int[][] map;
        static int[] shark;
        static int[] dx = {-1, 0, 0, 1}, dy = {0, -1, 1, 0};

        public static void main(String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            shark = new int[2];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    if (map[i][j] == 9) {
                        shark[0] = i;
                        shark[1] = j;
                        map[i][j] = 0;
                    }
                }
            }
            while (bfs());
            System.out.println(time);
        }

        static boolean bfs() {
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
                    a[2] != b[2] ? a[2] - b[2] : (a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]));
            boolean[][] visited = new boolean[N][N];
            pq.offer(new int[]{shark[0], shark[1], 0});
            visited[shark[0]][shark[1]] = true;

            while (!pq.isEmpty()) {
                int[] cur = pq.poll();
                if (map[cur[0]][cur[1]] > 0 && map[cur[0]][cur[1]] < sharkSize) {
                    map[cur[0]][cur[1]] = 0;
                    shark[0] = cur[0];
                    shark[1] = cur[1];
                    time += cur[2];
                    if (++eatCount == sharkSize) {
                        sharkSize++;
                        eatCount = 0;
                    }
                    return true;
                }
                for (int i = 0; i < 4; i++) {
                    int nx = cur[0] + dx[i], ny = cur[1] + dy[i];
                    if (nx >= 0 && ny >= 0 && nx < N && ny < N && !visited[nx][ny] && map[nx][ny] <= sharkSize) {
                        visited[nx][ny] = true;
                        pq.offer(new int[]{nx, ny, cur[2] + 1});
                    }
                }
            }
            return false;
        }
    }

