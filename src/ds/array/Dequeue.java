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
package ds.array;

/**
 * Array implementation of Double ended queue (Dequeue)
 *
 * @author Shamshad Alam
 * @param <T>
 */
public class Dequeue<T> {
    private int capacity = 16;
    private int back;
    private int front;
    private Object[] elem;

    public Dequeue() {
        this.elem = new Object[capacity];
    }

    /**
     * Add element at the back of the queue
     *
     * @param e
     */
    public void pushBack(T e) {
        this.back = nextBackElementIndex();
        this.elem[back] = e;
        if (this.front == this.back) {
            reallocate(back);
        }
    }

    /**
     * Add element at the front of the queue
     *
     * @param e
     */
    public void pushFront(T e) {
        this.elem[front] = e;
        this.front = this.front + 1 == capacity ? 0 : this.front + 1;
        if (this.front == this.back) {
            reallocate(back);
        }
    }

    /**
     * increase the size the queue
     *
     * @param pos
     */
    private void reallocate(int pos) {
        int length = elem.length;
        capacity <<= 1;
        Object[] temp = new Object[capacity];
        System.arraycopy(elem, pos, temp, 0, length - pos);
        System.arraycopy(elem, 0, temp, length - pos, pos);
        elem = temp;
        back = 0;
        front = length;
    }

    private int nextBackElementIndex() {
        return back == 0 ? capacity - 1 : back - 1;
    }

    private int frontElemIndex() {
        return front == 0 ? capacity - 1 : front - 1;
    }

    /**
     * Examine front of the queue
     *
     * @return
     */
    public T front() {
        checkUnderflow();
        return (T) elem[frontElemIndex()];
    }

    /**
     * Examine element at the back of the queue
     *
     * @return
     */
    public T back() {
        return (T) elem[back];
    }

    /**
     * Remove element from the back of the queue
     *
     * @return
     */
    public T popBack() {
        checkUnderflow();
        T t = (T) elem[back];
        back = (back + 1 == capacity) ? 0 : back + 1;
        return t;
    }

    /**
     * Remove element from the front of the queue
     *
     * @return
     */
    public T popFront() {
        checkUnderflow();
        T t = (T) elem[frontElemIndex()];
        front = front == 0 ? capacity - 1 : front - 1;
        return t;
    }

    /**
     * Check whether queue is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return this.front == this.back;
    }

    /**
     * count of element is queue
     *
     * @return
     */
    public int size() {
        return (this.front - this.back + this.capacity) & (capacity - 1);
    }

    private void checkUnderflow() {
        if (isEmpty()) {
            throw new RuntimeException("Dequeue is empty");
        }
    }
}
