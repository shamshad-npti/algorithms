/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Comparator;

/**
 *
 * @author shamshad
 */
public class Bubble<T> implements Sort<T> {

    @Override
    public void sort(T[] elements, Comparator<T> comparator) {
        int size = elements.length;
        if(size != 0) {
            boolean sorted = false;
            for (int i = size; i > 0 && !sorted; --i) {
                sorted = true;
                for (int j = 1; j < i; j++)
                    if(compare(elements[j - 1], elements[j], comparator) > 0) {
                        swap(elements, j - 1, j);
                        sorted = false;
                    }
            }
        }
    }
    
}
