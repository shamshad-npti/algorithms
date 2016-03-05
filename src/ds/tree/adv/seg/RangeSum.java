/*
 * Copyright (C) 2015 shamshad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ds.tree.adv.seg;

/**
 *
 * @author shamshad
 */
public class RangeSum {
    private final int[] elm;
    private final int size;
    public RangeSum(int[] elem) {
        this.size = elem.length;
        int s = Integer.highestOneBit(size);
        if((size & (size - 1)) != 0) {
            s <<= 1;
        }
        s = (s << 1) - 1;
        elm = new int[s];
        buildTree(elem, 0, size - 1, 0);
    }
    
    private void buildTree(int[] elem, int i, int j, int t) {
        if(i == j) {
            elm[t] = elem[i];
        } else {
            int m = (i + j) >> 1;
            buildTree(elem, i, m, left(t));
            buildTree(elem, m + 1, j, right(t));
            elm[t] = elm[left(t)] + elm[right(t)];
        }
    }
    
    public int rangeSum(int s, int e) {
        return rangeSum(s, e, 0, size - 1, 0);
    }
    
    public int rangeSum(int s, int e, int i, int j, int p) {
        if(i == s && j == e) {
            return elm[p];
        } else {
            int m = (i + j) >> 1;
            if(overlap(s, e, i, m) && overlap(s, e, m + 1, j)) {
                return rangeSum(s, m, i, m, left(p)) + rangeSum(m + 1, e, m + 1, j, right(p));
            } else if(overlap(s, e, i, m)) {
                return rangeSum(s, e, i, m, left(p));
            } else if(overlap(s, e, m + 1, j)) {
                return rangeSum(s, e, m + 1, j, right(p));
            }
            return 0;
        }
    }
    
    public void update(int v, int inx) {
        update(0, size - 1, v, inx, 0);
    }
    
    private void update(int i, int j, int v, int inx, int p) {
        if(i == j) {
            elm[p] = v;
        } else {
            int m = (i + j) >> 1;
            if(inx <= m) {
                update(i, m, v, inx, left(p));
            } else {
                update(m + 1, j, v, inx, right(p));
            }
            elm[p] = elm[left(p)] + elm[right(p)];
        }
    }
    
    private boolean overlap(int s1, int e1, int s2, int e2) {
        return (e1 - s1 + e2 - s2) >= Math.max(e1, e2) - Math.min(s1, s2);
    }
    
    private static int left(int i) {
        return (i << 1) | 1;
    }
    
    private static int right(int i) {
        return (i << 1) + 2;
    }
    
    private static int parent(int i) {
        return (i - 1) >> 1;
    }
}
