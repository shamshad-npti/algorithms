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
 * Implementation of SinglyLinkedList
 *
 * @author Shamshad Alam
 */
public class SinglyLinkedList<T> implements LinkedList<T> {
    private Node<T> head;
    private int size;

    @Override
    public void insertFront(T e) {
        Node<T> node = new Node(e, head);
        head = node;
        size++;
    }

    @Override
    public boolean contains(T e) {
        Node<T> temp = head;
        while (temp != null && !Objects.equals(e, temp.elem)) {
            temp = temp.next;
        }
        return temp != null;
    }

    @Override
    public void delete(T e) {
        Node<T> temp = head, prev = null;
        while (temp != null && !Objects.equals(e, temp.elem)) {
            prev = temp;
            temp = temp.next;
        }
        if (temp != null) {
            if (prev == null) {
                head.elem = null;
                head = head.next;
            } else {
                prev.next = temp.next;
                temp.elem = null;
            }
            size--;
        }
    }

    @Override
    public T front() {
        emptyCheck();
        return head.elem;
    }

    @Override
    public T removeBack() {
        throw new UnsupportedOperationException("Unsupported Operation (Use DoublyLinkedList instead)");
    }

    @Override
    public T removeFront() {
        emptyCheck();
        T e = this.head.elem;
        this.head.elem = null;
        this.head = this.head.next;
        this.size--;
        return e;
    }

    @Override
    public void insertBack(T e) {
        throw new UnsupportedOperationException("Unsupported Operation (Use DoublyLinkedList instead)");
    }

    @Override
    public T back() {
        throw new UnsupportedOperationException("Unsupported Operation (Use DoublyLinkedList instead)");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Node<T> x = head;
        while (x != null) {
            Node<T> next = x.next;
            x.next = null;
            x = next;
        }
        head = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> head = SinglyLinkedList.this.head;
            @Override
            public boolean hasNext() {
                return head != null;
            }

            @Override
            public T next() {
                T e = this.head.elem;
                this.head = this.head.next;
                return e;
            }
        };
    }

    /**
     * Reverse the SinglyLinkedList.
     *
     * @param <T>
     * @param list
     */
    public static <T> void reverse(SinglyLinkedList<T> list) {
        Node<T> temp = null;
        while (list.head != null) {
            Node<T> next = list.head;
            list.head = list.head.next;
            next.next = temp;
            temp = next;
        }
        list.head = temp;
    }

    private void emptyCheck() {
        if (isEmpty()) {
            throw new RuntimeException("List is empty");
        }
    }

    private static class Node<T> {

        private T elem;
        private Node next;

        public Node(T elem, Node next) {
            this.elem = elem;
            this.next = next;
        }

    }
}
