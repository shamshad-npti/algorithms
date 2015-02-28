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
import javafx.scene.paint.Color;

/**
 *
 * @author Shamshad Alam
 * @param <K>
 * @param <V>
 */
public class RedBlackTree<K extends Comparable<? super K>, V> extends BalancedAbstractTree<K, V, RedBlackTree.RBTreeEntry> {
    private RBTreeEntry<?, K, V> root;
    private int size;
    private static final boolean RED = true;
    private static final boolean BLACK = !RED;
    private static final RBTreeEntry sentinel = new RBTreeEntry();

    public RedBlackTree() {
        super(sentinel);
        //sentinel.right = sentinel.left = sentinel.parent = sentinel;
        this.root = sentinel;
    }

    @Override
    public V insert(K key, V value) {
        Objects.requireNonNull(key);
        SimpleTreeEntry k = endOfSearchEntry(key);
        if (k == sentinel) {
            root = new RBTreeEntry(key, value, BLACK, sentinel, sentinel, sentinel);
        } else {
            RBTreeEntry<?, K, V> e = (RBTreeEntry) k;
            int c = e.getKey().compareTo(key);
            if (c == 0) {
                V v = e.getValue();
                e.value = value;
                return v;
            } else {
                RBTreeEntry n = new RBTreeEntry(key, value, RED, sentinel, sentinel, e);
                if (c < 0) {
                    e.right = n;
                } else {
                    e.left = n;
                }
                insertFixUp(n);
            }
        }
        size++;
        return null;
    }

    private void insertFixUp(RBTreeEntry<?, K, V> e) {
        while (e.parent.color == RED) {
            if (isLeftChild(e.parent)) {
                RBTreeEntry<?, K, V> y = e.parent.parent.right;
                if (y.color == RED) {
                    y.color = e.parent.color = BLACK;
                    e = e.parent.parent;
                    e.color = RED;
                } else {
                    if (isRightChild(e)) {
                        e = e.parent;
                        rotateLeft(e);
                    }
                    e.parent.color = BLACK;
                    e.parent.parent.color = RED;
                    rotateRight(e.parent.parent);
                }
            } else {
                RBTreeEntry<?, K, V> y = e.parent.parent.left;
                if (y.color == RED) {
                    y.color = e.parent.color = BLACK;
                    e = e.parent.parent;
                    e.color = RED;
                } else {
                    if (isLeftChild(e)) {
                        e = e.parent;
                        rotateRight(e);
                    }
                    e.parent.color = BLACK;
                    e.parent.parent.color = RED;
                    rotateLeft(e.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    @Override
    public V remove(K key) {
        SimpleTreeEntry k = super.entry(key);
        if (k != sentinel) {
            RBTreeEntry<?, K, V> e = (RBTreeEntry) k;
            RBTreeEntry<?, K, V> x;
            boolean b = e.color;
            if (e.left == sentinel) {
                x = e.right;// == sentinel ? e.parent : e.right;
                transplant(e, e.right);
            } else if (e.right == sentinel) {
                x = e.left;
                transplant(e, e.left);
            } else {
                RBTreeEntry<?, K, V> y = (RBTreeEntry) treeMin(e.right);
                b = y.color;
                x = y.right;
                if (y.parent != e) {
                    transplant(y, y.right);
                    y.right = (RBTreeEntry) e.right;
                }
                y.right.parent = (RBTreeEntry) y;
                transplant(e, y);
                y.left = (RBTreeEntry) e.left;
                y.left.parent = (RBTreeEntry) y;
                y.color = e.color;
            }
            if (b == BLACK) {
                deleteFixUp(x);
            }
        }
        return null;
    }

    private void deleteFixUp(RBTreeEntry<?, K, V> e) {
        while (e != root && e.color == BLACK) {
            if (isLeftChild(e)) {
                RBTreeEntry<?, K, V> w = e.parent.right;
                if (w.color == RED) {
                    w.color = BLACK;
                    e.parent.color = RED;
                    rotateLeft(e.parent);
                    w = e.parent.right;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    e = e.parent;
                } else {
                    if (w.right.color == BLACK) {
                        w.left.color = BLACK;
                        w.color = RED;
                        rotateRight(w);
                        w = e.parent.right;
                    }
                    w.color = e.parent.color;
                    e.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateLeft(e.parent);
                    e = root;
                }
            } else {
                RBTreeEntry<?, K, V> w = e.parent.left;
                if (w.color == RED) {
                    w.color = BLACK;
                    e.parent.color = RED;
                    rotateRight(e.parent);
                    w = w.parent.left;
                }
                if (w.left.color == BLACK && w.right.color == BLACK) {
                    w.color = RED;
                    e = e.parent;
                } else {
                    if (w.left.color == BLACK) {
                        w.right.color = BLACK;
                        w.color = RED;
                        rotateLeft(w);
                        w = e.parent.left;
                    }
                    w.color = e.parent.color;
                    e.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateRight(e.parent);
                    e = root;
                }
            }
        }
        e.color = BLACK;
    }

    private void transplant(RBTreeEntry e, RBTreeEntry n) {
        if (e.parent == sentinel) {
            root = n;
        }
        if (isLeftChild(e)) {
            e.parent.left = n;
        } else if (isRightChild(e)) {
            e.parent.right = n;
        }
        n.parent = e.parent;
        if (n != sentinel) {
        }
    }

    @Override
    protected void setRoot(SimpleTreeEntry<RedBlackTree.RBTreeEntry, K, V> e) {
        this.root = (RBTreeEntry) e;
    }

    @Override
    protected SimpleTreeEntry root() {
        return root;
    }

    @Override
    protected Color getNodeColor(SimpleTreeEntry node) {
        return ((RBTreeEntry) node).color == BLACK ? Color.BLACK : Color.RED;
    }

    @Override
    public int size() {
        return size;
    }

    public boolean checkRedBlack() {
        int black = 0;
        RBTreeEntry<?, K, V> e = this.root;
        while (e != sentinel) {
            if (e.color == BLACK) {
                black++;
            }
            e = e.left;
        }
        return check(root.left, black - 1) && check(root.right, black - 1);
    }

    private boolean check(RBTreeEntry<?, K, V> e, int b) {
        if (e == sentinel) {
            return b == 0;
        } else if (e.color == RED) {
            return e.parent.color == BLACK && check(e.left, b) && check(e.right, b);
        } else {
            return check(e.left, b - 1) && check(e.right, b - 1);
        }
    }

    protected static class RBTreeEntry<E extends AbstractTree.SimpleTreeEntry<E, K, V>, K extends Comparable<? super K>, V> extends AbstractTree.SimpleTreeEntry<RBTreeEntry<E, K, V>, K, V> {
        private boolean color = RED;

        public RBTreeEntry(K key, V value, boolean color, RBTreeEntry left, RBTreeEntry right, RBTreeEntry parent) {
            super(key, value, left, right, parent);
            this.color = color;
        }

        public RBTreeEntry() {
            this(null, null, BLACK, null, null, null);
        }


        private void flipColor() {
            this.color = !this.color;
        }
    }

}
