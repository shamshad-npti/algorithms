/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import searching.BinarySearch;
import searching.Search;

/**
 *
 * @author shamshad
 */
public class Test {
    public static final int SIZE = 1000000;
    public static void start() throws Exception {
        Random random = new Random();
        Integer[] t = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            t[i] = random.nextInt(SIZE);
        }
        //System.setOut(new PrintStream("out.txt"));
        //System.out.println(Arrays.toString(t));
        long start = new Date().getTime();
        System.out.println("Sorting started at : " + start);
        Sort<Integer> sorter = new Merge<>();
        sorter.sort(t);
        long end = new Date().getTime();
        System.out.println("Sorting ended at : " + end);
        System.out.println("Total time taken : " + (end - start) * 0.001 + "ms");
        //System.out.println(Arrays.toString(t));
    }
}
