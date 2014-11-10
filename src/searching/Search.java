package searching;

import java.util.Comparator;

/**
 *
 * @author shamshad
 */
public interface Search<T> {
    default public int find(T[] elements, T elem) {
        return find(elements, elem, 0, null);
    }
    default public int find(T[] elements, T elem, int start) {
        return find(elements, elem, start, null);
    }
    public int find(T[] elements, T elem, int start, Comparator<T> comparator);
    
    default public int compare(T o1, T o2, Comparator<T> c) {
       return c == null ? ((Comparable<T>) o1).compareTo(o2) : c.compare(o1, o2);
    }
}
