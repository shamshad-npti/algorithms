/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author shamshad
 */
public class Radix implements Sort<Integer> {
    private int bound;

    public Radix(int bound) {
        this.bound = bound;
    }
    
    @Override
    public void sort(Integer[] elements, Comparator<Integer> comparator) {
        int bl = 8, bs = 1 << bl, t = 0;
        Queue<Integer>[] buckets = new Queue[bs];
        for (int i = 0; i < bs; i++)
            buckets[i] = new LinkedList<>();
        int base = 0, length = elements.length;
        while(bound > (1 << base - 1)) {
            for (int i = 0; i < length; i++)
                buckets[(elements[i] >> base) & (bs - 1)].offer(elements[i]);
            int k = 0;
            for (int i = 0; i < bs; i++) 
                while(!buckets[i].isEmpty())
                    elements[k++] = buckets[i].remove();
            base = (++t) * bl;
        }
    }
    
}
