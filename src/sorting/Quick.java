/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author shamshad
 * @param <T> 
 */
public class Quick<T> implements Sort<T>{
    private static final Random RAN_GEN = new Random();

    @Override
    public void sort(T[] elems, Comparator<T> comp) {
        new MergeSorter(elems, comp).quicksort(0, elems.length - 1);
    }
    
    private class MergeSorter {
        private final T[] elems;
        private final Comparator<T> comp;

        public MergeSorter(T[] elements, Comparator<T> comparator) {
            this.elems = elements;
            this.comp = comparator;
        }
        
        public void quicksort(int i, int j) {
            if(i >= j) return; // ONE ELEMENT
            int p = j; //i + RAN_GEN.nextInt(j - i + 1);
            T pivot = elems[p];
            int k = i, m = j - 1;
            do {
                while( k <= m && compare(pivot, elems[k], comp) > 0)
                    k++;
                while(m > k && compare(pivot, elems[m], comp) <= 0)
                    m--;
                swap(elems, k, m);
            } while(m > k);
            swap(elems, m, p);
            quicksort(i, m);
            quicksort(m + 1, j);
        }
    }
}
