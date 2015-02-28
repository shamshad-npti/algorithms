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

import java.util.Iterator;
import javafx.scene.Node;

/**
 *
 * @author Shamshad Alam
 * @param <K>
 * @param <V>
 */
public interface Tree<K extends Comparable<? super K>, V> {
    public V insert(K key, V value);
    public V remove(K key);
    public K successor(K k);
    public K predecessor(K k);
    public K maximum();
    public K minimum();
    public V get(K key);
    public Entry<K, V> maximumEntry();
    public Entry<K, V> minimumEntry();
    public Entry<K, V> successorEntry(K key);
    public Entry<K, V> predecessorEntry(K key);
    public Iterator<K> ascKeyIterator();
    public Iterator<K> descKeyIterator();
    public Iterator<K> inOrderKeyItr();
    public Iterator<K> preOrderKeyItr();
    public Iterator<K> postOrderKeyItr();
    public Iterator<K> levelOrderKeyItr();
    public Iterator<Entry<K, V>> inOrderItr();
    public Iterator<Entry<K, V>> preOrderItr();
    public Iterator<Entry<K, V>> postOrderItr();
    public Iterator<Entry<K, V>> levelOrderItr();
    public Iterator<Entry<K, V>> ascIterator();
    public Iterator<Entry<K, V>> descIterator();
    public boolean contains(K key);
    public boolean isEmpty();
    public int size();
    public Node draw();

    public interface Entry<K extends Comparable<? super K>, V> {
        public K getKey();
        public V getValue();
        public void setValue(V value);
    }
}
