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

import java.util.Iterator;
import java.util.Objects;

/**
 * Implementation of DoublyLinkedList
 *
 * @author Shamshad Alam
 * @param <T>
 */
public class DoublyLinkedList<T> implements LinkedList<T> {
    private final Node<T> sentinel;
    private int size = 0;

    public DoublyLinkedList() {
        sentinel = new Node<>(null, null, null);
        sentinel.next = sentinel.prev = sentinel;
    }

    @Override
    public void insertFront(T e) {
        Node<T> node = new Node<>(sentinel.next, sentinel, e);
        sentinel.next.prev = node;
        sentinel.next = node;
        size++;
    }

    @Override
    public void insertBack(T e) {
        Node<T> node = new Node<>(sentinel, sentinel.prev, e);
        sentinel.prev.next = node;
        sentinel.prev = node;
        size++;
    }

    @Override
    public T removeFront() {
        emptyCheck();
        Node<T> node = sentinel.next;
        sentinel.next = node.next;
        node.next.prev = sentinel;
        node.next = node.prev = null;
        --size;
        return node.elem;
    }

    @Override
    public T removeBack() {
        emptyCheck();
        Node<T> node = sentinel.prev;
        sentinel.prev = node.prev;
        node.prev.next = sentinel;
        node.next = node.prev = null;
        --size;
        return node.elem;
    }

    @Override
    public T front() {
        emptyCheck();
        return sentinel.next.elem;
    }

    @Override
    public T back() {
        emptyCheck();
        return sentinel.prev.elem;
    }

    @Override
    public boolean contains(T e) {
        Node<T> next = sentinel.next;
        while (next != sentinel && !Objects.equals(e, next.elem)) {
            next = next.next;
        }
        return next != sentinel;
    }

    @Override
    public void delete(T e) {
        Node<T> next = sentinel.next;
        while (next != sentinel && !Objects.equals(e, next.elem)) {
            next = next.next;
        }
        if (next != sentinel) {
            next.prev.next = next.next;
            next.next.prev = next.prev;
            next.next = next.prev = null;
            next.elem = null;
            size--;
        }
    }

    @Override
    public void clear() {
        Node<T> node = sentinel.next;
        while (node != sentinel) {
            Node<T> next = node.next;
            node.prev = null;
            node.next = null;
            node.elem = null;
            node = next;
        }
        sentinel.next = sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> next = sentinel.next;
            @Override
            public boolean hasNext() {
                return next != sentinel;
            }

            @Override
            public T next() {
                T e = next.elem;
                next = next.next;
                return e;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    private void emptyCheck() {
        if (isEmpty()) {
            throw new RuntimeException("The operation could not be performed on empty list");
        }
    }
    private static class Node<T> {

        private Node<T> next;
        private Node<T> prev;
        private T elem;

        public Node(Node<T> next, Node<T> prev, T elem) {
            this.next = next;
            this.prev = prev;
            this.elem = elem;
        }

    }
}
