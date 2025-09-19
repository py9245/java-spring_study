package day_07;

import java.io.*;
import java.util.*;

public class day_07_01 {
    
    static final int LIMIT = 2000;
    static final int[][] DXY = {{0,1},{0,-1},{-1,0},{1,0}};
    
    static class Microbe {
        int x, y, d, k;
        
        Microbe(int x, int y, int d, int k) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.k = k;
        }
        
        void move() {
            x += DXY[d][0];
            y += DXY[d][1];
        }
        
        boolean isOutOfBounds() {
            return x < -LIMIT || x > LIMIT || y < -LIMIT || y > LIMIT;
        }
        
        long getPositionKey() {
            return ((long)(x + LIMIT) << 16) | (y + LIMIT);
        }
    }
    
    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        
        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            
            List<Microbe> microbes = new ArrayList<>(N);
            
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()) * 2;
                int y = Integer.parseInt(st.nextToken()) * 2;
                int d = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());
                microbes.add(new Microbe(x, y, d, k));
            }
            
            long answer = 0;
            
            for (int step = 0; step <= 4000 && microbes.size() > 1; step++) {
                for (int i = microbes.size() - 1; i >= 0; i--) {
                    Microbe m = microbes.get(i);
                    m.move();
                    
                    if (m.isOutOfBounds()) {
                        microbes.remove(i);
                    }
                }
                
                if (microbes.size() <= 1) break;
                
                // 충돌 처리 - 위치별 그룹화
                microbes.sort((a, b) -> Long.compare(a.getPositionKey(), b.getPositionKey()));
                
                List<Microbe> survivors = new ArrayList<>();
                int i = 0;
                
                while (i < microbes.size()) {
                    long currentPos = microbes.get(i).getPositionKey();
                    int groupStart = i;
                    int totalK = 0;
                    int maxK = 0;
                    int maxKIdx = i;
                    
                    while (i < microbes.size() && microbes.get(i).getPositionKey() == currentPos) {
                        Microbe m = microbes.get(i);
                        totalK += m.k;
                        if (m.k > maxK) {
                            maxK = m.k;
                            maxKIdx = i;
                        }
                        i++;
                    }
                    
                    // 충돌 처리
                    if (i - groupStart >= 2) {
                        // 2개 이상이면 충돌
                        answer += totalK;
                    } else {
                        // 1개면 살아남음
                        survivors.add(microbes.get(groupStart));
                    }
                }
                
                microbes = survivors;
            }
            
            System.out.println("#" + tc + " " + answer);
        }
    }
}