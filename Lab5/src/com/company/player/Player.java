package com.company.player;

import com.company.game.Field;

public interface Player {
    Field move(Field field);
    void calcCount(Field field);
    int getCount();
}
