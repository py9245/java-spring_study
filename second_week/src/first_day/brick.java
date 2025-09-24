package first_day;

import java.io.*;
import java.util.*;


public class brick {
    static int N, W, H;
    static int best;
    static int[][] dxy = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    
    public static int explode(int[][] bd, int x, int y) {
        if (bd[x][y] == 0) return 0;
        
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x, y, bd[x][y]});
        int bom = 0;
        bd[x][y] = 0;
        bom += 1;
        
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int cx = curr[0], cy = curr[1], power = curr[2];
            
            if (power <= 1) continue;
            
            for (int[] dir : dxy) {
                int dx = dir[0], dy = dir[1];
                int nx = cx, ny = cy;
                
                for (int p = 1; p < power; p++) {
                    nx += dx;
                    ny += dy;
                    
                    if (!(0 <= nx && nx < W && 0 <= ny && ny < H)) break;
                    if (bd[nx][ny] == 0) continue;
                    
                    int nxtP = bd[nx][ny];
                    bd[nx][ny] = 0;
                    bom += 1;
                    
                    if (nxtP > 1) {
                        q.offer(new int[]{nx, ny, nxtP});
                    }
                }
            }
        }
        return bom;
    }
    

    public static void gravity(int[][] bd) {
        for (int x = 0; x < W; x++) {
            ArrayList<Integer> stack = new ArrayList<>();
            
            // 0이 아닌 값들만 수집
            for (int y = 0; y < H; y++) {
                if (bd[x][y] != 0) {
                    stack.add(bd[x][y]);
                }
            }
            
            // 전부 0으로 초기화
            for (int y = 0; y < H; y++) {
                bd[x][y] = 0;
            }
            
            // 아래부터 채우기
            for (int i = 0; i < stack.size(); i++) {
                bd[x][i] = stack.get(i);
            }
        }
    }
    
    // 재귀적으로 벽돌 깨기 탐색
    public static void boom(int n, int[][] bd, int total) {
        if (n == 0) {
            if (total > best) {
                best = total;
            }
            return;
        }
        
        for (int i = 0; i < W; i++) {
            Integer jHit = null;
            
            // 위에서부터 첫 번째 벽돌 찾기
            for (int y = H - 1; y >= 0; y--) {
                if (bd[i][y] > 0) {
                    jHit = y;
                    break;
                }
            }
            
            if (jHit == null) continue;
            
            // 배열 깊은 복사
            int[][] nxt = new int[W][];
            for (int j = 0; j < W; j++) {
                nxt[j] = Arrays.copyOf(bd[j], H);
            }
            
            int gained = explode(nxt, i, jHit);
            gravity(nxt);
            boom(n - 1, nxt, total + gained);
        }
        
        boolean allZero = true;
        for (int x = 0; x < W && allZero; x++) {
            for (int y = 0; y < H; y++) {
                if (bd[x][y] != 0) {
                    allZero = false;
                    break;
                }
            }
        }
        
        if (allZero) {
            if (total > best) {
                best = total;
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        
        for (int testCase = 1; testCase <= T; testCase++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            
            // 보드 입력 받기 뒤집어서
            int[][] newBoard = new int[W][H];
            for (int j = H - 1; j >= 0; j--) {
                st = new StringTokenizer(br.readLine());
                for (int i = 0; i < W; i++) {
                    newBoard[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            
            // 초기 벽돌 개수 계산
            int initial = 0;
            for (int x = 0; x < W; x++) {
                for (int y = 0; y < H; y++) {
                    if (newBoard[x][y] > 0) {
                        initial++;
                    }
                }
            }
            
            best = 0;
            
            // 배열 깊은 복사 후 탐색 시작
            int[][] copyBoard = new int[W][];
            for (int i = 0; i < W; i++) {
                copyBoard[i] = Arrays.copyOf(newBoard[i], H);
            }
            
            boom(N, copyBoard, 0);
            
            System.out.println("#" + testCase + " " + (initial - best));
        }
    }
}