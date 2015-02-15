/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorting;

import java.util.Random;

/**
 *
 * @author shamshad
 */
public class Test {
    public static final int SIZE = 10000000;
    public static void start() throws Exception {
        Random random = new Random();
        Integer[] t = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            t[i] = random.nextInt(SIZE);
        }
        long start = System.currentTimeMillis();
        System.out.println("Sorting started at : " + start);
        Sort<Integer> sorter = new Radix(SIZE);
        sorter.sort(t);
        long end = System.currentTimeMillis();
        verifySorted(t);
        System.out.println("Sorting ended at : " + end);
        System.out.println("Total time taken : " + (end - start) * 0.001 + "s");

    }

    private static void verifySorted(Integer[] elem) {
        for (int i = 1; i < elem.length; i++) {
            if (elem[i - 1] > elem[i]) {
                System.out.println("Test Failed");
                return;
            }
        }
        System.out.println("Test succeeded");
    }
}
