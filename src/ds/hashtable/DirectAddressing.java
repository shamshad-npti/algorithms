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
 * @param <V>
 */
public class DirectAddressing<V> extends AbstractHashTable<Integer, V> {

    private final Entry<Integer, V>[] entries;
    private int maxKey = 0;
    private int size = 0;

    public DirectAddressing(int maxKey) {
        entries = new Entry[maxKey];
        this.maxKey = maxKey;
    }

    @Override
    public V get(Integer key) {
        return isOut(key) || entries[key] == null ? null : entries[key].getValue();
    }

    @Override
    public void put(Integer key, V value) {
        checkHash(key);
        if (entries[key] == null) {
            entries[key] = new Entry<>(key, value);
            ++size;
        } else {
            entries[key].setValue(value);
        }
    }

    @Override
    public boolean remove(Integer key) {
        if (isOut(key)) {
            return false;
        } else {
            if (entries[key] == null) {
                return false;
            }
            entries[key] = null;
            --size;
            return true;
        }
    }

    @Override
    public boolean contains(Integer key) {
        return get(key) != null;
    }

    private int checkHash(Integer key) {
        if (isOut(key)) {
            throw new RuntimeException("Key out of bound!");
        }
        return key;
    }

    private boolean isOut(Integer key) {
        return key == null || key < 0 && key >= maxKey;
    }

    @Override
    public int size() {
        return size;
    }

}
