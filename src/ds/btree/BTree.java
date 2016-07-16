/*
 * Copyright (C) 2016 Shamshad Alam
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
package ds.btree;

/**
 *
 * @author Shamshad Alam
 */
public class BTree<K extends Comparable<? super K>, V> {
    private final int t;
    private final BTreeNode root;
    private int size;
    public BTree(int t) {
        this.t = t;
        this.root = new BTreeNode(t);
    }

    public BTree() {
        this(4);
    }
    
    public void delete(K key) {
        
    }
    
    public void insert(K key) {
        
    }
    
    public boolean exists(K key) {
        BTreeNode node = root;
        do {
            for(int i = 0; i < node.size; i++) {
                int r = key.compareTo((K)node.keys[i]);
                if(r == 0) return true;
                else if(r < 0) {
                    node = node.children[i];
                    break;
                }
            }
            
        } while(!node.leaf);
        return false;
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    private static class BTreeNode {
        private boolean leaf;
        private Object[] keys;
        private Object[] vals;
        private BTreeNode[] children;
        private int size;

        public BTreeNode(int t) {
            this.keys = new Object[t << 1];
            this.vals = new Object[t << 1];
            this.children = new BTreeNode[(t << 1) | 1];
            this.leaf = true;
            this.size = 0;
        }
    }
}
