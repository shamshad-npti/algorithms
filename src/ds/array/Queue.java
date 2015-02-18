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
 * Array implementation of queue
 *
 * @author Shamshad Alam
 * @param <T>
 */
public class Queue<T> {
    private int capacity = 16;
    private int tail;
    private int head;
    private Object[] elem;

    public Queue() {
        this.elem = new Object[capacity];
    }

    /**
     * Add an element to tail (rear) of the queue
     *
     * @param e
     */
    public void enqueue(T e) {
        this.elem[this.tail] = e;
        this.tail = (this.tail + 1) == capacity ? 0 : tail + 1;
        if (this.head == this.tail) {
            capacity <<= 1;
            Object[] temp = new Object[capacity];
            int length = elem.length;
            System.arraycopy(elem, head, temp, 0, length - this.head);
            System.arraycopy(elem, 0, temp, length - head, head);
            elem = temp;
            this.head = 0;
            this.tail = length;
        }
    }

    /**
     * remove and return an element from the front (head) of the queue
     *
     * @return
     */
    public T dequeue() {
        checkEmpty();
        T e = (T) elem[this.head];
        this.head = this.head + 1 == capacity ? 0 : this.head + 1;
        return e;
    }

    /**
     * return the element at the head of the queue
     *
     * @return
     */
    public T peek() {
        checkEmpty();
        return (T) elem[this.head];
    }

    /**
     * Check whether queue is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return this.head == this.tail;
    }

    /**
     * Size of queue
     *
     * @return
     */
    public int size() {
        return (this.tail - this.head + this.capacity) & (capacity - 1);
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
    }
}
