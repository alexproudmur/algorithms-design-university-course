package com.company;

import static com.company.SearchHelper.*;
import java.util.ArrayDeque;
import java.util.Queue;

@SuppressWarnings("all")
public class Bfs {
    //private static int iterations = 0;
    //private static int deleted = 0;

    public static void main(String[] args) {
        System.out.println("BFS Solution:");
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
        printField(bfs(initialField));
    }

    public static int[][] bfs(int[][] field) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.offer(new Node(field));
        return bfsRecursive(queue);
    }

    public static int[][] bfsRecursive(Queue<Node> queue) {
        for (Node node : queue) {
            //deleted++;
            //System.out.println("--------");
            //System.out.println(deleted);
            //System.out.println(iterations);
            //System.out.println(queue.size());
            if (!checkConflict(node.field)) {
//                System.out.println("--------");
//                System.out.println(deleted);
//                System.out.println(iterations);
//                System.out.println(queue.size());
                return node.field;
            }
        }
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            Node node = queue.poll();
            expand(queue, node);
        }
//        iterations++;
//        System.out.println("--------");
//        System.out.println(deleted);
//        System.out.println(iterations);
//        System.out.println(queue.size());
        return bfsRecursive(queue);
    }




}
