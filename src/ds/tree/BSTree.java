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
public class BSTree<K extends Comparable<? super K>, V> extends AbstractTree<K, V, AbstractTree.SimpleTreeEntry> {
    private SimpleTreeEntry<SimpleTreeEntry, K, V> root = null;
    private int size = 0;
    public BSTree() {
        super(null);
    }

    @Override
    public V insert(K key, V value) {
        Objects.requireNonNull(key);
        SimpleTreeEntry<SimpleTreeEntry, K, V> e = endOfSearchEntry(key);
        if (e == null) {
            root = new SimpleTreeEntry<>(key, value, null, null, null);
        } else {
            int c = e.getKey().compareTo(key);
            if (c == 0) {
                V v = e.getValue();
                e.value = value;
                return v;
            } else {
                SimpleTreeEntry<SimpleTreeEntry, K, V> n = new SimpleTreeEntry<>(key, value, null, null, e);
                if (c < 0) {
                    e.right = n;
                } else {
                    e.left = n;
                }
            }
        }
        size++;
        return null;
    }

    @Override
    public V remove(K key) {
        SimpleTreeEntry<SimpleTreeEntry, K, V> e = entry(key);
        if (e != null) {
            if (e.left == null) {
                transplant(e, e.right);
            } else if (e.right == null) {
                transplant(e, e.left);
            } else {
                SimpleTreeEntry<SimpleTreeEntry, K, V> m = treeMin(e.right);
                if (e.right != m) {
                    transplant(m, m.right);
                    m.right = e.right;
                    m.right.parent = m;
                }
                transplant(e, m);
                m.left = e.left;
                m.left.parent = m;
                e.left = e.parent = e.right = null;
            }
            size--;
        }
        return null;
    }

    @Override
    protected SimpleTreeEntry root() {
        return root;
    }

    @Override
    public int size() {
        return size;
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
}
