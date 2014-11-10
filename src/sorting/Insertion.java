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
 * @param <T>
 */
public class Insertion<T> implements Sort<T> {

    @Override
    public void sort(T[] elements, Comparator<T> comparator) {
        int size = elements.length;
        if(size != 0)
            for (int i = 1; i < size; i++) {
                T e = elements[i];
                int j = i - 1;
                while(j >= 0 && compare(e, elements[j], comparator) < 0)
                    elements[j + 1] = elements[j--];
                elements[j + 1] = e;
            }
    }
}
