package algorithm;
import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class MinimumSquareEasy {
    private static final int[][] MOVE = {{2, 0, 0, 0}, {1, 1, 0, 0}, {1, 0, 1, 0},
                                         {1, 0, 0, 1}, {0, 2, 0, 0}, {0, 1, 1, 0},
                                         {0, 1, 0, 1}, {0, 0, 2, 0}, {0, 0, 1, 1},
                                         {0, 0, 0, 2}};
    public long minArea(int[] x, int[] y) {
        int len = x.length;
        Point[] px = new Point[len];
        for (int i = 0; i < len; i++)
            px[i] = new Point(x[i], y[i]);
        Comparator<Point> xSorter = (Point o1, Point o2) -> o1.x == o2.x ? o1.y - o2.y : o1.x - o2.x;
        Comparator<Point> ySorter = (Point o1, Point o2) -> o1.y == o2.y ? o1.x - o2.x : o1.y - o2.y;
        Arrays.sort(px, xSorter);
        int mxx, mxy, mnx, mny, l;
        int min = Integer.MAX_VALUE;
        for (int[] m : MOVE) {
            Stack<Point> pts = new Stack<>();
            pts.addAll(Arrays.asList(px));
            int k = m[0];
            while(k-- != 0) pts.remove(0);
            mnx = pts.get(0).x - 1;
            k = m[2];
            while(k-- != 0) pts.pop();
            mxx = pts.peek().x + 1;
            pts.sort(ySorter);
            k = m[1];
            while(k-- != 0) pts.remove(0);
            mny = pts.get(0).y - 1;
            k = m[3];
            while(k-- != 0) pts.pop();
            mxy = pts.peek().y + 1;
            l = Math.max(mxx - mnx, mxy - mny);
            if(canplace(x, y, mnx, mny, l))
                min = Math.min(min, l);            
        }
        return 1L * min * min;
    }

    boolean canplace(int x[], int y[], long x1, long y1, long len) {
        long in = 0, l = x.length, x2 = x1 + len, y2 = y1 + len;
        for (int i = 0; i < l; i++)
            if(inside(x[i], y[i], x1, y1, x2, y2))
                in++;
        return l - in <= 2;
    }
    
    boolean inside(long x, long y, long x1, long y1, long x2, long y2) {
        return x > x1 && y > y1 && x < x2 && y < y2;
    }    
}