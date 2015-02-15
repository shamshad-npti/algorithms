/*
 * Solution to strongly connected components
 * Coursera (Algorithm, Design & Analysis - 2015)
 * Run with options -Xmx1024 and -Xss128
 * [Try to write program yourself. You will certainly
 *  learn memory management] : 
 * e.g. This program will consume almost 
 * three times more memory if you use Set<Integer> 
 * to store neighbor vertices or you use Map<E, V>
 * to store graph instead of array. Moreover
 * program will be very much slow
 */
package practice;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Shamshad Alam
 */
public class SCC {
    private int time = 0;
    private final int size = 875715;
    private Node[] nodes = new Node[size];
    private int scc;

    public void solve() throws FileNotFoundException {
        BufferedInputStream bus = new BufferedInputStream(new FileInputStream("SCC.txt"), 1 << 16);
        
        Scanner in = new Scanner(bus);
        for (int i = 1; i < size; i++)
            nodes[i] = new Node(i);

        System.out.println("Reading input...");
        while(in.hasNextInt())
            nodes[in.nextInt()].next.add(in.nextInt());

        System.out.println("First dfs Search...");
        for (int i = 1; i < size; i++)
            if(!nodes[i].visited)
                dfs(nodes[i]);

        System.out.println("Transposing & Rearranging Graph...");
        for (int i = 1; i < size; i++) {
            nodes[i].id = nodes[i].finish;
            List<Integer> list = new ArrayList<>();
            for (Integer n : nodes[i].next)
                list.add(nodes[n].finish);
            nodes[i].next = list;
        }
        
        Node[] aux = new Node[size];
        for (int i = 1; i < size; i++)
            aux[i] = new Node(i);

        for (int i = 1; i < size; i++)
            for (Integer n : nodes[i].next)
                aux[n].next.add(nodes[i].id);
        
        nodes = aux;
        SortedSet<Integer> inth = new TreeSet<>();
        inth.add(0);
        System.out.println("Second dfs Search...");
        for (int i = size - 1; i >= 1; i--, scc = 0) {
            if(!nodes[i].visited) {
                dfs2(nodes[i]);
                if (inth.first() < scc) {
                    if (inth.size() > 5)
                        inth.remove(inth.first());
                    inth.add(scc);
                }
            }
        }
        System.out.println("Here is the top five winner...!");
        System.out.println(inth);
    }

    private void dfs(Node n) {
        n.visited = true;
        n.next.stream().filter((c) -> (!nodes[c].visited)).forEach((c) -> {
            dfs(nodes[c]);
        });
        n.finish = ++time;
    }

    private void dfs2(Node n) {
        n.visited = true;
        n.next.stream().filter((c) -> (!nodes[c].visited)).forEach((c) -> {
            dfs2(nodes[c]);
        });
        scc++;
    }

    private static class Node {
        int id;
        int finish;
        List<Integer> next = new ArrayList<>();
        boolean visited;

        public Node(int id) {
            this.id = id;
        }
    }
}
