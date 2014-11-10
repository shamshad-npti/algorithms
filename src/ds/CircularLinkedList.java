/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author shamshad
 * @param <E>
 */
public class CircularLinkedList<E> implements Iterable<E> {
    private final Node<E> parent;

    public CircularLinkedList() {
        this.parent = new Node<>(null, null, null);
        this.parent.next = this.parent.prev = this.parent;
    }
    
    public void insert(E elem) {
        Node<E> n = new Node<>(this.parent.next, this.parent, elem);
        this.parent.next.prev = n;
        this.parent.next = n;
    }
    
    public void delete() {
        if(!isEmpty()) {
            Node<E> n = this.parent.next;
            this.parent.next = n.next;
            n.next.prev = n.prev;
            n.next = n.prev = null;
            n.elem = null;
        }
    }
    
    public void delete(E elem) {
        Node<E> n = node(elem);
        if(n != this.parent) {
            n.next.prev = n.prev;
            n.prev.next = n.next;
            n.next = n.prev = null;
            n.elem = null;
        }
    }
    
    public boolean contains(E elem) {
        return node(elem) != this.parent;
    }
    
    private Node<E> node(E elem) {
        Node<E> n = this.parent.next;
        while(n != this.parent && !Objects.equals(n.elem, elem))
            n = n.next;
        return n;
    }
    
    public boolean isEmpty() {
        return this.parent == this.parent.next;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> e = parent.next;
            @Override
            public boolean hasNext() {
                return e != parent;
            }

            @Override
            public E next() {
                E elem = e.elem;
                e = e.next;
                return elem;
            }
        };
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
