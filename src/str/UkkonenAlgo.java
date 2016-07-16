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
package str;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author shamshadnpti
 */
public class UkkonenAlgo {
    
    public static Node createTree(String s) {
        int size = s.length(), pos = 0, req = 0, length = 0;
        int[] transform = new int[size];
        for(int i = 0; i < size; i++) {
            transform[i] = s.charAt(i) - 'a' + 1;
        }
        Node root = new Node(-1, 0);
        if(size == 0) {
            return root;
        }
        Node current = root, parent = null, last = null, split;
        NodeIndex end = new NodeIndex(1);
        root.next[transform[0]] = new Node(0, end);
        
        for(int i = 1; i < size; i++) {
            end.index++;
            req++;
            if(pos < current.end.index) {
                if(transform[pos] == transform[i]) {
                    pos++;
                    continue;
                }
                last = null;
                do {
                    split = new Node(current.start.index, pos);
                    split.next[transform[i]] = new Node(i, end);
                    split.next[transform[pos]] = current;
                    current.start.index = pos;
                    parent.next[transform[split.start.index]] = split;
                    length = split.length();
                    if(last != null) {
                        last.suffixLink = split;
                    }
                    last = split;
                    
                    if(parent.suffixLink != null) {
                        parent = parent.suffixLink;
                        current = parent.next[transform[split.start.index]];
                    } else {
                        current = parent.next[transform[split.start.index + 1]];
                        length--;
                    }
                    
                    while(length != 0 && length >= current.length()) {
                        parent = current;
                        length -= current.length();
                        current = current.next[transform[i - length]];
                    }
                    req--;
                    pos = (current != null) ? current.start.index + length : 0;
                } while(length > 0);
                last.suffixLink = parent;
                current = parent;
            }
            
            while(current != null && current.next[transform[i]] == null) {
                current.next[transform[i]] = new Node(i, end);
                current = current.suffixLink;
                req--;
            }
            
            parent = current;
            current = current == null ? root : current.next[transform[i]];
            pos = current.start.index + 1;
        }
        System.out.println("Required : " + req);
        return root;
    }

    private static void printTree(Node n, String s, String p, boolean suffix) {
        if(n.end.index == s.length() && suffix) {
            System.out.println(p);
        } else {
            for(int i = 0; i < n.next.length; i++) {
                Node u = n.next[i];
                if(u != null) {
                    if(!suffix) {
                        System.out.println(p + n + "[" + i + "]->" + u);                        
                        printTree(u, s, p + "-", suffix);                        
                    } else {
                        printTree(u, s, p + s.substring(u.getStart(), u.getEnd()), suffix);                        
                    }
                }
            }
        }
    }
    
    public static void printTree(Node r, String s) {
        printTree(r, s, "", false);
    }
    
    public static void printSuffix(Node r, String s) {
        printTree(r, s, "", true);
    }
    
    public static List<Integer> toSuffixArray(String s) {
        Node n = createTree(s), u;
        int v;
        Stack<Node> st = new Stack<>();
        Stack<Integer> ls = new Stack<>();
        st.push(n);
        ls.push(0);
        List<Integer> ret = new ArrayList<>();
        while(!st.empty()) {
            u = st.pop();
            v = ls.pop();
            if(u.end.index == s.length()) {
                ret.add(s.length() - v);
                continue;
            }
            for(int i = 26; i >= 0; i--) {
                Node w = u.next[i];
                if(w != null) {
                    st.push(w);
                    ls.push(v + w.length());
                }
            }
        }
        return ret;
    }
    
    public static class Node {
        private NodeIndex start;
        private NodeIndex end;
        private Node suffixLink;
        private Node[] next;

        public Node(int start, NodeIndex end, Node suffixLink) {
            this.start = new NodeIndex(start);
            this.end = end;
            this.suffixLink = suffixLink;
            this.next = new Node[27];
        }
        
        public Node(int start, NodeIndex end) {
            this(start, end, null);
        }
        
        public Node(int start, int end) {
            this(start, new NodeIndex(end));
        }
        
        public int length() {
            return end.index - start.index;
        }
        
        public int getStart() {
            return start.index;
        }

        public int getEnd() {
            return end.index;
        }

        public Node[] getNext() {
            return next;
        }

        public Node getSuffixLink() {
            return suffixLink;
        }

        @Override
        public String toString() {
            return "{s:" + start + ", e:" + end + "}";
        }
        
    }
    
    public static class NodeIndex {
        private int index;

        public NodeIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            return "" + index;
        }
        
    }
}
