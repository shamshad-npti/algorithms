package ds;

import java.util.NoSuchElementException;

/**
 *
 * @author Shamshad Alam
 * @param <T>
 */
public class Queue<T> {
    private final Node<T> front;
    private final Node<T> rear;

    public Queue() {
        this.front = new Node<>(null, null, null);
        this.rear = new Node<>(null, null, null);
        this.front.next = this.rear;
        this.rear.prev = this.front;
    }
    
    public void enqueue(T elem) {
        Node<T> n = new Node<>(this.rear, this.rear.prev, elem);
        this.rear.prev.next = n;
        this.rear.prev = n;
    }
    
    public T front() {
        if(!isEmpty())
            return this.front.next.elem;
        else 
            throw new NoSuchElementException("Queue is empty!");
    }
    
    public void dequeue() {
        if(isEmpty())
            throw new NoSuchElementException("Queue is empty!");
        Node<T> n = this.front.next;
        this.front.next = n.next;
        n.next.prev = n.prev;
        n.next = n.prev = null;
        n.elem = null;
    }
    
    public boolean isEmpty() {
        return this.front.next == this.rear;
    }
    
    private static class Node<E> {
         Node<E> next;
         Node<E> prev;
         E elem;

        public Node(Node<E> next, Node<E> prev, E elem) {
            this.next = next;
            this.prev = prev;
            this.elem = elem;
        }
         
    }
}
