package sorting;

import java.util.Comparator;

/**
 *
 * @author Shamshad Alam
 * @param <T>
 */
public interface Sort<T> {
    default public void sort(T[] elements) {
        sort(elements, null);
    }
    
    public void sort(T[] elements, Comparator<T> comparator);
    
    default public int compare(T o1, T o2, Comparator<T> c) {
       return c == null ? ((Comparable<T>) o1).compareTo(o2) : c.compare(o1, o2);
    }
    
    default public void swap(T e[], int i, int j) {
        T t = e[i];
        e[i] = e[j];
        e[j] = t;
    }
}
