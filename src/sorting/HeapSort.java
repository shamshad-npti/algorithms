package sorting;

import java.util.Comparator;

/**
 *
 * @author Shamshad Alam
 */
public class HeapSort<T> implements Sort<T> {
    @Override
    public void sort(T[] e, Comparator<T> com) {
        buildHeap(e, com);
        for (int i = e.length - 1; i > 0; i--) {
            swap(e, 0, i);
            heapify(e, 0, i, com);
        }
    }

    private void buildHeap(T[] e, Comparator<T> comp) {
        for (int i = e.length / 2; i >= 0; i--) {
            heapify(e, i, e.length, comp);
        }
    }

    private void heapify(T[] e, int i, int size, Comparator<T> comp) {
        int l;
        int r;
        int max = i;
        do {
            i = max;
            l = left(i);
            r = right(i);
            if (l < size && compare(e[max], e[l], comp) < 0) {
                max = l;
            }
            if (r < size && compare(e[max], e[r], comp) < 0) {
                max = r;
            }
            if (i != max) {
                swap(e, max, i);
            }
        } while (max != i);
    }

    private int left(int i) {
        return (i << 1) + 1;
    }

    private int right(int i) {
        return (i << 1) + 2;
    }
}
