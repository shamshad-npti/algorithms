/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author shamshad
 */
public class Counting implements Sort<Integer> {

    /**
     * This will work only for none negative integer
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
    
}
