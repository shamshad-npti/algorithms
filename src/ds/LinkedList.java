/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

/**
 *
 * @author shamshad
 */
public class LinkedList<T> {
    final private Node<T> head;

    public LinkedList() {
        this.head = new Node<>(null, null);
    }
    
    public void insert(T t) {
        head.next = new Node<>(head.next, t);
    }
    
    public void insert(T t, int i) {
        Node<T> c = head;
        while(i-- != 0)
            c = c.next;
        Node<T> x = new Node<>(c.next, t);
        c.next = x;
    }
    
    public void delete() {
        checkEmpty();
        Node<T> n = this.head.next.next;
        head.next.next = null;
        this.head.next = n;
    }

    public T head() {
        checkEmpty();
        return this.head.next.element;
    }
    
    public boolean isEmpty() {
        return head.next == null;
    }
    
    private void checkEmpty() {
        if(isEmpty()) {
            throw new RuntimeException("List is empty");
        }
    }
    private static class Node<E> {
        private Node<E> next;
        E element;

        public Node(Node<E> next, E element) {
            this.next = next;
            this.element = element;
        }
        
    }
}
