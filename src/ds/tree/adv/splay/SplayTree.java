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
package ds.tree.adv.splay;

import ds.tree.Tree;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javafx.scene.Node;

/**
 *
 * @author Shamshad Alam
 * @param <K>
 * @param <V>
 */
public class SplayTree<K extends Comparable<? super K>, V> implements Tree<K, V>{
    private TreeEntry<K, V> root;
    private int size = 0;
    
    @Override
    public V insert(K key, V value) {
        TreeEntry<K, V> t = treeEntry(key), e = t;
        if(t == null) {
            root = new TreeEntry(key, value, null, null, null);
            return value;
        }
        int k = key.compareTo(t.key);
        V old = value;
        if(k == 0) {
            old = t.val;
            t.val = value;
        } else {
            e = new TreeEntry(key, value, t, null, null);
            if(k < 0) {
                t.left = e;
            } else {
                t.right = e;
            }
        }
        splay(e);
        ++size;
        return t.val;
    }

    private void splay(TreeEntry<K, V> node) {
        while(node != root) {
            if(node.parent == root) {
                if(isLeftChild(node)) {
                    rotateRight(node.parent);
                } else {
                    rotateLeft(node.parent);
                }
            } else {
                if(isLeftChild(node)) {
                    if(isLeftChild(node.parent)) {
                        rotateRight(node.parent.parent);
                        rotateRight(node.parent);
                    } else {
                        rotateRight(node.parent);
                        rotateLeft(node.parent);
                    }
                } else {
                    if(isRightChild(node.parent)) {
                        rotateLeft(node.parent.parent);
                        rotateLeft(node.parent);
                    } else {
                        rotateLeft(node.parent);
                        rotateRight(node.parent);
                    }
                }
            }
        }
    }
    
    private void rotateLeft(TreeEntry<K, V> node) {
        if(node == null || node.right == null) {
            return;
        }
        TreeEntry<K, V> right = node.right;
        if(isRoot(node)) {
           right.parent = root.parent;
           root = right;
        } else {
            if(isLeftChild(node)) {
                node.parent.left = right;
            } else {
                node.parent.right = right;
            }
            right.parent = node.parent;
        }
        node.parent = right;
        node.right = right.left;
        right.left = node;
    }
    
    private void rotateRight(TreeEntry<K, V> node) {
        if(node == null || node.left == null) {
            return;
        }
        TreeEntry<K, V> left = node.left;
        if(isRoot(node)) {
            left.parent = root.parent;
            root = left;
        } else {
            if(isLeftChild(node)) {
                node.parent.left = left;
            } else {
                node.parent.right = left;
            }
            left.parent = node.parent;
        }
        node.parent = left;
        node.left = left.right;
        left.right = node;
    }
    
    private boolean isLeftChild(TreeEntry<K, V> e) {
        return e != root && e != null && e.parent.left == e;
    }
    
    private boolean isRightChild(TreeEntry<K, V> e) {
        return e != root && e != null && e.parent.right == e;
    }
    
    private boolean isRoot(TreeEntry<K, V> e) {
        return e == root;
    }
    
    private boolean hasParent(TreeEntry<K, V> e) {
        return e != null && e.parent != null;
    }
    
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public K successor(K k) {
        TreeEntry<K, V> e = (TreeEntry<K, V>)successorEntry(k);
        return e == null ? null : e.key;
    }

    @Override
    public K predecessor(K k) {
        TreeEntry<K, V> e = (TreeEntry<K, V>)predecessorEntry(k);
        return e == null ? null : e.key;
    }

    @Override
    public K maximum() {
        return isEmpty() ? null : treeMaximum(root).key;
    }

    @Override
    public K minimum() {
        return isEmpty() ? null : treeMinimum(root).key;
    }

    private TreeEntry<K, V> treeMinimum(TreeEntry<K, V> node) {
        while(node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    private TreeEntry<K, V> treeMaximum(TreeEntry<K, V> node) {
        if(node.right != null) {
            node = node.right;
        }
        return node;
    }
    
    private TreeEntry<K, V> treeEntry(K k) {
        TreeEntry<K, V> t = root, e = root;
        int c = 0;
        while(t != null) {
            c = k.compareTo(t.key);
            if(c == 0) {
                return t;
            }
            e = t;
            t = c < 0 ? t.left : t.right;
        }
        return e;
    }
    
    @Override
    public V get(K key) {
        TreeEntry<K, V> e = treeEntry(key);
        if(e != null && e.key.compareTo(key) == 0) {
            splay(e);
            return e.val;
        }
        return null;
    }

    @Override
    public Entry<K, V> maximumEntry() {
        return treeMaximum(root);
    }

    @Override
    public Entry<K, V> minimumEntry() {
        return treeMinimum(root);
    }

    @Override
    public Entry<K, V> successorEntry(K key) {
        TreeEntry<K, V> e = treeEntry(key);
        if(e == null) {
            return null;
        }
        if(key.compareTo(e.key) >= 0) {
            if(e.right != null) {
                return treeMinimum(e.right);
            }
            while(e != root && isRightChild(e)) {
                e = e.parent;
            }
            return e == root ? null : e.parent;
        }
        return e;
    }

    @Override
    public Entry<K, V> predecessorEntry(K key) {
        TreeEntry<K, V> e = treeEntry(key);
        if(e == null) {
            return null;
        }
        if(key.compareTo(e.key) <= 0) {
            if(e.left != null) {
                return treeMaximum(e.left);
            }
            while(e != root && isLeftChild(e)) {
                e = e.parent;
            }
            return e == root ? null : e.parent;
        }
        return e;
    }

    @Override
    public Iterator<K> ascKeyIterator() {
        return new AscKItr();
    }

    @Override
    public Iterator<K> descKeyIterator() {
        return new DscKItr();
    }

    @Override
    public Iterator<K> inOrderKeyItr() {
        return new AscKItr();
    }

    @Override
    public Iterator<K> preOrderKeyItr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<K> postOrderKeyItr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<K> levelOrderKeyItr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Entry<K, V>> inOrderItr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Entry<K, V>> preOrderItr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Entry<K, V>> postOrderItr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Entry<K, V>> levelOrderItr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Entry<K, V>> ascIterator() {
        return new AscItr();
    }

    @Override
    public Iterator<Entry<K, V>> descIterator() {
        return new DscItr();
    }

    private class DscKItr implements Iterator<K> {
        DscItr itr = new DscItr();

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public K next() {
            return itr.next().getKey();
        }
        
    }
    
    private class PreOrdItr implements Iterator<Entry<K, V>> {
        TreeEntry<K, V> e = root;

        @Override
        public boolean hasNext() {
            return e != null;
        }

        @Override
        public Entry<K, V> next() {
            TreeEntry<K, V> t = e;
            if(e.left != null) {
                e  = e.left;
            } else if(e.right != null) {
                e = e.right;
            } else {
                while(isRightChild(e) || (hasParent(e) && e.parent.right == null)) {
                    e = e.parent;
                }
                if(e != null) {
                    return e;
                } else {
                }
            }
            return t;
        }
        
    }
    private class DscItr implements Iterator<Entry<K, V>> {
        TreeEntry<K, V> e = treeMaximum(root);

        @Override
        public boolean hasNext() {
            return e != null;
        }

        @Override
        public Entry<K, V> next() {
            if(e == null) {
                throw new NoSuchElementException();
            }
            TreeEntry<K, V> t = e;
            if(e.left != null) {
                e = treeMaximum(e.left);
            } else {
                while(isLeftChild(e)) {
                    e = e.parent;
                }
                e = e.parent;
            }
            return t;
        }
        
        
    }
    private class AscKItr implements Iterator<K> {
        AscItr itr = new AscItr();

        @Override
        public K next() {
            return itr.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }
        
    }
    
    private class AscItr implements Iterator<Entry<K, V>> {
        TreeEntry<K, V> e = treeMinimum(root);

        @Override
        public Entry<K, V> next() {
            if(e == null) {
                throw new NoSuchElementException("No such element");
            }
            TreeEntry<K, V> t = e;
            if(e.right != null) {
                e = treeMinimum(e.right);
            } else {
                while(isRightChild(e)) {
                    e = e.parent;
                }
                e = e.parent;
            }
            return t;
        }

        @Override
        public boolean hasNext() {
            return e != null;
        }
        
    }
    
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Node draw() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static class TreeEntry<K extends Comparable<? super K>, V> implements Entry<K, V>{
        private K key;
        private V val;
        private TreeEntry<K, V> parent;
        private TreeEntry<K, V> left;
        private TreeEntry<K, V> right;

        public TreeEntry(K key, V val, TreeEntry<K, V> parent, TreeEntry<K, V> left, TreeEntry<K, V> right) {
            this.key = key;
            this.val = val;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }

        @Override
        public void setValue(V value) {
            this.val = value;
        }
        
    }    
}
