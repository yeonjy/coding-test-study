package yeonjy.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_16236 {
    private static int[] dx = {1, 0, -1, 0};
    private static int[] dy = {0, 1, 0, -1};
    private static int N;
    private static int[][] map;
    private static int[][] dist;

    private static int sharkSize = 2;
    private static int eatingCnt = 0;
    private static int count = 0;
    private static int sharkX = -1;
    private static int sharkY = -1;
    private static int minX;
    private static int minY;
    private static int minDist;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        map = new int[N + 1][N + 1];

        StringTokenizer st;
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());

                if (map[i][j] == 9) {
                    sharkX = i;
                    sharkY = j;
                    map[i][j] = 0;
                }
            }
        }

        while (true) {
            dist = new int[N + 1][N + 1];  //매번 거리 맵 초기화
            minX = Integer.MAX_VALUE;
            minY = Integer.MAX_VALUE;
            minDist = Integer.MAX_VALUE;

            bfs(sharkX, sharkY);

            if (minX != Integer.MAX_VALUE && minY != Integer.MAX_VALUE) {  //물고기를 먹었으면
                eatingCnt++;
                map[minX][minY] = 0;
                sharkX = minX;
                sharkY = minY;
                count += dist[minX][minY];

                if (eatingCnt == sharkSize) {  //아기상어 크기만큼의 물고기 먹으면
                    sharkSize++;
                    eatingCnt = 0;  //물고기 먹은 수 초기화
                }

            } else {
                break;
            }

        }
        System.out.println(count);
    }

    static void bfs(int x, int y) {  //상하좌우 움직이기
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));  //파라미터로 들어온 좌표 추가

        while (!queue.isEmpty()) {
            Point p = queue.poll();

            for (int i = 0; i < 4; i++) {  //상하좌우
                int newX = p.x + dx[i];
                int newY = p.y + dy[i];

                if (isInArea(newX, newY) && isAbleToMove(newX, newY) && dist[newX][newY] == 0) {
                    dist[newX][newY] = dist[p.x][p.y] + 1;
                    if (isEatable(newX, newY)) {
                        if (minDist > dist[newX][newY]) {
                            minDist = dist[newX][newY];
                            minX = newX;
                            minY = newY;
                        } else if (minDist == dist[newX][newY]) {
                            if (minX == newX) {
                                if (minY > newY) {
                                    minX = newX;
                                    minY = newY;
                                }
                            }
                            else if (minX > newX) {
                                minX = newX;
                                minY = newY;
                            }
                        }
                    }
                    queue.add(new Point(newX, newY));
                }

            }
        }
    }

    private static boolean isAbleToMove(int x, int y) {  //상어 크기보다 물고기가 더 큰지 검사
        return map[x][y] <= sharkSize;
    }

    private static boolean isEatable(int x, int y) {  //물고기인지 && 먹을 수 있는 물고기인지 검사
        return map[x][y] != 0 && map[x][y] < sharkSize;
    }

    private static boolean isInArea(int x, int y) {  //map 범위 내인지 확인
        return x <= N && x > 0 && y <= N && y > 0;
    }

    private static class Point {
        int x;
        int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
