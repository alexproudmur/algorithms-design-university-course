package com.company;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import static com.company.SearchHelper.*;

public class StarA {
    private static final Set<Node> closed = new HashSet<>();
    private static int iterations = 0;

    public static void main(String[] args) {
        System.out.println("A* Solution:");
        int[][] initialField = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
        };
        printField(aStar(initialField));
    }

    public static int[][] aStar(int[][] field) {
        Queue<SearchHelper.Node> queue = new PriorityQueue<>();
        queue.offer(new Node(field, 0));
        return aStarRecursive(queue);
    }

    public static int[][] aStarRecursive(Queue<Node> queue) {
        Node node = queue.poll();
        System.out.println("--------");
        System.out.println(closed.size());
        System.out.println(iterations);
        System.out.println(queue.size());
        if (closed.contains(node)) {
            return aStarRecursive(queue);
        }
        closed.add(node);
        if (!checkConflict(node.field)) {
            return node.field;
        }
        expand(queue, node, true);
        iterations++;
        return aStarRecursive(queue);
    }
}
