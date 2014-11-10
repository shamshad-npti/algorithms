/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author shamshad
 * @param <E>
 */
public class BinaryTree<E>  {
    private final Tree<E> tree;
    private final Comparator<E> com;

    public BinaryTree(Comparator<E> com) {
        this.tree = new Tree<>();
        this.com = com;
    }
    
    public E treeMin() {
        return isEmpty() ? null : treeMin(this.tree.root).elem;
    }
    
    public E treeMax() {
        return isEmpty() ? null : treeMax(this.tree.root).elem;
    }
    
    public void insert(E elem) {
        Node<E> n = tree.root, y = null;
        Node<E> nn = new Node<>(elem, null, null, null);
        while(n != null) {
            y = n;
            n = com.compare(elem, n.elem) < 0 ? n.left : n.right;
        }
        nn.parent = y;
        nn.size = 1;
        if(y == null) tree.root = nn;
        else if(com.compare(elem, y.elem) < 0) y.left = nn;
        else y.right = nn;
        balance(nn);
    }
    
    public void delete(E elem) {
        if(!isEmpty()) {
            Node<E> n = node(elem);
            if(n == null) return;
            if(n.left == null)
                transplant(n, n.right);
            else if(n.right == null)
                transplant(n, n.left);
            else {
                Node<E> e = treeMin(n.right);
                Node<E> ep = e.parent;
                if(ep != n) {
                    transplant(e, e.right);
                    e.right = n.right;
                    e.right.parent = e;
                }
                transplant(n, e);
                e.left = n.left;
                e.left.parent = e;
                n = ep; // balance to work
            }
            balance(n);
        }
    }
    
    public E successor(E elem) {
        if(!isEmpty()) {
            Node<E> n = node(elem);
            if(n != null) {
                if(n.right != null) return treeMin(n.right).elem;
                Node<E> p = n.parent;
                while(p != null && p.right == n) {
                    n = p;
                    p = p.parent;
                }
                return p == null ? null : p.elem;
            }
        }
        return null;
    }
    
    public E predecessor(E elem) {
        if(!isEmpty()) {
            Node<E> n = node(elem);
            if(n != null) {
                if(n.left != null) return treeMax(n.left).elem;
                Node<E> p = n.parent;
                while(p != null && p.left == n) {
                    n = p;
                    p = p.parent;
                }
                return p != null ? p.elem : null;
            }
        }
        return null;
    }
    
    public boolean isEmpty() {
        return this.tree == null || this.tree.root.size == 0;
    }
    
    public int size() {
        return this.tree == null ? 0 : this.tree.root.size;
    }
    
    public int height() {
        return height(tree.root);
    }
    
    public Iterator<E> inorder() {
        return new InOrderIter();
    }
    
    private void rotateLeft(Node<E> n) {
        Node<E> r = n.right;
        n.right = r.left;
        r.left = n;
        r.parent = n.parent;
        n.parent = r;
        if(n.right != null) n.right.parent = n;
        if(r.parent != null)
            if(r.parent.left == n) r.parent.left = r;
            else r.parent.right = r;
        if(tree.root == n) tree.root = r;
        updateSize(n);
        updateHeight(n);
        updateSize(r);
        updateHeight(r);
    }
    
    private void rotateRight(Node<E> n) {
        Node<E> l = n.left;
        n.left = l.right;
        l.right = n;
        l.parent = n.parent;
        n.parent = l;
        if(n.left != null) n.left.parent = n;
        if(l.parent != null)
            if(l.parent.left == n) l.parent.left = l;
            else l.parent.right = l;
        if(tree.root == n) tree.root = l;
        updateSize(n);
        updateHeight(n);
        updateSize(l);
        updateHeight(l);
    }
    
    private void balance(Node<E> n) {
        if(n != null) {
            int h = height(n.left) - height(n.right);
            if(h == -2) {
                if(height(n.right.left) - height(n.right.right) == 1)
                    rotateRight(n.right);
                rotateLeft(n);
            }
            else if(h == 2) {
                if(height(n.left.left) - height(n.left.right) == -1)
                    rotateLeft(n.left);
                rotateRight(n);
            } else {
                updateHeight(n);
                updateSize(n);
            }
            balance(n.parent);
        }
    }
        
    private byte height(Node<E> n) {
        return n == null ? -1 : n.height;
    }
    
    private int size(Node<E> e) {
        return e == null ? 0 : e.size;
    }
    
    private void updateHeight(Node<E> n) {
        if(n != null) n.height = (byte)(1 + Math.max(height(n.left), height(n.right)));
    }
    
    private void updateSize(Node<E> n) {
        if(n != null) n.size = size(n.left) + size(n.right) + 1;
    }
    
    private void updateSizes(Node<E> n) {
        while(n != null) {
            n.size = size(n.left) + size(n.right) + 1;
            n = n.parent;
        }
    }
    
    private void transplant(Node<E> u, Node<E> v) {
        if(u.parent == null) this.tree.root = v;
        else if(u.parent.left == u) u.parent.left = v;
        else u.parent.right = v;
        if(v != null) v.parent = u.parent;
    }
    
    private Node<E> node(E e) {
        Node<E> n = tree.root;
        int c;
        while(n != null) {
            c = com.compare(e, n.elem);
            if(c < 0) n = n.left;
            else if(c > 0) n = n.right;
            else return n;
        }
        return null;
    }
    
    private Node<E> treeMin(Node<E> root) {
        if(root == null) return null;
        while(root.left != null)
            root = root.left;
        return root;
    }
    
    private Node<E> treeMax(Node<E> root) {
        if(root == null) return null;
        while(root.right != null)
            root = root.right;
        return root;
    }

    private class InOrderIter implements Iterator<E> {
        private final java.util.Stack<Node<E>> stack;
        public InOrderIter() {
            stack = new java.util.Stack<>();
            if(!isEmpty()) {
                Node<E> n = tree.root;
                while(n != null) {
                    stack.push(n);
                    n = n.left;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            Node<E> n = stack.pop();
            if(n.right != null) {
                Node<E> e = n.right;
                while(e != null) {
                    stack.push(e);
                    e = e.left;
                }
            } 
            return n.elem;
        }
        
    }
    private static class Tree<T> {
        private Node<T> root;
    }
    
    private static class Node<T> {
        private T elem;
        private int size;
        private byte height;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;

        public Node(T elem, Node<T> left, Node<T> right, Node<T> parent) {
            this.elem = elem;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
        
    }
}
