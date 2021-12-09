package com.example.lab6.algo;

import com.example.lab6.game.Strategy;

import java.util.*;

public class Minimax {

    public TreeSet<GameTree.GameTreeNode> deque = new TreeSet<>();

    public double minimax(GameTree.GameTreeNode root, boolean ai, int depth) {
        if (depth == 1) {
            return root.estimate;
        }
        if (root.chanceNode) {
            double sum = 0;
            for (GameTree.GameTreeNode child : root.children) {
                sum += minimax(child, ai, depth - 1);
            }
            root.estimate = sum / 11;
            deque.add(root);
            return root.estimate;
        }
        if (ai) {
            double maxEval = Integer.MIN_VALUE;
            for (GameTree.GameTreeNode child : root.children) {
                double eval = minimax(child, false, depth - 1);
                maxEval = Math.max(maxEval, eval);
            }
//            if (root.children != null) {
//                TreeSet<GameTree.GameTreeNode> nodes = new TreeSet<>(Arrays.asList(root.children));
//                deque.push(nodes.last());
//            }
            return maxEval;
        } else {
            double minEval = Integer.MAX_VALUE;
            for (GameTree.GameTreeNode child : root.children) {
                double eval = minimax(child, true, depth - 1);
                minEval = Math.min(minEval, eval);
            }
//            if (root.children != null) {
//                TreeSet<GameTree.GameTreeNode> nodes = new TreeSet<>(Arrays.asList(root.children));
//                deque.push(nodes.first());
//            }
            return minEval;
        }
    }

    public Strategy getStrategy() {
        TreeSet<GameTree.GameTreeNode> set = new TreeSet<>();
        for (GameTree.GameTreeNode child : deque.last().children) {
            set.add(child);
        }
        return set.first().strategy;
    }
}
