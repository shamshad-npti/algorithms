package searching;

import java.util.Comparator;

/**
 * Algorithm: Binary Search
 * Input: Sorted Array <a1, a2, a3, ... an>, elem to find
 * Output: Index i of the element if it exists in array -1 otherwise
 * Time Complexity: O(lg(n))
 * @author Shamshad Alam
 */
public class BinarySearch<T> implements Search<T> {

    @Override
    public int find(T[] elements, T elem, int start, int end, Comparator<T> comparator) {
        int i = start, j = end, mid, f;
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
