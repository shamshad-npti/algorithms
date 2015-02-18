package searching;

import java.util.Comparator;

/**
 *
 * @author Shamshad Alam
 */
public interface Search<T> {
    default public int find(T[] elements, T elem) {
        return find(elements, elem, 0);
    }
    default public int find(T[] elements, T elem, int start) {
        return find(elements, elem, start, elements.length - 1);
    }

    default public int find(T[] elements, T elem, int start, int end) {
        return find(elements, elem, start, end, null);
    }

    public int find(T[] elements, T elem, int start, int end, Comparator<T> comparator);
    
    default public int compare(T o1, T o2, Comparator<T> c) {
       return c == null ? ((Comparable<T>) o1).compareTo(o2) : c.compare(o1, o2);
    }
}
