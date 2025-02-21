import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*
백준 / 아기 상어 / 골드3
https://www.acmicpc.net/problem/16236
 */
public class BOJ_16236 {

    private static int N;
    private static int[][] map;
    private static boolean[][] visited;
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};
    private static int sharkSize = 2;
    private static int eatCount = 0;
    private static int time = 0;
    private static Position sharkPosition;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        visited = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 9) {
                    sharkPosition = new Position(i, j);
                    map[i][j] = 0; // 아기 상어의 초기 위치를 0으로 설정
                }
            }
        }

        while (true) {
            Position target = bfs();
            if (target == null) {
                break; // 더 이상 먹을 수 있는 물고기가 없을 때 종료
            }

            time += target.distance;
            sharkPosition = new Position(target.x, target.y);
            map[target.x][target.y] = 0;
            eatCount++;

            if (eatCount == sharkSize) {
                sharkSize++;
                eatCount = 0;
            }
        }

        System.out.println(time);
    }

    private static Position bfs() {
        Queue<Position> queue = new LinkedList<>();
        queue.add(new Position(sharkPosition.x, sharkPosition.y, 0));
        visited = new boolean[N][N];
        visited[sharkPosition.x][sharkPosition.y] = true;

        List<Position> fishList = new ArrayList<>();

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (nx >= 0 && ny >= 0 && nx < N && ny < N && !visited[nx][ny]) {
                    if (map[nx][ny] <= sharkSize) {
                        visited[nx][ny] = true;
                        queue.add(new Position(nx, ny, current.distance + 1));
                        if (map[nx][ny] > 0 && map[nx][ny] < sharkSize) {
                            fishList.add(new Position(nx, ny, current.distance + 1));
                        }
                    }
                }
            }
        }

        if (fishList.isEmpty()) {
            return null;
        }

        Collections.sort(fishList);
        return fishList.get(0);
    }

    private static class Position implements Comparable<Position> {
        int x, y, distance;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Position(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public int compareTo(Position other) {
            if (this.distance == other.distance) {
                if (this.x == other.x) {
                    return Integer.compare(this.y, other.y);
                }
                return Integer.compare(this.x, other.x);
            }
            return Integer.compare(this.distance, other.distance);
        }
    }
}
