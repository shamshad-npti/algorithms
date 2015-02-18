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
 * Implementation of stack using two queue
 *
 * @author Shamshad Alam
 */
public class StackImpUsingQueue<T> {

    private final Queue<T> first = new Queue<>();
    private final Queue<T> second = new Queue<>();

    public void push(T e) {
        Queue<T> pushable = second.isEmpty() ? first : second;
        pushable.enqueue(e);
    }

    public T pop() {
        checkUnderflow();
        if (second.isEmpty()) {
            while (first.size() > 1) {
                second.enqueue(first.dequeue());
            }
            return first.dequeue();
        } else {
            while (second.size() > 1) {
                first.enqueue(second.dequeue());
            }
            return second.dequeue();
        }
    }

    public int size() {
        return first.size() + second.size();
    }

    public boolean isEmpty() {
        return first.isEmpty() && second.isEmpty();
    }

    private void checkUnderflow() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
    }
}
