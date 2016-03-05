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
 * @param <T>
 */
public class RangeMin<T extends Comparable<? super T>> {
    private Comparable<? super T>[] elem;
    private int size;
    public RangeMin(T[] elems) {
        int mx = size = elems.length;
        int len = Integer.highestOneBit(mx);
        if((mx & (mx - 1)) != 0) {
            len <<= 1;
        }
        len = (len << 1) - 1;
        this.elem = new Comparable[len];
        buildTree(elems, 0, mx - 1, 0);
    }
    
    private void buildTree(T[] elems, int i, int j, int p) {
        if(i == j) {
            elem[p] = elems[i];
        } else {
            int m = (i + j) >> 1;
            int l = left(p), r = right(p);
            System.out.println(i + " "+j);
            buildTree(elems, i, m, l);
            buildTree(elems, m + 1, j, r);
            elem[p] = elem[l].compareTo((T)elem[r]) <= 0 ? elem[l] : elem[r];
        }
    }
    
    public T minimum(int s, int e) {
        if(e < 0 || s >= size || s > e) return null;
        return minimum(Math.max(s, 0), Math.min(size - 1, e), 0, size - 1, 0);
    }
    
    private T minimum(int s, int e, int i, int j, int p) {
        if(s == i && e == j) {
            return (T) elem[p];
        } else {
            int m = (i + j) >> 1;
            int l = left(p);
            int r = right(p);
            if(overlap(s, e, i, m) && overlap(s, e, m + 1, j)) {
                T left = minimum(s, m, i, m, l);
                T right = minimum(m + 1, e, m + 1, j, r);
                return left.compareTo(right) < 0 ? left : right;
            } else if(overlap(s, e, i, m)) {
                return minimum(s, e, i, m, l);
            } else if(overlap(s, e, m + 1, j)) {
                return minimum(s, e, m + 1, j, r);
            } else {
                System.out.println("Unexpected...");
                return null;
            }
        }
    }
    
    public void update(int index, T v) {
        update(0, size - 1, index, v, 0);
    }
    
    private void update(int i, int j, int inx, T v, int p) {
        if(i == j) {
            elem[p] = v;
        } else {
            int m = (i + j) >> 1;
            if(inx <= m) {
                update(i, m, inx, v, left(p));
            } else {
                update(m + 1, j, inx, v, right(p));
            }
            if(elem[p].compareTo(v) > 0) {
                elem[p] = v;
            }
        }
    }
        
    private boolean overlap(int s1, int e1, int s2, int e2) {
        return (e1 - s1) + (e2 - s2) >= Math.max(e1, e2) - Math.min(s1, s2);
    }
    
    public void printTree() {
        for (Comparable<? super T> e : elem) {
            System.out.println(e);
        }
    }
    
    private boolean isLeftChild(int i) {
        return (i & 1) == 1;
    }
    
    private static int parent(int p) {
        return (p - 1) >>> 1;
    }
    
    private static int left(int p) {
        return (p << 1) | 1;
    }
    
    private static int right(int p) {
        return (p + 1) << 1;
    }
}
