/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Shamshad Alam
 */
public class Radix implements Sort<Integer> {
    private int bound;

    public Radix(int bound) {
        this.bound = bound;
    }
    
    @Override
    public void sort(Integer[] elements, Comparator<Integer> comparator) {
        int bl = 12, bs = 1 << bl, t = 0;
        List<Integer>[] buckets = new List[bs];
        for (int i = 0; i < bs; i++)
            buckets[i] = new ArrayList<>();
        int base = 0, length = elements.length;
        while(bound > (1 << base - 1)) {
            for (int i = 0; i < length; i++)
                buckets[(elements[i] >> base) & (bs - 1)].add(elements[i]);
            int k = 0;
            for (int i = 0; i < bs; i++) {
                for (Integer bucket : buckets[i]) 
                    elements[k++] = bucket;
                //buckets[i].clear();
                buckets[i] = new ArrayList<>();
            }
            base = (++t) * bl;
        }
    }
    
}
