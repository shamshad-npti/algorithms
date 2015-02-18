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
 * Array implementation of stack
 *
 * @author shame
 */
public class Stack<T> {
    private int capacity = 10;
    private int top;
    private Object[] elem;

    public Stack() {
        this.top = -1;
        this.elem = new Object[capacity];
    }

    /**
     * Push(add at the top) an element to stack
     *
     * @param e
     */
    public void push(T e) {
        if (top == capacity - 1) {
            capacity <<= 1;
            Object[] temp = new Object[capacity];
            System.arraycopy(elem, 0, temp, 0, size());
            this.elem = temp;
        }
        this.elem[++top] = e;
    }

    /**
     * Remove top element from stack and return it
     *
     * @return
     */
    public T pop() {
        checkUnderflow();
        return (T) elem[top--];
    }

    /**
     * Peek top element from stack and return it
     *
     * @return
     */
    public T top() {
        checkUnderflow();
        return (T) this.elem[top];
    }

    /**
     * Check whether stack is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * count of element in stack
     *
     * @return
     */
    public int size() {
        return top + 1;
    }
    private void checkUnderflow() {
        if (isEmpty()) {
            throw new RuntimeException("Stack Underflow");
        }
    }
}
