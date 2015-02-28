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

/**
 *
 * @author Shamshad Alam
 */
public abstract class AbstractHashTable<K, V> {

    public boolean contains(K key) {
        return get(key) != null;
    }

    public abstract void put(K key, V value);

    public abstract boolean remove(K key);

    public abstract V get(K key);

    public abstract int size();

    public boolean isEmpty() {
        return size() == 0;
    }

    public static class Entry<K, V> {

        protected K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
