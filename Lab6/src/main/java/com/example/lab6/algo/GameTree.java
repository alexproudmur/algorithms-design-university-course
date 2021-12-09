package com.example.lab6.algo;

import com.example.lab6.game.GameState;
import com.example.lab6.game.Strategy;

public class GameTree {
    public GameTreeNode root;
    public int depth;
    static Strategy[] strategies = Strategy.values();

    public GameTree(GameState gameState, int depth) {
        GameTreeNode newRoot = new GameTreeNode(null, gameState, 0);
        this.root = newRoot;
        this.depth = depth;
        root.expand(depth, true);
    }

    public class GameTreeNode implements Comparable<GameTreeNode> {
        GameTreeNode parent;
        GameTreeNode[] children;
        boolean chanceNode;
        public Strategy strategy;
        GameState gameState;
        double estimate;
        int droppedAmount;

        private GameTreeNode(boolean chanceNode, int droppedAmount) {
            this.chanceNode = chanceNode;
            this.droppedAmount = droppedAmount;
        }

        public GameTreeNode(Strategy strategy, GameState gameState, int droppedAmount) {
            this.strategy = strategy;
            this.gameState = gameState;
            this.droppedAmount = droppedAmount;
        }


        public void expand(int depth, boolean ai) {
            if (depth == 0) {
                return;
            }
            if (!chanceNode) {
                children = new GameTreeNode[11];
                for (int i = 3; i <= 13; i++) {
                    children[i-3] = new GameTreeNode(true, i);
                    children[i-3].parent = this;
                }
                for (GameTreeNode child : children) {
                    child.expand(depth - 1, ai);
                }
            } else {
                children = new GameTreeNode[12];
                int i = 0;
                for (Strategy strategy : strategies) {
                    children[i] = new GameTreeNode(strategy,
                            new GameState(parent.gameState.aiCount, parent.gameState.humanCount, 0),
                            droppedAmount);
                    children[i].parent = this;
                    i++;
                }
                for (GameTreeNode child : children) {
                    child.expand(depth - 1, !ai);
                }
            }
            for (GameTreeNode child : children) {
                if (child.gameState != null) {
                    child.changeSum();
                    child.gameState.eval(ai);
                }
            }
            if (depth == 2) {
                for (GameTreeNode child : children) {
                    child.estimate(ai);
                }
            }
        }

        public void estimate(boolean ai) {
            this.estimate = ai ? gameState.aiCount : gameState.humanCount;
        }

        public void changeSum() {
            this.gameState.changeSum(strategy, droppedAmount);
        }

        @Override
        public int compareTo(GameTreeNode o) {
            return (int) Math.floor(this.estimate - o.estimate);
        }
    }
}
