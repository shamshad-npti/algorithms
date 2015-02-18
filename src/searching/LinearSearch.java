/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searching;

import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author shamshad
 */
public class LinearSearch<T> implements Search<T> {

    @Override
    public int find(T[] elements, T elem, int start, int end, Comparator<T> predicate) {
        if(predicate != null) {
            for (int i = start; i <= end; i++)
                if(predicate.compare(elem, elements[i]) == 0)
                    return i;
        } else {
            for (int i = start; i < elements.length; i++)
                if(Objects.equals(elem, elements[i]))
                    return i;            
        }
        return -1;
    }    
}
