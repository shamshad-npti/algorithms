package ds;

import java.util.Objects;

/**
 *
 * @author shamshad
 * @param <T>
 */
public class DoubleLinkedList<T> {
    final private Node<T> head;
    final private Node<T> tail;
    public DoubleLinkedList() {
        this.head = new Node<>(null, null, null);
        this.tail = new Node<>(null, null, null);
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }
    
    public void insert(T elem) {
        Node<T> n = new Node(elem, head.next, head);
        head.next.prev = n;
        head.next = n;
    }
    
    public void insertToTail(T elem) {
        Node<T> n = new Node<>(elem, tail, tail.prev);
        tail.prev.next = n;
        tail.prev = n;
    }
    
    public void deleteFromTail() {
        Node<T> n = tail.prev;
        tail.prev = n.prev;
        n.prev.next = n.next;
        n.next = n.prev = null;
        n.elem = null;
    }
    
    public boolean delete(T e) {
        Node<T> n = this.head.next;
        while(n != tail && !Objects.equals(n.elem, e))
            n = n.next;
        if(n != tail) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            n.next = n.prev = null;
            n.elem = null;
            return true;
        }
        return false;
    }
    
    public void delete() {
        Node<T> n = this.head.next;
        this.head.next = n.next;
        n.next.prev = n.prev;
        n.next = n.prev = null;
        n.elem = null;
    }
    
    public T head() {
        return this.head.next.elem;
    }
    
    public T tail() {
        return this.tail.prev.elem;
    }
    
    public boolean isEmpty() {
        return this.head.next == this.tail;
    }
    
    private static class Node<E> {
        private E elem;
        private Node<E> next;
        private Node<E> prev;

        public Node(E elem, Node<E> next, Node<E> prev) {
            this.elem = elem;
            this.next = next;
            this.prev = prev;
        }
        
    }
}
