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
package ds.linkedlistimp;

/**
 * Linked List implementation of stack
 *
 * @author Shamshad Alam
 * @param <T>
 */
public class Stack<T> extends SinglyLinkedList<T> {

    public void push(T e) {
        super.insertFront(e);
    }

    public T pop() {
        return super.removeFront();
    }

    public T top() {
        return super.front();
    }
}
