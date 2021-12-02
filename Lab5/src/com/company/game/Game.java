package com.company.game;

import com.company.player.AI;
import com.company.player.Human;
import com.company.player.Player;

public class Game {
    public static int depth;
    Field field;
    Player[] players = new Player[2];

    public Game(String difficulty) {
        switch (difficulty) {
            case "Low": depth = 2; break;
            case "Medium": depth = 5; break;
            case "High": depth = 8; break;
            default: depth = 3;
        }
        players[0] = new Human();
        players[1] = new AI();
        field = new Field();
    }

    public void start() {
        int i = 0;
        while (true) {
            if (i % 2 == 0) {
                field = players[0].move(field);
            } else {
                field = players[1].move(field);
            }
            System.out.println(field);
            i++;
        }
    }
}
