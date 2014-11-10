/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds;

import java.util.Arrays;

/**
 *
 * @author shamshad
 * @param <T>
 */
public class Stack<T> {
    private int top = -1;
    private Object[] elem;
    private int capacity = 8;
    
    public Stack() {
        elem = new Object[capacity];
    }
    
    
    public void pop() {
        checkUnderflow();
        top--;
    }
    
    public T top() {
        checkUnderflow();
        return (T)elem[top];
    }
    
    public void push(T t) {
        ensureCapacity(++top);
        elem[top] = t;
    }
    
    public int size() {
        return top + 1;
    }
    
    public boolean isEmpty () {
        return size() == 0;
    }
    
    private void ensureCapacity(int c) {
        if(c >= capacity) {
            capacity = capacity << 1;
            Object[] copy = new Object[capacity];
            System.arraycopy(elem, 0, copy, 0, c);
            Arrays.fill(elem, null);
            elem = copy;
        }
    }
    private void checkUnderflow() {
        if(isEmpty())
            throw new StackUnderflowException("Stack Underflow");
    }
    
    private static class StackUnderflowException extends RuntimeException {

        public StackUnderflowException(String message) {
            super(message);
        }
        
    }
}
