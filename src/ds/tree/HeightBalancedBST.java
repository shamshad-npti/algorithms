/*
 * Copyright (C) 2015 Shamshad Alam
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

import java.util.Objects;

/**
 *
 * @author Shamshad Alam
 * @param <K>
 * @param <V>
 */
public class HeightBalancedBST<K extends Comparable<? super K>, V> extends BalancedAbstractTree<K, V, HeightBalancedBST.HBTreeEntry> {
    private SimpleTreeEntry root;
    private int size;

    public HeightBalancedBST() {
        super(null);
    }

    @Override
    public V insert(K key, V value) {
        Objects.requireNonNull(key);
        SimpleTreeEntry k = endOfSearchEntry(key);
        if (k == null) {
            root = new HBTreeEntry(key, value, null, null, null);
        } else {
            HBTreeEntry<?> e = (HBTreeEntry) k;
            int c = e.getKey().compareTo(key);
            if (c == 0) {
                V v = e.getValue();
                e.value = value;
                return v;
            } else {
                HBTreeEntry n = new HBTreeEntry(key, value, null, null, e);
                if (c < 0) {
                    e.right = n;
                } else {
                    e.left = n;
                }
                balance(n);
            }
        }
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        SimpleTreeEntry k = entry(key);
        if (k != null) {
            HBTreeEntry<?> e = (HBTreeEntry) k;
            if (e.left == null) {
                transplant(e, e.right);
            } else if (e.right == null) {
                transplant(e, e.left);
            } else {
                HBTreeEntry m = (HBTreeEntry) treeMin(e.right);
                HBTreeEntry x = (HBTreeEntry) (m.right == sentinel() ? m.parent : m.right);
                if (e.right != m) {
                    transplant(m, m.right);
                    m.right = e.right;
                    m.right.parent = m;
                }
                transplant(e, m);
                m.left = e.left;
                m.left.parent = m;
                e = (HBTreeEntry) x;
            }
            size--;
            balance(e);
        }
        return null;
    }

    @Override
    protected SimpleTreeEntry<HeightBalancedBST.HBTreeEntry, K, V> root() {
        return root;
    }

    @Override
    public int size() {
        return size;
    }

    public int height() {
        return root == null ? -1 : height((HBTreeEntry) root);
    }

    private void balance(HBTreeEntry<?> e) {
        if (e != null) {
            int h = height(e.left) - height(e.right);
            if (Math.abs(h) == 2) {
                if (h == -2) {
                    HBTreeEntry<?> r = e.right;
                    if (height(r.left) - height(r.right) == 1) {
                        rotateRight(r);
                        updateHeight(r);
                        updateHeight(r.parent);
                    }
                    rotateLeft(e);
                } else if (h == 2) {
                    HBTreeEntry<?> l = e.left;
                    if (height(l.right) - height(l.left) == 1) {
                        rotateLeft(l);
                        updateHeight(l);
                        updateHeight(l.parent);
                    }
                    rotateRight(e);
                }
            }
            updateHeight(e);
            updateHeight(e.parent);
            balance(e.parent);
        }
    }

    public static boolean isHBTRee(AbstractTree tree) {
        SimpleTreeEntry root = tree.root();
        SimpleTreeEntry s = tree.sentinel();
        return root == s ? true : Math.abs(height(root.left, s) - height(root.right, s)) <= 2;
    }

    private static int height(SimpleTreeEntry e, SimpleTreeEntry s) {
        if (e == null) {
            return -1;
        } else {
            return 1 + Math.max(height(e.left, s), height(e.right, s));
        }
    }

    @Override
    protected void setRoot(SimpleTreeEntry<HBTreeEntry, K, V> e) {
        this.root = e;
    }

    private void transplant(SimpleTreeEntry e, SimpleTreeEntry n) {
        if (e.parent == null) {
            root = n;
        } else {
            if (isLeftChild(e)) {
                e.parent.left = n;
            } else {
                e.parent.right = n;
            }
        }
        if (n != null) {
            n.parent = e.parent;
        }
    }

    private void updateHeight(HBTreeEntry<?> node) {
        if (node != null) {
            node.height = (byte) (1 + Math.max(height(node.left), height(node.right)));
        }
    }

    private byte height(HBTreeEntry node) {
        return node != null ? node.height : -1;
    }

    protected class HBTreeEntry<E extends AbstractTree.SimpleTreeEntry<E, K, V>> extends AbstractTree.SimpleTreeEntry<HBTreeEntry<E>, K, V> {
        private byte height;

        public HBTreeEntry(K key, V value, HBTreeEntry left, HBTreeEntry right, HBTreeEntry parent) {
            super(key, value, left, right, parent);
            this.height = 0;
        }

    }
}
