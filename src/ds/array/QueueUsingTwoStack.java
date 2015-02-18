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
 * Implementation of queue using two stacks
 *
 * @author Shamshad Alam
 * @param <T>
 */
public class QueueUsingTwoStack<T> {

    private final Stack<T> stack = new Stack<>();
    private final Stack<T> aux = new Stack<>();

    public void enqueue(T e) {
        stack.push(e);
    }

    public T dequeue() {
        checkUnderflowAndFix();
        return aux.pop();
    }

    public T peek() {
        checkUnderflowAndFix();
        return aux.top();
    }

    public int size() {
        return stack.size() + aux.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty() && aux.isEmpty();
    }

    private void checkUnderflowAndFix() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        if (aux.isEmpty()) {
            while (!stack.isEmpty()) {
                aux.push(stack.pop());
            }
        }
    }
}
