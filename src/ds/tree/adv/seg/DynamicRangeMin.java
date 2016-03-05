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
 * Augmented HBBST to support dynamic range min query
 * Insertion O(lg(n))
 * Deletion O(lg(n))
 * Update O(lg(n))
 * Range Minimum Query O(lg(n))
 * Minimum Query O(lg(n))
 * (It can be also used as min heap with slight modification)
 * 
 * @author Shamshad Alam
 * @param <T>
 */
public class DynamicRangeMin<T extends Comparable<? super T>> {
    private TreeEntry root;

    public DynamicRangeMin() {
    }
    
    public DynamicRangeMin(int capacity, T max) {
        root = initTree(1, capacity, max);
        root.val = root.min = max;
    }
    
    private TreeEntry initTree(int i, int j, T max) {
        if(i == j) {
            return new TreeEntry(max, null, null, null);
        } else {
            int m = (i + j) >> 1;
            TreeEntry parent = new TreeEntry(max, null, null, null, j - i + 1);
            if(i < m) {
                parent.left = initTree(i, m - 1, max);
                parent.left.parent = parent;
            }
            if(j > m) {
                parent.right = initTree(m + 1, j, max);
                parent.right.parent = parent;
            }
            return parent;
        }
    }
    
    public void insert(int inx, T val) {
        if(root == null) {
            root = new TreeEntry(val, null, null, null);
        } else {
            TreeEntry e = root, t = root;
            while(e != null) {
                t = e;
                e.size++;
                if(inx <= rank(e)) {
                    e = e.left;
                } else {
                    e = e.right;
                }
            }
            if(inx <= rank(e)) {
                t.left = new TreeEntry(val, null, null, t);
            } else {
                t.right = new TreeEntry(val, null, null, t);
            }
            balance(t);
        }
    }
    
    public void add(T val) {
        insert(size() + 1, val);
    }
    
    public void update(int inx, T val) {
        if(inx > 0 && inx <= size()) {
            TreeEntry e = entryAt(root, inx);
            e.val = val;
            while(e != null) {
                e.min = min(val, e.min);
                e = e.parent;
            }
        }
    }
        
    public void delete(int inx) {
        if(inx > 0 && inx <= size()) {
            TreeEntry e = entryAt(root, inx);
            TreeEntry b = e.parent;
            if(e.left == null) {
                transplant(e, e.right);
            } else if(e.right == null) {
                transplant(e, e.left);
            } else {
                TreeEntry next = entryAt(e.right, 1);
                b = next.parent;
                if(e.right != next) {
                    transplant(next, next.right);
                    next.right = e.right;
                    next.right.parent = next;
                }
                transplant(e, next);
                next.left = e.left;
                next.left.parent = next;
            }
            
            if(b != null) {
                updateInfo(b);
                balance(b);
            }
        }
    }
    
    private TreeEntry entryAt(TreeEntry e, int inx) {
        int rank;
        while((rank = rank(e)) != inx) {
            if(rank < inx) {
                inx = inx - rank;
                e = e.right;
            } else {
                e = e.left;
            }
        }
        return e;
    }
    
    private void transplant(TreeEntry s, TreeEntry t) {
        if(s == root) {
            root = t;
        } else {
            if(s.parent.left == s) {
                s.parent.left = t;
            } else {
                s.parent.right = t;
            }
        }
        
        if(t != null) {
            t.parent = s.parent;
        }
    }
    
    public T minimum(int s, int e) {
        return minimum(root, s, e);
    }
    
    private T minimum(TreeEntry n, int s, int e) {
        if(s == 1 && e == n.size) {
            return n.min;
        } else {
            int rank = rank(n);
            if(s > rank) {
                return minimum(n.right, s - rank, e - rank);
            } else if(e < rank) {
                return minimum(n.left, s, e);
            } else if(s == rank && s == e) {
                return n.val;
            } else if(s == rank) {
                return min(n.val, minimum(n.right, 1, e - rank));
            } else if(e == rank) {
                return min(n.val, minimum(n.left, s, e - 1));
            } else {
                T left = minimum(n.left, s, rank - 1);
                T right = minimum(n.right, 1, e - rank);
                return min(min(left, right), n.val);
            }
        }
    }
    
    public T minimum() {
        return root.min;
    }
    
    public void printTree() {
        printTree(root, 1);
    }
    
    private void printTree(TreeEntry e, int k) {
        if(e.left != null) {
            printTree(e.left, k);
        }
        System.out.println(k + " " + e.val + " " + e.min);
        if(e.right != null) {
            printTree(e.right, rank(e) + k);
        }
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return root == null ? 0 : root.size;
    }
    
    private void balance(TreeEntry e) {
        while(e != null) {
            int ps = size(e) >> 2;
            if(ps > size(e.left)) {
                if(size(e.right.left) > (size(e.right.right) << 1)) {
                    rotateRight(e.right);
                }
                rotateLeft(e);
            } else if(ps > size(e.right)) {
                if(size(e.left.right) > (size(e.left.left) << 1)) {
                    rotateLeft(e.left);
                }
                rotateRight(e);
            } else if(e.parent != null){
                updateInfo(e.parent);
            }
            e = e.parent;
        }
        
    }
    
    private void rotateLeft(TreeEntry n) {
        TreeEntry right = n.right;
        if(n.parent != null) {
            if(n.parent.right == n) {
                n.parent.right = right;
            } else {
                n.parent.left = right;
            }
            right.parent = n.parent;
        } else {
            right.parent = null;
            root = right;
        }
        n.parent = right;
        n.right = right.left;
        right.left = n;
        updateInfo(n);
        updateInfo(right);
    }
    
    private void rotateRight(TreeEntry n) {
        TreeEntry left = n.left;
        if(n.parent != null) {
            if(n.parent.left == n) {
                n.parent.left = left;
            } else {
                n.parent.right = left;
            }
            left.parent = n.parent;
        } else {
            left.parent = null;
            root = left;
        }
        n.parent = left;
        n.left = left.right;
        left.right = n;
        updateInfo(n);
        updateInfo(left);
    }

    private void updateInfo(TreeEntry e) {
        if(e.left == null && e.right == null) {
            e.size = 1;
            e.min = e.val;
        } else if(e.left == null) {
            e.size = e.right.size + 1;
            e.min = min(e.val, e.right.min);
        } else if(e.right == null) {
            e.size = e.left.size + 1;
            e.min = min(e.val, e.left.min);
        } else {
            e.size = e.left.size + e.right.size + 1;
            e.min = min(e.val, min(e.left.min, e.right.min));
        }
    }
            
    private T min(T v1, T v2) {
        return v1.compareTo(v2) <= 0 ? v1 : v2;
    }
    
    private int rank(TreeEntry e) {
        return (e == null || e.left == null) ? ((e == null) ? 0 : 1) : e.left.size + 1; 
    }
    
    private int size(TreeEntry e) {
        return e == null ? 0 : e.size;
    }
    
    private class TreeEntry {
        private int size;
        private T val;
        private T min;
        private TreeEntry left;
        private TreeEntry right;
        private TreeEntry parent;

        public TreeEntry(T val, TreeEntry left, TreeEntry right, TreeEntry parent) {
            this.val = val;
            this.min = val;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.size = 1;
        }
        
        public TreeEntry(T val, TreeEntry left, TreeEntry right, TreeEntry parent, int size) {
            this.val = val;
            this.min = val;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.size = size;
        }
        
    }
}
