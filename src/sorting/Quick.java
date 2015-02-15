package sorting;

import java.util.Comparator;
import java.util.Random;

/**
 * Randomized quick sort implementation which recursion stack depth is bounded
 * by O(log(n)) and its expected running time is O(n*log(n)).
 * Its worst case
 * performance is, however, O(n^2) with probability 1/n!
 *
 * @author shamshad
 * @param <T> 
 */
public class Quick<T> implements Sort<T>{
    private static final Random RAN_GEN = new Random();

    @Override
    public void sort(T[] elems, Comparator<T> comp) {
        new QuickSorter(elems, comp).quicksort(0, elems.length - 1);
    }
    
    private class QuickSorter {
        private final T[] elems;
        private final Comparator<T> comp;

        public QuickSorter(T[] elements, Comparator<T> comparator) {
            this.elems = elements;
            this.comp = comparator;
        }
        
        public void quicksort(int i, int j) {
            if (i < j) {
                int p;
                while (i < j) {
                    p = partition(i, j);
                    if (p < (i + j) >> 1) {
                        quicksort(i, p - 1);
                        i = p + 1;
                    } else {
                        quicksort(p + 1, j);
                        j = p - 1;
                    }
                }
            }
        }

        private int partition(int from, int to) {
            int p = from + RAN_GEN.nextInt(to - from + 1);
            swap(elems, p, from);
            int i = from + 1;
            int j = from + 1;
            while (j <= to) {
                if (compare(elems[j], elems[from], comp) < 0) {
                    swap(elems, i++, j);
                }
                j++;
            }
            swap(elems, from, --i);
            return i;
        }
    }
}
