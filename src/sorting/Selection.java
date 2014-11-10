package sorting;

import java.util.Comparator;

/**
 * Selection Sorting
 * BEST CASE     O(n)
 * WORST CASE    O(n^2)
 * AVERAGE CASE  O(n^2)
 * COMPARISON    n * (n - 1) / 2
 * SWAP          n
 * @author shamshad
 */
public class Selection<T> implements Sort<T> {

    @Override
    public void sort(T[] elements, Comparator<T> comparator) {
        int size = elements.length;
        if(size != 0) {
            boolean sorted = false;
            for(int i = 0; i < size - 1 && !sorted; ++i) {
                int k = i;
                T t = elements[k];
                sorted = true;
                for(int j = i + 1; j < size; ++j)
                    if(compare(t, elements[j], comparator) > 0) {
                        k = j;
                        t = elements[k];
                        sorted = false;
                    }
                swap(elements, i, k);
            }
        }
    }
    
}
