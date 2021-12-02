package com.company.player;

import com.company.algo.GameTree;
import com.company.algo.Minimax;
import com.company.game.Field;
import com.company.game.Game;

public class AI implements Player {
    public int count = 0;

    @Override
    public Field move(Field field) {
        Minimax minimax = new Minimax();
        GameTree gameTree = new GameTree(field, Game.depth);
        minimax.minimax(gameTree.root);
        int holeToMove = minimax.stack.pop().movedHole;
        if (field.holes[holeToMove-1].count == 0) {
            for (int i = 5; i < 9; i++) {
                if (field.holes[i].count != 0) {
                    holeToMove = i + 1;
                    break;
                }
            }
        }
        return field.move(holeToMove, false);
    }

    @Override
    public void calcCount(Field field) {
        count += field.minCount;
    }

    @Override
    public int getCount() {
        return count;
    }
}
