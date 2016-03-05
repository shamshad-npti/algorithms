/*
 * Copyright (C) 2016 shamshad
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
package ds.tree;

import java.util.Random;

/**
 *
 * @author Shamshad Alam
 */
public class SkipList {
    private int size;
    private Node root;

    public SkipList() {
        this.size = 0;
        this.root = new Node(Integer.MIN_VALUE, null, null, null, null, null);
    }
    
    public void insert(int key, Object val) {
        Node t = root;
        while(t.key < key) {
            if(t.next == null || t.next.key > t.key) {
                if(t.down == null) {
                    break;
                } else {
                    t = t.down;
                }
            } else {
                t = t.next;
            }
        }
        Node n = new Node(key, val, t.next, null, null, t);
        if(t.next != null) {
            t.next.prev = n;
        }
        t.next = n;
        while(toss()) {
            while(t.up == null) {
                if(t.prev == null) {
                    break;
                }
                t = t.prev;
            }
            if(t.up == null) {
                t.up = new Node(Integer.MIN_VALUE, null, t.next, t, null, t.prev);
            }
            t = t.up;
            Node nn = new Node(key, val, t.next, n, null, t);
            t.next.prev = nn;
            t.next = nn;
            n.up = nn;
            n = nn;
        }
        ++size;
    }
    
    public boolean exists(int key) {
        Node t = searchEndNode(key);
        return t != null;
    }
    
    public Object get(int key) {
        Node n = searchEndNode(key);
        return n == null ? null : n.val;
    }
    
    private Node searchEndNode(int key) {
        Node t = root;
        while(t.key < key) {
            if(t.next == null) {
                return null;
            }
            if(t.next.key > key) {
                if(t.down == null) {
                    return null;
                }
                t = t.down;
            } else {
                t = t.next;
            }
        }
        return t;
    }
    
    public void printStats() {
        
    }
    
    public int size() {
        return size;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean toss() {
        return R.nextBoolean();
    }
    
    private static final Random R = new Random();
    
    private static class Node {
        int key;
        Object val;
        Node next;
        Node down;
        Node up;
        Node prev;
        public Node(int key, Object val, Node next, Node down, Node up, Node prev) {
            this.key = key;
            this.val = val;
            this.next = next;
            this.down = down;
            this.up = up;
            this.prev = prev;
        }
        
    }
}
