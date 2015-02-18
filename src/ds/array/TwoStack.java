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
 * Two stack in single array
 *
 * @author Shamshad Alam
 */
public class TwoStack<T> {

    private int capacity = 16;
    private int top1 = -1;
    private int top2 = capacity;
    private Object[] elem;

    public TwoStack() {
        this.elem = new Object[capacity];
    }

    public void pushToFirst(T e) {
        ensureCapacity();
        this.elem[++top1] = e;
    }

    public void pushToSecond(T e) {
        ensureCapacity();
        this.elem[--top2] = e;
    }

    private void ensureCapacity() {
        if (Math.abs(top1 - top2) == 1) {
            int size = sizeOfSecond();
            capacity <<= 1;
            Object[] temp = new Object[capacity];
            System.arraycopy(elem, 0, temp, 0, sizeOfFirst());
            System.arraycopy(elem, top2, temp, capacity - size, size);
            elem = temp;
            top2 = capacity - size;
        }
    }

    public T popFromFirst() {
        checkUnderflowFirst();
        return (T) this.elem[top1--];
    }

    public T popFromSecond() {
        checkUnderflowSecond();
        return (T) this.elem[top2++];
    }

    public T topFromFirst() {
        checkUnderflowFirst();
        return (T) this.elem[top1];
    }

    public T topFromSecond() {
        checkUnderflowSecond();
        return (T) this.elem[top2];
    }

    public boolean isEmptyFirst() {
        return top1 == -1;
    }

    public boolean isEmptySecond() {
        return top2 == capacity;
    }

    public int sizeOfFirst() {
        return top1 + 1;
    }

    public int sizeOfSecond() {
        return capacity - top2;
    }

    private void checkUnderflowFirst() {
        if (isEmptyFirst()) {
            throw new RuntimeException("First stack is empty");
        }
    }

    private void checkUnderflowSecond() {
        if (isEmptySecond()) {
            throw new RuntimeException("Second stack is empty");
        }
    }
}
