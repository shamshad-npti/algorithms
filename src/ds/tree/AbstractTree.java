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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author Shamshad Alam
 * @param <E>
 * @param <K>
 * @param <V>
 */
public abstract class AbstractTree<K extends Comparable<? super K>, V, E extends AbstractTree.SimpleTreeEntry> implements Tree<K, V> {

    private final SimpleTreeEntry<E, K, V> sentinel;

    public AbstractTree(SimpleTreeEntry<E, K, V> sentinel) {
        this.sentinel = sentinel;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public K successor(K k) {
        return key(successorEntry(k));
    }

    @Override
    public Entry<K, V> successorEntry(K k) {
        SimpleTreeEntry<E, K, V> e = endOfSearchEntry(k);
        return e != sentinel && e.key.compareTo(k) <= 0 ? successor(e) : e;
    }

    protected SimpleTreeEntry<E, K, V> successor(SimpleTreeEntry<E, K, V> e) {
        if (e.right != sentinel) {
            return treeMin(e.right);
        } else {
            while (e.parent != sentinel && e == e.parent.right) {
                e = e.parent;
            }
            return e.parent;
        }
    }

    @Override
    public K predecessor(K k) {
        return key(predecessorEntry(k));
    }

    @Override
    public Entry<K, V> predecessorEntry(K key) {
        SimpleTreeEntry<E, K, V> e = endOfSearchEntry(key);
        return e != sentinel && e.key.compareTo(key) >= 0 ? predecessor(e) : e;
    }

    protected SimpleTreeEntry<E, K, V> predecessor(SimpleTreeEntry<E, K, V> e) {
        if (e.left != sentinel) {
            return treeMax(e.left);
        } else {
            while (e.parent != sentinel && e == e.parent.left) {
                e = e.parent;
            }
            return e.parent;
        }
    }

    @Override
    public K maximum() {
        return key(maximumEntry());
    }

    @Override
    public Entry<K, V> maximumEntry() {
        return treeMax(this.root());
    }

    @Override
    public K minimum() {
        return key(minimumEntry());
    }

    @Override
    public Entry<K, V> minimumEntry() {
        return treeMin(this.root());
    }

    @Override
    public V get(K key) {
        SimpleTreeEntry<E, K, V> e = entry(key);
        return e == sentinel ? null : e.getValue();
    }

    @Override
    public boolean contains(K key) {
        return entry(key) != sentinel;
    }

    @Override
    public Iterator<Entry<K, V>> inOrderItr() {
        return new InorderItr();
    }

    @Override
    public Iterator<K> inOrderKeyItr() {
        return new InorderKeyItr();
    }

    @Override
    public Iterator<Entry<K, V>> ascIterator() {
        return inOrderItr();
    }

    @Override
    public Iterator<K> ascKeyIterator() {
        return inOrderKeyItr();
    }

    @Override
    public Iterator<Entry<K, V>> descIterator() {
        return new DescItr();
    }

    @Override
    public Iterator<K> descKeyIterator() {
        return new DescKeyItr();
    }

    @Override
    public Iterator<Entry<K, V>> preOrderItr() {
        return new PreOrderItr();
    }

    @Override
    public Iterator<K> preOrderKeyItr() {
        return new PreOrderKeyItr();
    }

    @Override
    public Iterator<Entry<K, V>> postOrderItr() {
        return new PostOrderItr();
    }

    @Override
    public Iterator<K> postOrderKeyItr() {
        return new PostOrderKeyItr();
    }

    @Override
    public Iterator<Entry<K, V>> levelOrderItr() {
        return new LevelOrderItr();
    }

    @Override
    public Iterator<K> levelOrderKeyItr() {
        return new LevelOrderKeyItr();
    }

    @Override
    public Node draw() {
        int r = 1296;
        ScrollPane sp = new ScrollPane();
        Pane pane = new AnchorPane();
        sp.setContent(pane);
        BufferedImage bi = new BufferedImage(r, 600, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = bi.createGraphics();
        if (root() != sentinel) {
            draw(root(), pane, 0, r, 1);
        }
        return sp;
    }

    private void draw(SimpleTreeEntry node, Pane g, int left, int right, int level) {
        if (node != sentinel) {
            int mid = (left + right) >> 1;
            int top = level * 60;
            int t = (level - 1) * 60;
            Line line = new Line();
            line.setStroke(Color.BLUE);
            Label label = new Label(node.getKey().toString());
            if (isLeftChild(node)) {
                line.setStartX(right);
                line.startYProperty().bind(label.heightProperty().add(t));
                line.endXProperty().bind(label.widthProperty().add(mid));
                line.setEndY(top);
                g.getChildren().add(line);
            } else if (isRightChild(node)) {
                line.startXProperty().bind(label.widthProperty().add(left));
                line.startYProperty().bind(label.heightProperty().add(t));
                line.setEndX(mid);
                line.setEndY(top);
                g.getChildren().add(line);
            }
            label.setBorder(new Border(new BorderStroke(getNodeColor(node), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            label.setLayoutX(mid);
            label.setLayoutY(top);
            g.getChildren().add(label);
            draw(node.left, g, left, mid, level + 1);
            draw(node.right, g, mid, right, level + 1);
        }
    }

    protected Color getNodeColor(SimpleTreeEntry node) {
        return Color.BLACK;
    }

    public boolean isBST() {
        return isBST(root());
    }

    private boolean isBST(SimpleTreeEntry e) {
        if (e != sentinel) {
            boolean bst = true;
            if (e.left != sentinel) {
                bst = (e.left.key.compareTo(e.key) < 0) && isBST(e.left);
            }
            if (bst && e.right != sentinel) {
                bst = e.right.key.compareTo(e.key) > 0 && isBST(e.right);
            }
            return bst;
        }
        return true;
    }

    protected SimpleTreeEntry<E, K, V> treeMin(SimpleTreeEntry e) {
        while (e.left != sentinel) {
            e = e.left;
        }
        return e;
    }

    protected SimpleTreeEntry<E, K, V> treeMax(SimpleTreeEntry e) {
        while (e.right != sentinel) {
            e = e.right;
        }
        return e;
    }

    protected SimpleTreeEntry<E, K, V> entry(K key) {
        if (key == null) {
            return sentinel;
        }

        SimpleTreeEntry<E, K, V> entry = this.root();
        while (entry != sentinel) {
            int c = key.compareTo(entry.key);
            if (c == 0) {
                return entry;
            }
            entry = c < 0 ? entry.left : entry.right;
        }
        return sentinel;
    }

    protected SimpleTreeEntry<E, K, V> endOfSearchEntry(K key) {
        if (key == null) {
            return sentinel;
        }

        SimpleTreeEntry<E, K, V> entry = this.root();
        SimpleTreeEntry<E, K, V> parent = sentinel;

        while (entry != sentinel) {
            int c = key.compareTo(entry.key);
            parent = entry;
            if (c == 0) {
                break;
            }
            entry = c < 0 ? entry.left : entry.right;
        }
        return parent;
    }

    private K key(Entry<K, V> e) {
        return e == sentinel() ? null : e.getKey();
    }

    protected boolean isLeftChild(SimpleTreeEntry e) {
        return e.parent != sentinel && e.parent.left == e;
    }

    protected boolean isRightChild(SimpleTreeEntry e) {
        return e.parent != sentinel && e.parent.right == e;
    }

    protected boolean isRoot(SimpleTreeEntry e) {
        return root() == e;
    }

    protected final SimpleTreeEntry<E, K, V> sentinel() {
        return sentinel;
    }

    protected abstract SimpleTreeEntry root();

    private class LevelOrderKeyItr implements Iterator<K> {

        private final LevelOrderItr itr = new LevelOrderItr();

        @Override
        public K next() {
            return itr.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

    }

    private class LevelOrderItr implements Iterator<Entry<K, V>> {
        private final Queue<SimpleTreeEntry<E, K, V>> queue = new LinkedList<>();

        public LevelOrderItr() {
            queue.add(root());
        }

        @Override
        public Entry<K, V> next() {
            SimpleTreeEntry<E, K, V> k = queue.remove();
            if (k.left != sentinel) {
                queue.add(k.left);
            }
            if (k.right != sentinel) {
                queue.add(k.right);
            }
            return k;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

    }

    private class InorderKeyItr implements Iterator<K> {
        private final InorderItr itr = new InorderItr();

        @Override
        public K next() {
            return itr.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }
    }

    private class InorderItr implements Iterator<Entry<K, V>> {
        private SimpleTreeEntry<E, K, V> e = treeMin(root());

        @Override
        public Entry<K, V> next() {
            Entry<K, V> k = e;
            e = successor(e);
            return k;
        }

        @Override
        public boolean hasNext() {
            return e != sentinel;
        }

    }

    private class DescKeyItr implements Iterator<K> {

        private final DescItr itr = new DescItr();

        @Override
        public K next() {
            return itr.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

    }
    private class DescItr implements Iterator<Entry<K, V>> {

        private SimpleTreeEntry<E, K, V> e = treeMax(root());

        @Override
        public Entry<K, V> next() {
            Entry<K, V> k = e;
            e = predecessor(e);
            return k;
        }

        @Override
        public boolean hasNext() {
            return e != sentinel;
        }

    }

    private class PreOrderKeyItr implements Iterator<K> {

        private final PreOrderItr itr = new PreOrderItr();

        @Override
        public K next() {
            return itr.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

    }

    private class PreOrderItr implements Iterator<Entry<K, V>> {
        private SimpleTreeEntry<E, K, V> e = root();

        @Override
        public Entry<K, V> next() {
            Entry<K, V> k = e;
            if (e.left != sentinel) {
                e = e.left;
            } else if (e.right != sentinel) {
                e = e.right;
            } else {
                while (e != sentinel) {
                    if (isLeftChild(e) && e.parent.right != sentinel) {
                        e = e.parent.right;
                        break;
                    }
                    e = e.parent;
                }
            }
            return k;
        }

        @Override
        public boolean hasNext() {
            return e != sentinel;
        }

    }

    private class PostOrderKeyItr implements Iterator<K> {

        private final PostOrderItr itr = new PostOrderItr();

        @Override
        public K next() {
            return itr.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

    }

    private class PostOrderItr implements Iterator<Entry<K, V>> {
        private SimpleTreeEntry<E, K, V> e = null;

        public PostOrderItr() {
            e = next(root());
        }

        private SimpleTreeEntry<E, K, V> next(SimpleTreeEntry<E, K, V> e) {
            SimpleTreeEntry<E, K, V> k = e;
            e = (e.left != sentinel) ? e.left : e.right;
            while (e != sentinel) {
                k = e;
                e = e.left != sentinel ? e.left : e.right;
            }
            return k;
        }

        @Override
        public Entry<K, V> next() {
            Entry<K, V> k = e;
            if (isLeftChild(e)) {
                e = e.parent.right == sentinel ? e.parent : next(e.parent.right);
            } else if (isRightChild(e)) {
                e = e.parent;
            } else {
                e = sentinel;
            }
            return k;
        }

        @Override
        public boolean hasNext() {
            return e != sentinel;
        }

    }

    protected static class SimpleTreeEntry<E extends AbstractTree.SimpleTreeEntry, K extends Comparable<? super K>, V> implements Entry<K, V> {
        protected K key;
        protected V value;
        protected E left;
        protected E right;
        protected E parent;

        public SimpleTreeEntry(K key, V value, E left, E right, E parent) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        Iterator<Entry<K, V>> itr = inOrderItr();
        buffer.append("[");
        while (itr.hasNext()) {
            Entry<K, V> next = itr.next();
            buffer.append(next.getKey()).append("=").append(next.getValue());
            if (itr.hasNext()) {
                buffer.append(", ");
            }
        }
        return buffer.append("]").toString();
    }

}
