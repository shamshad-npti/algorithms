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
public class Counting implements Sort<Integer> {

    /**
     * (1). This will work only for none negative integer (2). it is unstable
     *
     * @param elements
     * @param comparator 
     */
    @Override
    public void sort(Integer[] elements, Comparator<Integer> comparator) {
        int[] aux = new int[elements.length];
        for (int b : elements)
            aux[b]++;
        int k = 0;
        for (int i = 0; i < elements.length; i++)
            while(aux[i]-- != 0)
                elements[k++] = i;
    }

    public void stableCountingSort(int[] elem, int max) {
        int[] counter = new int[max + 1];
        int[] aux = new int[elem.length];
        for (int i = 0; i < elem.length; i++) {
            counter[elem[i]]++;
        }
        for (int i = 1; i < counter.length; i++) {
            counter[i] += counter[i - 1];
        }

        for (int i = elem.length - 1; i >= 0; i--) {
            aux[counter[elem[i]]] = elem[i];
            counter[elem[i]]--;
        }
        System.arraycopy(aux, 0, elem, 0, elem.length);
    }
}
