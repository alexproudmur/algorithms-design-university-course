package com.company.algo;

import com.company.game.Field;

public class GameTree {
    public GameTreeNode root;
    public int depth;

    public GameTree(Field field, int depth) {
        this.depth = depth;
        this.root = new GameTreeNode(null, null, field);
        root.expand(depth, false);
    }

    public class GameTreeNode implements Comparable<GameTreeNode> {
        public GameTreeNode parent;
        public GameTreeNode[] children;
        public Field field;
        public int movedHole;
        public int estimate = -1;

        public GameTreeNode(GameTreeNode parent, GameTreeNode[] children, Field field, int movedHole) {
            this.parent = parent;
            this.children = children;
            this.field = field;
            this.movedHole = movedHole;
        }

        public GameTreeNode(GameTreeNode parent, GameTreeNode[] children, Field field) {
            this.parent = parent;
            this.children = children;
            this.field = field;
        }

        public void expand(int depth, boolean maximizing) {
            if (depth == 0) {
                return;
            }
            GameTreeNode[] gameTreeNodes = new GameTreeNode[5];
            for (int i = 0; i < 5; i++) {
                int hole = i + (maximizing ? 1 : 6);
                Field field = this.field.move(hole);
                //field.estimate();
                gameTreeNodes[i] = new GameTreeNode(this, null, field, hole);
            }
            this.children = gameTreeNodes;

            if (depth == 1) {
                for (GameTreeNode gameTreeNode : gameTreeNodes) {
                    gameTreeNode.estimate(maximizing);
                }
            }
            for (GameTreeNode gameTreeNode : gameTreeNodes) {
                gameTreeNode.expand(depth - 1, !maximizing);
            }
        }

        private void estimate(boolean maximizing) {
            this.estimate = maximizing ? field.maxCount : field.minCount;
        }

        public void setEstimate(int estimate) {
            this.estimate = estimate;
        }

        @Override
        public int compareTo(GameTreeNode o) {
            return this.estimate - o.estimate;
        }

        @Override
        public String toString() {
            return "GameTreeNode{" +
                    "movedHole=" + movedHole +
                    ", estimate=" + estimate +
                    '}';
        }
    }
}
