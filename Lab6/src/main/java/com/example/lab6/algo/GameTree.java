package com.example.lab6.algo;

import com.example.lab6.game.GameState;
import com.example.lab6.game.Strategy;

public class GameTree {
    public GameTreeNode root;
    public int depth;
    static Strategy[] strategies = Strategy.values();

    public GameTree(GameState gameState, int depth) {
        this.root = new GameTreeNode(null, gameState, 0);
        this.depth = depth;
        root.expand(depth, true);
    }

    public class GameTreeNode implements Comparable<GameTreeNode> {
        public GameTreeNode parent;
        public GameTreeNode[] children;
        public boolean chanceNode;
        public Strategy strategy;
        public GameState gameState;
        public double estimate;
        public int droppedAmount;

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
                estimate(!ai);
                return;
            }

            if (!chanceNode) {
                children = new GameTreeNode[11];
                for (int i = 3; i <= 13; i++) {
                    children[i - 3] = new GameTreeNode(true, i);
                    children[i - 3].parent = this;
                }
                for (GameTreeNode child : children) {
                    child.expand(depth - 1, ai);
                }
            } else {
                children = new GameTreeNode[12];
                int i = 0;
                for (Strategy strategy : strategies) {
                    children[i] = new GameTreeNode(strategy,
                            new GameState(parent.gameState.aiCount, parent.gameState.humanCount, parent.gameState.currSum),
                            droppedAmount);
                    children[i].parent = this;
                    children[i].changeSum();
                    children[i].gameState.eval(ai);
                    i++;
                }
                for (GameTreeNode child : children) {
                    child.expand(depth - 1, !ai);
                }
            }
            if (!chanceNode && parent != null) {
            estimate(!ai);}
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