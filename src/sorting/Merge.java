package sorting;

import java.util.Comparator;

/**
 * Performance
 * Best   O( n lg(n))
 * Worst  O( n lg(n))
 * 
 * @author shamshad
 */
public class Merge<T> implements Sort<T> {
    
    @Override
    public void sort(T[] elements, Comparator<T> comparator) {
        new MergeSorter(elements, comparator).mergesort(0, elements.length - 1);
    }

    private class MergeSorter {
        private final T[] elem;
        private final Comparator<T> comparator;

        public MergeSorter(T[] elem, Comparator<T> comparator) {
            this.elem = elem;
            this.comparator = comparator;
        }
        
        void mergesort(int i, int j) {
            if(i != j) {
                int mid = (i + j) / 2;
                mergesort(i, mid);
                mergesort(mid + 1, j);
                int l = mid - i + 1, r = j - mid, li = 0, ri = 0;
                Object[] left = new Object[l];
                Object[] right = new Object[r];
                System.arraycopy(elem, i, left, 0, l);
                System.arraycopy(elem, mid + 1, right, 0, r);
                int k = i;
                while(k <= j) {
                    if(compare((T)left[li], (T)right[ri], comparator) > 0)
                        elem[k++] = (T)right[ri++];
                    else
                        elem[k++] = (T)left[li++];
                    if(ri >= r || li >= l) break;
                }
                if(li < l)
                    for (int m = k; m <= j; m++)
                        elem[m] = (T)left[li++];
                else if(ri < r)
                    for (int m = k; m <= j; m++)
                        elem[m] = (T)right[ri++];
            }
        }
    }
}
