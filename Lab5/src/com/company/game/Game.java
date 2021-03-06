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
            case "Low":
                 depth = 2;
                 break;
            case "Medium":
                depth = 5;
                break;
            case "High":
                depth = 8;
                break;
            default:
                depth = 3;
        }
        players[0] = new Human();
        players[1] = new AI();
        field = new Field();
        System.out.println(field);
    }

    public void start() {
        int i = 0;
        while (true) {
            if (i % 2 == 0) {
                field = players[0].move(field);
            } else {
                field = players[1].move(field);
            }
            players[0].calcCount(field);
            players[1].calcCount(field);
            System.out.println("AI count - " + players[1].getCount());
            System.out.println("Your count - " + players[0].getCount());
            System.out.println(field);
            i++;
        }
    }
}
