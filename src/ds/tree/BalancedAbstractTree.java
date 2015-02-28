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

/**
 *
 * @author Shamshad Alam
 * @param <E>
 * @param <K>
 * @param <V>
 */
public abstract class BalancedAbstractTree<K extends Comparable<? super K>, V, E extends AbstractTree.SimpleTreeEntry> extends AbstractTree<K, V, E> {

    public BalancedAbstractTree(SimpleTreeEntry sentinel) {
        super(sentinel);
    }

    protected void rotateLeft(E e) {
        SimpleTreeEntry r = e.right;
        if (isLeftChild(e)) {
            e.parent.left = r;
        } else if (isRightChild(e)) {
            e.parent.right = r;
        } else {
            setRoot(r);
        }
        r.parent = e.parent;
        e.right = r.left;
        if (e.right != sentinel()) {
            e.right.parent = e;
        }
        r.left = e;
        e.parent = r;

    }

    protected void rotateRight(E e) {
        SimpleTreeEntry l = e.left;
        if (isLeftChild(e)) {
            e.parent.left = l;
        } else if (isRightChild(e)) {
            e.parent.right = l;
        } else {
            setRoot(l);
        }
        l.parent = e.parent;
        e.left = l.right;
        if (e.left != sentinel()) {
            e.left.parent = e;
        }
        l.right = e;
        e.parent = l;
    }

    protected abstract void setRoot(SimpleTreeEntry<E, K, V> e);
}
