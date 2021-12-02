package com.company.player;

import com.company.Main;
import com.company.algo.GameTree;
import com.company.algo.Minimax;
import com.company.game.Field;
import com.company.game.Game;

public class AI implements Player {
    @Override
    public Field move(Field field) {
        Minimax minimax = new Minimax();
        GameTree gameTree = new GameTree(field, Game.depth);
        minimax.minimax(gameTree.root);
        int holeToMove = minimax.stack.pop().movedHole;
        return field.move(holeToMove);
    }
}
