package searching;

import java.util.Comparator;

/**
 *
 * @author shamshad
 */
public class BinarySearch<T> implements Search<T> {

    @Override
    public int find(T[] elements, T elem, int start, Comparator<T> comparator) {
        int i = start, j = elements.length - 1, mid, f;
        while(i < j) {
            mid = (i + j) / 2;
            f = compare(elem, elements[mid], comparator);
            if(f > 0)
                i = mid + 1;
            else if (f < 0) 
                j = mid;
            else
                return mid;
        }
        return -1;
    }
    
}
