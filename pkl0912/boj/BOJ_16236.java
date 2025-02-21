package pkl0912.boj;
import java.util.*;
import java.io.*;

public class BOJ_16236{
    static int size=2;
    static int[] pos;
    static int[] dx = {-1,0,1,0};
    static int[] dy = {0,-1,0,1};
    static int n;
    static int[][] graph;
    static int time = 0;
    static int eat = 0;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        graph = new int[n][n];
        pos = new int[2];
        for(int i = 0; i<n; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 0; j<n; j++){
                int num = Integer.parseInt(st.nextToken());
                graph[i][j] = num;
                if(num==9){
                    pos[0] = i;
                    pos[1] = j;
                    graph[i][j] = 0;
                }

            }
        }
        while(true){
            int[] next = bfs();
            if(next==null) break;
            pos[0] = next[0];
            pos[1] = next[1];
            time += next[2];
            graph[pos[0]][pos[1]] = 0;
            eat++;
            if(size==eat){
                size++;
                eat=0;
            }

        }  
        System.out.println(time);
        
    }
    static int[] bfs(){
        PriorityQueue<int[]> q = new PriorityQueue<>((a,b)->{
            if(a[2]==b[2]){
                if(a[0]==b[0])return a[1]-b[1];
                return a[0]-b[0];
            }
            return a[2]-b[2];
        });
        int[][] dist = new int[n][n];
        q.add(new int[]{pos[0], pos[1], 0});
        dist[pos[0]][pos[1]] = 1;

        while(!q.isEmpty()){
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int d = cur[2];
            if (graph[x][y] > 0 && graph[x][y] < size) {
                return new int[]{x, y, d}; 
            }
            for(int i = 0; i<4; i++){
                int nx = x+dx[i];
                int ny = y+dy[i];
                if(0<=nx && nx <n && 0<=ny && ny<n && graph[nx][ny]<=size && dist[nx][ny]==0){
                    dist[nx][ny] =d +1;
                    q.add(new int[]{nx, ny, d+1});
                
                }
            }           
        }
        return null;
    }
}