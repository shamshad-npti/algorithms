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
package ds.hashtable;

import ds.linkedlistimp.DoublyLinkedList;
import ds.linkedlistimp.LinkedList;
import java.util.Objects;

/**
 *
 * @author Shamshad Alam
 */
public class ChainedHashTable<K, V> extends AbstractHashTable<K, V> {
    private final HashFunction<K> hashFunction;
    private final DoublyLinkedList<Entry<K, V>>[] table;
    private final int capacity = 14387;
    private int size;

    public ChainedHashTable() {
        this.table = new DoublyLinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new DoublyLinkedList<>();
        }
        hashFunction = new DivisionHashFunction<>();
    }

    public void stats() {
        double sum = size;
        double dev = 0.0;
        double mean = sum / capacity;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, emp = 0;
        for (DoublyLinkedList<Entry<K, V>> t : table) {
            dev += Math.pow(mean - t.size(), 2);
            min = Math.min(min, t.size());
            max = Math.max(max, t.size());
            emp = t.size() == 0 ? emp + 1 : emp;
        }
        System.out.println("Minimum chain length : " + min);
        System.out.println("Maximum chain length : " + max);
        System.out.println("Number of empty slot : " + emp);
        System.out.println("Standard Deviation : " + String.format("%.2f", Math.sqrt(dev / capacity)));
    }

    @Override
    public V get(K key) {
        int hash = hashFunction.hash(key);
        LinkedList<Entry<K, V>> list = table[hash];
        for (Entry<K, V> e : list) {
            if (Objects.equals(key, e.key)) {
                return e.getValue();
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int hash = hashFunction.hash(key);
        LinkedList<Entry<K, V>> list = table[hash];
        for (Entry<K, V> e : list) {
            if (Objects.equals(e.key, key)) {
                e.setValue(value);
                return;
            }
        }
        ++size;
        list.insertFront(new Entry<>(key, value));
    }

    @Override
    public boolean remove(K key) {
        int hash = hashFunction.hash(key);
        LinkedList<Entry<K, V>> list = table[hash];
        int s = list.size();
        list.delete(new Entry<>(key, null));
        if (s != list.size()) {
            size--;
        }
        return s != list.size();
    }

    @Override
    public int size() {
        return size;
    }

    public static class DivisionHashFunction<K> implements HashFunction<K> {
        private final int capacity = 14387;

        @Override
        public int hash(K key) {
            return key.hashCode() % capacity;
        }
    }

    public static class MultiplicationHashFunction<K> implements HashFunction<K> {
        private final int bits = 10;
        private final int A = 265443577;

        @Override
        public int hash(K key) {
            return (key.hashCode() * A) >>> (32 - bits);
        }

    }
}
