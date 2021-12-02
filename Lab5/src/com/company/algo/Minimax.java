package com.company.algo;

import com.company.Main;
import com.company.game.Game;

import java.util.Arrays;
import java.util.Stack;
import java.util.TreeSet;

public class Minimax {

    public Stack<GameTree.GameTreeNode> stack = new Stack<>();

    private int minimax(GameTree.GameTreeNode root, boolean maximizing, int depth, int alpha, int beta) {

        if (root.children == null) {
            return root.estimate;
        }

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (GameTree.GameTreeNode child : root.children) {
                int eval = minimax(child, false, depth - 1, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(eval, alpha);
                if (beta <= alpha) {
                    break;
                }
            }
            if (root.children != null) {
                TreeSet<GameTree.GameTreeNode> nodes = new TreeSet<>(Arrays.asList(root.children));
                stack.push(nodes.last());
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (GameTree.GameTreeNode child : root.children) {
                int eval = minimax(child, true, depth - 1, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(eval, beta);
                if (beta <= alpha) {
                    break;
                }
            }
            if (root.children != null) {
                TreeSet<GameTree.GameTreeNode> nodes = new TreeSet<>(Arrays.asList(root.children));
                stack.push(nodes.first());
            }
            return minEval;
        }
    }

        public int minimax(GameTree.GameTreeNode root) {
            return minimax(root, false, Game.depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }










//        if (root.children[0].children == null) {
//
//            TreeSet<GameTree.GameTreeNode> nodes = new TreeSet<>(Arrays.asList(root.children));
//            if (maximizing) {
//                root.movedHole = nodes.last().movedHole;
//                root.setEstimate(nodes.last().estimate);
//            } else {
//                root.movedHole = nodes.first().movedHole;
//                root.setEstimate(nodes.first().estimate);
//            }
//            return root.movedHole;
//        }
//
//        return minimax(root)

    }
